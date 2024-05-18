package com.example.ktrip.gijin.domain;

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
    private int review_id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column
    private int point;

    @Column(length = 255)
    private String content;

    @Column
    private int content_id;

    @Column
    private int content_type_id;
}

