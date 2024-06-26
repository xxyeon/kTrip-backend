package Iniro.kTrip.controller;

import Iniro.kTrip.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // CORS 에러 방지
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/toggle")
    public void toggleFavoriteSpot(@RequestParam(name = "cid", required = true) String cid,
                                @RequestParam(name = "mid", required = true) int mid,
                                @RequestParam(name = "toggle", required = true) int toggle) throws IllegalAccessException {
        if(toggle == 0){
            favoriteService.deleteFavoriteSpot(cid, mid);
        }
        else if(toggle == 1){
            favoriteService.addFavoriteSpot(cid, mid);
        }
    }
}