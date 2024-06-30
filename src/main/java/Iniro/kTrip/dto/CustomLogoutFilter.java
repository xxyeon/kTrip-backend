package Iniro.kTrip.dto;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.jwt.JWTUtil;
import Iniro.kTrip.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //path and method verify
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {

            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =request.getHeader("Authorization");
        String id=jwtUtil.getId(token);
        //로그아웃 진행
        //Refresh 토큰 DB에서 제거
        Member member=memberRepository.findById(id);
        member.setRefreshToken(null);
        member.setExpiration(null);
        memberRepository.save(member);



        response.setStatus(HttpServletResponse.SC_OK);
    }
}
