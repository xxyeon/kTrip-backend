package domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int review_id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column
    private int point;

    @Column(length = 255)
    private String content;
}
