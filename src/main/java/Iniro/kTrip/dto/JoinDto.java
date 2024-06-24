package Iniro.kTrip.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto
{

    private String id;
    private String password;

    private String email;

    private String nickname;

    private String name;


}
