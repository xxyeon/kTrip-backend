package Iniro.kTrip.service;



import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.Review;
import Iniro.kTrip.domain.Want;
import Iniro.kTrip.dto.NicknameDto;
import Iniro.kTrip.dto.PasswordDto;
import Iniro.kTrip.repository.MemberRepository;
import Iniro.kTrip.repository.ReviewRepository;
import Iniro.kTrip.repository.WantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MypageService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final WantRepository wantRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MypageService(ReviewRepository reviewRepository, MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder, WantRepository wantRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.wantRepository = wantRepository;
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



    public List<Want> FindWant(Member member) {
        return wantRepository.findByMember(member);
    }



    public void deleteReview(int review_id, Member member) {

        List<Review> reviews = reviewRepository.findByMember(member);
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getRid() == review_id) {
                reviewRepository.delete(reviews.get(i));
                return;
            }
        }
        throw new IllegalArgumentException("현재 사용자가 작성한 리뷰가 아닙니다.");


    }

    public void deleteWant(int cid, Member member) {
        List<Want> wants= wantRepository.findByMember(member);
        for (int i = 0; i < wants.size(); i++) {
            if (wants.get(i).getCid() == cid) {
                wantRepository.delete(wants.get(i));
                return;
            }
        }
        throw new IllegalArgumentException("현재 사용자가 작성한 코스가 아닙니다.");
    }
}