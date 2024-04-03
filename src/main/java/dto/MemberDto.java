package dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class MemberDto {
    private int member_id;
    private String id;
    private String email;
    private String nickname;
    private String name;

    public MemberDto(int member_id, String id, String email, String nickname, String name){
        this.member_id = member_id;
        this.id=id;
        this.name=name;
        this.email=email;
        this.nickname=nickname;
    }

}
