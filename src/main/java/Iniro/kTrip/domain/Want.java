package Iniro.kTrip.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Want {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    @ManyToOne
    @JoinColumn(name="mid")
    private Member member;

    @Column
    private int ctypeid;
}
