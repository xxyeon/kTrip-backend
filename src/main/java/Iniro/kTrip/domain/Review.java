package Iniro.kTrip.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;

    @ManyToOne
    @JoinColumn(name="mid")
    private Member member;

    @Column
    private int point;

    @Column(length = 255)
    private String content;

    @Column
    private int cid;

    @Column
    private int ctypeid;

    @Column(length = 255)
    private String writedate;

}

