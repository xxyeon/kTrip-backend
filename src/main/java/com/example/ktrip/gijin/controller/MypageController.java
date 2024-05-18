package com.example.ktrip.gijin.controller;

import com.example.ktrip.gijin.domain.Favorite;
import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.domain.Review;
import com.example.ktrip.gijin.dto.MemberDetails;
import com.example.ktrip.gijin.dto.NicknameDto;
import com.example.ktrip.gijin.dto.PasswordDto;
import com.example.ktrip.gijin.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

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
    public String joinProcess(PasswordDto passwordDto)
    {
        mypageService.changePassword(passwordDto);
        return "/mypage";
    }
    @PostMapping("/mypage/nickname")
    public String joinProcess(NicknameDto nicknameDto)
    {
        mypageService.changeNickname(nicknameDto);
        return "/mypage";
    }
    @GetMapping("/mypage")
    public ResponseEntity<?> showMypage(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {
            String id = memberDetails.getId();
            Member member = mypageService.getMemberById(id);
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
            String id = memberDetails.getId();
            Member member = mypageService.getMemberById(id);
            List<Review> reviews = mypageService.FindReviews(member);
            return ResponseEntity.ok(reviews);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "사용자에게 접근 권한이 없습니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @DeleteMapping("/mypage/review/{review_id}")
    @PreAuthorize("hasAuthority('DELETE_REVIEW')")
    public void deleteReview(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable int review_id) {
        if (memberDetails != null) {
            String id=memberDetails.getId();
            Member member=mypageService.getMemberById(id);

            mypageService.deleteReview(review_id,member);

        } else {
            throw new IllegalArgumentException("사용자에게 접근 권한이 없습니다");
        }
    }
    @GetMapping("/mypage/favorite")
    public ResponseEntity<?> showFavorite(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails != null) {
            String id = memberDetails.getId();
            Member member = mypageService.getMemberById(id);
            List<Favorite> favorites = mypageService.FindFavorite(member);
            return ResponseEntity.ok(favorites);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "사용자에게 접근 권한이 없습니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
    @DeleteMapping("/mypage/favorite/{favorite_id}")
    @PreAuthorize("hasAuthority('DELETE_FAVORITE')")
    public void deleteFavorite(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable int favorite_id) {
        if (memberDetails != null) {
            String id=memberDetails.getId();
            Member member=mypageService.getMemberById(id);
            mypageService.deleteFavorite(favorite_id,member);

        } else {
            throw new IllegalArgumentException("사용자에게 접근 권한이 없습니다");
        }
    }

}
