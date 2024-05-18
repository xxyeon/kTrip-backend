package com.example.ktrip.gijin.Service;

import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MypageServiceTest {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MypageServiceTest(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @Test
    void changePassword() throws ClassNotFoundException {
        String id="admin";
        String password="1234";
        String newPassword="1234";
        Member member = memberRepository.findById(id);
        System.out.println(member.getPassword());
        if (member == null)
        {
            throw new ClassNotFoundException("해당 id에 맞는 계정이 없습니다.");
        }

        if(!bCryptPasswordEncoder.matches(password,member.getPassword()))
        {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        member.updatepassword(bCryptPasswordEncoder.encode(newPassword));
        memberRepository.save(member);
        System.out.println(member.getPassword());
    }


}