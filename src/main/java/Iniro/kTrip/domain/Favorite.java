package Iniro.kTrip.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="Favorite")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fid;

    @ManyToOne
    @JoinColumn(name="mid")
    private Member member;

    @Column
    private String cid;



}

