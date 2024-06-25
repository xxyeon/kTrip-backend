package Iniro.kTrip.controller;


import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Review;
import Iniro.kTrip.domain.Want;
import Iniro.kTrip.dto.MemberDetails;
import Iniro.kTrip.dto.NicknameDto;
import Iniro.kTrip.dto.PasswordDto;
import Iniro.kTrip.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MypageController
{
    private final MypageService mypageService;
    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @PostMapping("/mypage/password")
    public String password(@RequestBody PasswordDto passwordDto)
    {
        mypageService.changePassword(passwordDto);
        return "/mypage";
    }
    @PostMapping("/mypage/nickname")
    public String nickname(@RequestBody NicknameDto nicknameDto)
    {
        mypageService.changeNickname(nicknameDto);
        return "/mypage";
    }
    @GetMapping("/mypage")
    public ResponseEntity<?> showMypage(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("name", member.getName());
            response.put("nickname", member.getNickname());
            response.put("email", member.getEmail());

            return ResponseEntity.ok(response);
        } else {
            throw new IllegalArgumentException("사용자에게 접근 권한이 없습니다");
        }
    }

    @GetMapping("/mypage/review")
    public ResponseEntity<?> showReview(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {

            Member member = mypageService.getMemberById(memberDetails.getId());
            List<Review> reviews = mypageService.FindReviews(member);
            return ResponseEntity.ok(reviews);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "사용자에게 접근 권한이 없습니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @DeleteMapping("/mypage/review/{rid}")
    @PreAuthorize("hasAuthority('DELETE_REVIEW')")
    public void deleteReview(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable int rid) {
        if (memberDetails != null) {
            String id=memberDetails.getId();
            Member member=mypageService.getMemberById(id);

            mypageService.deleteReview(rid,member);

        } else {
            throw new IllegalArgumentException("사용자에게 접근 권한이 없습니다");
        }
    }
    @GetMapping("/mypage/want")
    public ResponseEntity<?> showWant(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            List<Want> wants = mypageService.FindWant(member);
            return ResponseEntity.ok(wants);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "사용자에게 접근 권한이 없습니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
    @DeleteMapping("/mypage/want/{cid}")
    @PreAuthorize("hasAuthority('DELETE_WANT')")
    public void deleteWant(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable int cid) {
        if (memberDetails != null) {
            Member member = mypageService.getMemberById(memberDetails.getId());
            mypageService.deleteWant(cid,member);

        } else {
            throw new IllegalArgumentException("사용자에게 접근 권한이 없습니다");
        }
    }

}
