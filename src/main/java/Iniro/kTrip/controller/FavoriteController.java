package Iniro.kTrip.controller;

import Iniro.kTrip.dao.FavoriteRepository;
import Iniro.kTrip.domain.Favorite;
import Iniro.kTrip.domain.Member;
import Iniro.kTrip.jwt.JWTUtil;
import Iniro.kTrip.repository.MemberRepository;
import Iniro.kTrip.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private FavoriteRepository favoriteRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @PostMapping("/toggle")
    public ResponseEntity<?> toggleFavorite(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> params) {
        try {
            String cid = params.get("cid");
            int toggle = Integer.parseInt(params.get("toggle"));
            String memberId = jwtUtil.getId(token);
            Member member = memberRepository.findById(memberId);

            if (member != null) {
                if (toggle == 1) {
                    favoriteService.addFavoriteSpot(cid, member);
                } else if (toggle == 0) {
                    favoriteService.deleteFavoriteSpot(cid, member);
                }
                return ResponseEntity.ok("즐겨찾기 업데이트 성공");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("잘못된 toggle 값입니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    @GetMapping("/load")
    public ResponseEntity<?> loadFavoriteSpot(@RequestHeader("Authorization") String token, @RequestParam(name = "cid") String cid) {
        String memberId = jwtUtil.getId(token);
        Member member = memberRepository.findById(memberId);
        if (favoriteRepository.existsByMemberAndCid(member, cid)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자에게 접근 권한이 없습니다");
    }

}