package Iniro.kTrip.domain;



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
    private int mid;

    @Column(length = 20)
    private String id;
    @Column(length = 20)
    private String password;

    @Column(length = 50, columnDefinition = "MEDIUMTEXT")
    private String email;
    @Column(length = 20)
    private String nickname;
    @Column(length = 20)
    private String name;

    @Column(name = "access_token", length=255)
    private String accessToken;

    @Column(name = "refresh_token",length=255) // @Column 애너테이션을 통해 데이터베이스 컬럼 이름을 명시
    private String refreshToken; // 자바 필드 이름은 camelCase를 사용하지만, 데이터베이스 컬럼 이름은 snake_case를 사용할 수 있습니다.

    @Column(length = 20)
    private String loginType;


    @Column(length=255)
    private String expiration;
    @Column(length=255)
    private String role;
    public void updatepassword(String new_password)
    {
        this.password=new_password;
    }


    // Oauth 구현할 때의 생성자.
    public Member(String userId, String nickname, String email,String type){
        this.id = userId;
        this.password = "PassWord";
        this.nickname = nickname;
        this.email = email;
        this.loginType = type;
    }
}
