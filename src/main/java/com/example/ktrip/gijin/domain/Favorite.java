package com.example.ktrip.gijin.domain;

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
    private int fav_id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(length = 20)
    private String region;

    @Column
    private int content_id;

    @Column
    private int content_type_id;

}

