package Iniro.kTrip.controller;

import Iniro.kTrip.domain.Favorite;
import Iniro.kTrip.domain.Member;
import Iniro.kTrip.dto.MemberDetails;
import Iniro.kTrip.dto.ReviewDto;
import Iniro.kTrip.jwt.JWTUtil;
import Iniro.kTrip.repository.MemberRepository;
import Iniro.kTrip.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@CrossOrigin(origins = "*") // CORS 에러 방지
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @PostMapping("/toggle")
    public void toggleFavoriteSpot(
            @RequestHeader("Authorization") String token,
            @RequestParam(name = "cid", required = true) String cid,
            @RequestParam(name = "toggle", required = true) int toggle) throws IllegalAccessException {
        String memberId = jwtUtil.getId(token);
        Member member=memberRepository.findById(memberId);
        if(member!=null)
        {
            if(toggle == 0){
                favoriteService.deleteFavoriteSpot(cid, member);
            }
            else if(toggle == 1){
                favoriteService.addFavoriteSpot(cid, member);
            }
        }

    }
    @GetMapping("/load")
    public ResponseEntity<?> loadFavoriteSpot(@RequestHeader("Authorization") String token)
    {
        String memberId = jwtUtil.getId(token);
        Member member=memberRepository.findById(memberId);
        if(member!=null)
        {
            List<Favorite> favorites =favoriteService.favoriteByMid(member);
            return ResponseEntity.ok(favorites);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
    }
}