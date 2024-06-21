package Iniro.kTrip.service;


import Iniro.kTrip.domain.Member;
import Iniro.kTrip.dto.JoinDto;
import Iniro.kTrip.repository.MemberRepository;
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
        String email=joinDto.getEmail();
        String name= joinDto.getName();;
        String nickname= joinDto.getNickname();
        Boolean isExist =memberRepository.existsById(id);

        if(isExist)
        {

            return ;
        }
        Member data=new Member();
        data.setId(id);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setNickname(nickname);
        data.setEmail(email);
        data.setName(name);


        memberRepository.save(data);
    }
}