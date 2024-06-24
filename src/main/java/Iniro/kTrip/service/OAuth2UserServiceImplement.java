package Iniro.kTrip.service;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

    private final MemberRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();
        try{
            // 디버깅을 위한 코드입니다. 문제 생겨서 일단 토큰부터 만듦
            //System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        }catch (Exception e){
            e.printStackTrace();
        }
        Member member = null;
        String userId = null;
        String email = "email";
        if (oauthClientName.equals("naver")){
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id").substring(0, 14);
            email = responseMap.get("email");
            member = new Iniro.kTrip.domain.Member(userId, email, "nickname", "naver");
        }
        userRepository.save(member);

        return oAuth2User;
    }
}
