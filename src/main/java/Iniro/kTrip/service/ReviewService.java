package Iniro.kTrip.service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import Iniro.kTrip.dao.*;
import Iniro.kTrip.domain.*;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@AllArgsConstructor
@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public void reviewWrite(Review review){reviewRepository.save(review);}
    public List<Review> reviewList(int ctypeid, int cid){return reviewRepository.findByCtypeidAndCid(ctypeid, cid);}
    public void reviewDelete(int review_id){reviewRepository.deleteById(review_id);}

}
