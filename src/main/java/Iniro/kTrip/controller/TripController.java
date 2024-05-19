package Iniro.kTrip.controller;

import Iniro.kTrip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/search")
//    public ResponseEntity<?> getTrip(String keyword, int sigunguCode, int areaCode, int contentTypeId, String cat1, String cat2, String cat3) {
//        return ResponseEntity.ok().body(tripService.getTripByKeyword(keyword, sigunguCode, areaCode, contentTypeId, cat1, cat2, cat3));
//    }
}
