package Iniro.kTrip.controller;

import Iniro.kTrip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ResourceBundle;

@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public ResponseEntity<?> recommandTrip(String areaCode, String sigunguCode) {

        return ResponseEntity.ok().body(tripService.recommandTrip(areaCode, sigunguCode));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getTrip(
            String keyword,
            @RequestParam(required = false) String sigunguCode,
            @RequestParam(required = false) Integer areaCode,
            @RequestParam(required = false) Integer contentTypeId,
            @RequestParam(required = false) String cat1,
            @RequestParam(required = false) String cat2,
            @RequestParam(required = false) String cat3) {
        return ResponseEntity.ok().body(tripService.getTripByKeyword(keyword, sigunguCode, areaCode, contentTypeId, cat1, cat2, cat3));
    }

    @GetMapping("/stay")
    public ResponseEntity<?> getStay(
            @RequestParam(required = false) String areaCode,
            @RequestParam(required = false) String sigunguCode
    ) {
        return ResponseEntity.ok().body(tripService.getStay(areaCode, sigunguCode));
    }

    @GetMapping("/detail_common")
    public ResponseEntity<?> getTripCommon(String contentId) { //servicekey error
        return ResponseEntity.ok().body(tripService.getTripInfo(contentId));
    }

    @GetMapping("/detail_intro")
    ResponseEntity<?> getTripIntro(String contentId, String contentTypeId) {
        return ResponseEntity.ok().body(tripService.getTripIntro(contentId, contentTypeId));
    }

    @GetMapping("/stay/info")
    ResponseEntity<?> getStayInfo(String contentId, String contentTypeId) {
        return ResponseEntity.ok().body(tripService.getStayInfo(contentId, contentTypeId));
    }

    /**
     * 위치기반 여행지 추천
     */
    @GetMapping("/location")
    public ResponseEntity<?> recommandByLocation(String mapX, String mapY, String radius) {
        return ResponseEntity.ok().body(tripService.recommandByLocation(mapX, mapY, radius));
    }


    /**
     * 지역별 음식점 추천
     */
    @GetMapping("/food")
    public ResponseEntity<?> recommandFood(String areaCode, String sigunguCode, @RequestParam(required = false) String cat3) {
        return ResponseEntity.ok().body(tripService.recommendFood(areaCode, sigunguCode, cat3));
    }

    /**
     * 지역별 쇼핑몰 추천
     */
    @GetMapping("/shopping")
    public ResponseEntity<?> recommandShopping(String areaCode, String sigunguCode, @RequestParam(required = false) String cat3) {
        return ResponseEntity.ok().body(tripService.recommendShopping(areaCode, sigunguCode, cat3));
    }

}
