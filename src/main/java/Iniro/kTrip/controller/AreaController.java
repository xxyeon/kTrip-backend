package Iniro.kTrip.controller;


import Iniro.kTrip.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
public class AreaController {
    @Autowired
    AreaService areaService;

    @GetMapping("/area")
    public ResponseEntity<List<?>> getArea(@RequestParam(name = "areacode", required = false) String areaCode,@RequestParam(name = "pageno", required = false) String pageNo) throws URISyntaxException {
        List<?> areaInfo = areaService.getAreaInfo(areaCode, pageNo);
        return ResponseEntity.ok().body(areaInfo);
    }

}