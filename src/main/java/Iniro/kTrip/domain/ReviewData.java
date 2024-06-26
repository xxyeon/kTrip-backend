package Iniro.kTrip.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

// 프론트에서 작성한 리뷰 데이터 ( 별점, 내용 )
@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid = 3;
    private int ctypeid = 1;
    private int cid = 3;
    private int point;
    private String content;
    private String writedate;
}
