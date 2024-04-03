package service;

import domain.Member;
import dto.MemberDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

    // 로그인 서비스 코드. 로그인 성공(회원 db에 존재하는 정보)하면 true
    public boolean loginCheck(Member member){
        boolean result = memberRepository.findById(member.getMember_id()).isPresent();
        return result;
    }




}
