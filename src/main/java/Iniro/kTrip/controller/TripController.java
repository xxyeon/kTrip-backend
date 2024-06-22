package Iniro.kTrip.controller;

import Iniro.kTrip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/trip")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/recommend")
    public ResponseEntity<?> recommendTrip(@RequestParam(name = "areacode", required = false) String areaCode,
                                           @RequestParam(name = "sigungucode", required = false) String sigunguCode,
                                           @RequestParam(name = "contenttypeid", required = false) String contenttypeid,
                                           @RequestParam(name = "pageno", required = false) String pageNo,
                                           @RequestParam(name = "cat1", required = false) String cat1) throws URISyntaxException{

        return ResponseEntity.ok().body(tripService.recommendTrip(areaCode, sigunguCode, contenttypeid, pageNo, cat1));
    }

}