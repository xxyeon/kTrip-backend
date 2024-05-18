package com.example.ktrip.gijin.service;

import com.example.ktrip.gijin.domain.Favorite;
import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.domain.Review;
import com.example.ktrip.gijin.dto.NicknameDto;
import com.example.ktrip.gijin.dto.PasswordDto;

import com.example.ktrip.gijin.repository.FavoriteRepository;
import com.example.ktrip.gijin.repository.MemberRepository;
import com.example.ktrip.gijin.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MypageService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MypageService(ReviewRepository reviewRepository, MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FavoriteRepository favoriteRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.favoriteRepository = favoriteRepository;
    }

    public void changePassword(PasswordDto passwordDto) {
        String id = passwordDto.getId();
        String currentPassword = passwordDto.getCurrentPassword();
        String newPassword = passwordDto.getNewPassword();
        Member member = validatePassword(id, currentPassword);
        if (member != null) {
            member.updatepassword(bCryptPasswordEncoder.encode(newPassword));
            memberRepository.save(member);
        }

    }

    public void changeNickname(NicknameDto nicknameDto) {
        String id = nicknameDto.getId();
        String currentNickname = nicknameDto.getCurrentNickname();
        String newNickname = nicknameDto.getNewNickname();
        Member member = memberRepository.findById(id);

        if (member != null && member.getNickname().equals(currentNickname)) {
            member.setNickname(newNickname);
            memberRepository.save(member);
        }

    }

    public Member validatePassword(String id, String password) {
        Member member = memberRepository.findById(id);
        if (member == null)
            throw new IllegalArgumentException("현재 사용자 정보가 없습니다");

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        return member;

    }

    public Member getMemberById(String id) {
        Member member = memberRepository.findById(id);
        if (member != null)
            return member;
        else
            return null;
    }

    public List<Review> FindReviews(Member member) {
        return reviewRepository.findByMember(member);
    }

    ;

    public List<Favorite> FindFavorite(Member member) {
        return favoriteRepository.findByMember(member);
    }

    ;

    public void deleteReview(int review_id, Member member) {
        int member_id = member.getMember_id();
        List<Review> reviews = reviewRepository.findByMember(member);
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getReview_id() == review_id) {
                reviewRepository.delete(reviews.get(i));
                return;
            }
        }
        throw new IllegalArgumentException("현재 사용자가 작성한 리뷰가 아닙니다.");


    }

    public void deleteFavorite(int favorite_id, Member member) {
        int member_id = member.getMember_id();
        List<Favorite> favorites= favoriteRepository.findByMember(member);
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getFav_id() == favorite_id) {
                favoriteRepository.delete(favorites.get(i));
                return;
            }
        }
        throw new IllegalArgumentException("현재 사용자가 작성한 코스가 아닙니다.");
    }
}