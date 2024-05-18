package com.example.ktrip.gijin.service;

import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.dto.JoinDto;
import com.example.ktrip.gijin.repository.MemberRepository;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService
{

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public JoinService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public void joinProcess(JoinDto joinDto)
    {
        String id= joinDto.getId();
        String password= joinDto.getPassword();

        Boolean isExist =memberRepository.existsById(id);

        if(isExist)
        {

            return ;
        }
        Member data=new Member();
        data.setId(id);
        data.setPassword(bCryptPasswordEncoder.encode(password));


        memberRepository.save(data);
    }
}