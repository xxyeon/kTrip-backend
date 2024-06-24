package Iniro.kTrip.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

import Iniro.kTrip.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import Iniro.kTrip.domain.ReviewData;

import Iniro.kTrip.domain.*;
import Iniro.kTrip.dto.*;
import Iniro.kTrip.util.ReviewConverter;
import lombok.*;
import java.util.List;
@AllArgsConstructor
@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public void registerReview(Review review){reviewRepository.save(review);}
    public Review createReview(ReviewData reviewData){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = localDateTime.format(formatter);

        return Review.builder()
                .member(Member.builder().mid(reviewData.getMid()).build())
                .ctypeid(reviewData.getCtypeid())
                .cid(reviewData.getCid())
                .point(reviewData.getPoint())
                .content(reviewData.getContent())
                .writedate(formattedDateTime)
                .build();
    }
    public List<ReviewDto> getReviewDtos(int ctypeid, int cid) {
        return reviewRepository.findByCtypeidAndCid(ctypeid, cid).stream()
                .map(ReviewConverter::convertToDto)
                .collect(Collectors.toList());
    }
}
