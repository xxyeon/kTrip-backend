package Iniro.kTrip.controller;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.dto.MemberDetails;
import Iniro.kTrip.repository.MemberRepository;
import Iniro.kTrip.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // CORS 에러 방지
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/toggle")
    public void toggleFavoriteSpot(
                                @AuthenticationPrincipal MemberDetails memberDetails,
                                @RequestParam(name = "cid", required = true) String cid,
                                @RequestParam(name = "toggle", required = true) int toggle) throws IllegalAccessException {
        Member member=memberRepository.findById(memberDetails.getId());
        if(member!=null)
        {
            if(toggle == 0){
                favoriteService.deleteFavoriteSpot(cid, member.getMid());
            }
            else if(toggle == 1){
                favoriteService.addFavoriteSpot(cid, member.getMid());
            }
        }

    }
}