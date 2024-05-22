package Iniro.kTrip.controller;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Review;
import Iniro.kTrip.domain.ReviewData;
import Iniro.kTrip.dto.ReviewDto;
import Iniro.kTrip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // CORS 에러 방지
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/review")
    public List<Review> getReviewListJson(@RequestParam("ctypeid") int ctypeid, @RequestParam("cid") int cid) {
        return reviewService.reviewLoad(ctypeid, cid);
    }

    @PostMapping("/writepro")
    public void reviewWritePro(@RequestBody ReviewData reviewData) {
        Review newReview = reviewService.reviewCreate(reviewData);
        reviewService.reviewRegister(newReview);
    }
}