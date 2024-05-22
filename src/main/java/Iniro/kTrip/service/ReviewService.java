package Iniro.kTrip.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import Iniro.kTrip.domain.ReviewData;
import Iniro.kTrip.dao.*;
import Iniro.kTrip.domain.*;
import Iniro.kTrip.dto.*;
import lombok.*;
import java.util.List;
@AllArgsConstructor
@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public void reviewRegister(Review review){reviewRepository.save(review);}
    public List<Review> reviewLoad(int ctypeid, int cid){return reviewRepository.findByCtypeidAndCid(ctypeid, cid);}
    public Review reviewCreate(ReviewData reviewData){
        Review newReview = new Review();
        Member member = new Member();
        member.setMid(reviewData.getMid());

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = localDateTime.format(formatter);

        newReview.setMember(member);
        newReview.setCtypeid(reviewData.getCtypeid());
        newReview.setCid(reviewData.getCid());
        newReview.setPoint(reviewData.getPoint());
        newReview.setContent(reviewData.getContent());
        newReview.setWritedate(formattedDateTime);

        return newReview;
    }

}
