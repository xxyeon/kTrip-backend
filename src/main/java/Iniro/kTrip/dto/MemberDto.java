package Iniro.kTrip.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class MemberDto {
    private int member_id;
    private String id;
    private String password;
    private String email;
    private String nickname;
    private String name;

    public MemberDto(int member_id, String id, String password,String email, String nickname, String name){
        this.member_id = member_id;
        this.password = password;
        this.id=id;
        this.name=name;
        this.email=email;
        this.nickname=nickname;
    }

}
