package Iniro.kTrip.controller;

import Iniro.kTrip.domain.ReviewData;
import Iniro.kTrip.dto.ReviewDto;
import Iniro.kTrip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*") // CORS 에러 방지
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping()
    public List<ReviewDto> getReviewListJson(@RequestParam("ctypeid") int ctypeid, @RequestParam("cid") int cid) {
        return reviewService.getReviewDtos(ctypeid, cid);
    }

    @PostMapping("/write")
    public void createReview(@RequestBody ReviewData reviewData) {
        reviewService.registerReview(reviewService.createReview(reviewData));
    }
}