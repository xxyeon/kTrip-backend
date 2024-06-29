package Iniro.kTrip.handler;

import Iniro.kTrip.domain.CustomOAuth2User;
import Iniro.kTrip.domain.Member;
import Iniro.kTrip.dto.MemberDto;
import Iniro.kTrip.jwt.JWTUtil;
import Iniro.kTrip.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException{

        String redirectUri = "http://localhost:8080/login/oauth2/code/naver";
        String targetUrl;

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2User.getName();
        Member member =  memberRepository.findById(userId);
        //MemberDto memberDto = new MemberDto(member.getMember_id(), member.getId(), member.getPassword(), member.getEmail(), member.getNickname(), member.getName());
        String token = jwtProvider.createAccessToken("Authorization",member, 100000);
        String refresh = jwtProvider.createAccessToken("refresh", member, 86400000);
        response.setHeader("Authorization", token);
        response.addCookie(createCookie("refresh", refresh));
        addRefreshEntity(member.getId(), refresh, 86400000);
        targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build().toUriString();

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        //response.sendRedirect("http://localhost:8080/auth/oauth-response/"+token+"/3600");


    }
    private void addRefreshEntity(String id, String refresh, int expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Member member = memberRepository.findById(id);
        if (member != null) {
            member.setRefreshToken(refresh);
            member.setExpiration(date.toString());
            memberRepository.save(member);
        }
    }
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
