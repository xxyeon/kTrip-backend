package Iniro.kTrip.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(name = "Review")
@Data
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
    private int ctypeid; // 여행지 id
    @Column
    private int cid; // 여행지 id
    @Column
    private int point;
    @Column(length = 255)
    private String content;
    @Column(length = 45)
    private String writedate;
}