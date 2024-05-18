package com.example.ktrip.gijin.service;

import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.dto.MemberDetails;
import com.example.ktrip.gijin.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailService implements UserDetailsService
{
    private final MemberRepository memberRepository;
    @Autowired
    public MemberDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException
    {
        Member memberData= memberRepository.findById(id);

        if(memberData!=null)
        {
            return new MemberDetails(memberData);
        }
        return null;
    }
}
