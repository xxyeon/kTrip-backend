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
    public ResponseEntity<?> recommandTrip(int areaCode, int sigunguCode) {

        return ResponseEntity.ok().body(tripService.recommandTrip(areaCode, sigunguCode));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getTrip(
            String keyword,
            @RequestParam(required = false) Integer sigunguCode,
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
    public ResponseEntity<?> getTripCommon(String contentId) {
        return ResponseEntity.ok().body(tripService.getTripInfo(contentId));
    }

    @GetMapping ("/detail_intro")
    ResponseEntity<?> getTripIntro(String contentId, String contentTypeId) {
        return ResponseEntity.ok().body(tripService.getTripIntro(contentId, contentTypeId));
    }

    @GetMapping("/stay/info")
    ResponseEntity<?> getStayInfo(String contentId, String contentTypeId) {
        return ResponseEntity.ok().body(tripService.getStayInfo(contentId, contentTypeId));
    }
}
