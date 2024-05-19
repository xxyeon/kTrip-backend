package Iniro.kTrip.service;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.dto.MemberDto;
import Iniro.kTrip.token.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import Iniro.kTrip.repository.MemberRepository;

import java.util.Optional;
import java.util.OptionalLong;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    private MemberDto memberDto;

    // 회원가입 서비스 코드
    public boolean signUp(Member member){
        Optional<Member> isExist = memberRepository.findById(member.getMember_id());
        if (isExist.isEmpty()){
            memberRepository.save(member);
            return true;
        }
        else{
            return false;
        }

    }

    @Transactional
    // 로그인 서비스 코드. 로그인 성공(회원 db에 존재하는 정보)하면 true
    public String login(Member member){
        int member_id = member.getMember_id();
        String password = member.getPassword();

        Optional<Member> requestMember = memberRepository.findById(member_id);
        if (requestMember.isEmpty()){
            throw new UsernameNotFoundException("존재하지 않는 ID입니다.");
        }

        // 암호화된 password를 디코딩한 값과 password 값이 다르면 null 반환
        if (!encoder.matches(password, requestMember.get().getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        MemberDto info = modelMapper.map(member, MemberDto.class);

        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }




}
