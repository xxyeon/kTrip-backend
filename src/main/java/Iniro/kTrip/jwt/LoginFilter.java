package Iniro.kTrip.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import Iniro.kTrip.domain.Member;
import Iniro.kTrip.dto.MemberDetails;
import Iniro.kTrip.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, MemberRepository memberRepository, String filterProcessesUrl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
        setFilterProcessesUrl(filterProcessesUrl); // 설정된 경로로 필터 매핑
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), Map.class);
            String id = credentials.get("id");
            String password = credentials.get("password");

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(id, password, null);
            log.info("authToekn: {}", authToken);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setFilterProcessesUrl(filterProcessesUrl);
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(filterProcessesUrl));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)throws IOException {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        String id = memberDetails.getId();
        String email = memberDetails.getEmail();
        String nickname = memberDetails.getNickname();
        String name = memberDetails.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String access = jwtUtil.createJwt("Authorization", id, role, email, nickname, name, 600000L);
        String refresh = jwtUtil.createJwt("refresh", id, role, email, nickname, name, 86400000L);

        addRefreshEntity(id, refresh, 86400000L);

        response.setHeader("Authorization", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());

    }

    private void addRefreshEntity(String id, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Member member = memberRepository.findById(id);
        if (member != null) {
            member.setRefreshToken(refresh);
            member.setExpiration(date.toString());
            memberRepository.save(member);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)throws IOException {
        response.setStatus(401);

    }
}
