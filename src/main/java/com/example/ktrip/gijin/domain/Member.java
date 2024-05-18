package com.example.ktrip.gijin.domain;



import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int member_id;

    @Column(length = 20)
    private String id;
    @Column(length = 255)
    private String password;

    @Column(length = 20)
    private String email;
    @Column(length = 20)
    private String nickname;
    @Column(length = 20)
    private String name;

    @Column(length=255)
    private String access_token;
    @Column(length=255)
    private String refresh_token;
    @Column(length = 20)
    private String type;
    public void updatepassword(String new_password)
    {
        this.password=new_password;
    }


}
