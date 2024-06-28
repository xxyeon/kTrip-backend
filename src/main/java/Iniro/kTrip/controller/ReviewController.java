package Iniro.kTrip.controller;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.ReviewData;
import Iniro.kTrip.dto.MemberDetails;
import Iniro.kTrip.dto.ReviewDto;
import Iniro.kTrip.jwt.JWTUtil;
import Iniro.kTrip.repository.MemberRepository;
import Iniro.kTrip.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*") // CORS 에러 방지
@CrossOrigin(origins = "http://localhost:3000") // CORS 에러 방지
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @GetMapping()
    public ResponseEntity<?> getReviewListJson(/*@AuthenticationPrincipal MemberDetails memberDetails,*/ @RequestParam("ctypeid") int ctypeid, @RequestParam("cid") int cid) {
//        if (memberDetails != null) {
//            List<ReviewDto> reviews = reviewService.getReviewDtos(ctypeid, cid);
//            return ResponseEntity.ok(reviews);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
//        }
        List<ReviewDto> reviews = reviewService.getReviewDtos(ctypeid, cid);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/write")
    public ResponseEntity<?> createReview(@RequestHeader("Authorization") String token, @RequestBody ReviewData reviewData) {
        String memberId = jwtUtil.getId(token);
        Member member=memberRepository.findById(memberId);
        if (member != null) {
            System.out.println(member.getMid());
            reviewService.registerReview(reviewService.createReview(member.getMid(),reviewData));
            return ResponseEntity.ok("리뷰가 성공적으로 작성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
        }
    }
}