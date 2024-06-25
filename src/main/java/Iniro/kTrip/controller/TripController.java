package Iniro.kTrip.controller;

import Iniro.kTrip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/detailinfo")
    public ResponseEntity<?> getTripIntro(
            @RequestParam(name = "contentid", required = false) String contentid,
            @RequestParam(name = "contenttypeid", required = false) String contenttypeid) {

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            List<Map<String, Object>> mainInfo = (List<Map<String, Object>>) tripService.getDetailCommon(contentid, contenttypeid);
            if (!mainInfo.isEmpty()) {
                mergeDetails(result, mainInfo.get(0));
            }

            List<Map<String, Object>> details = (List<Map<String, Object>>) tripService.getDetailIntro(contentid, contenttypeid);
            mergeDetailsList(result, details);

            details = (List<Map<String, Object>>) tripService.getDetailInfo(contentid, contenttypeid);
            mergeDetailsList(result, details);

            resultList.add(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching data: " + e.getMessage());
        }

        // JSON 형식으로 배열로 반환
        return ResponseEntity.ok().body(resultList);
    }

    // key-value 쌍에서 value가 빈 문자열이거나, 이미 존재하는 key는 데이터에 들어가지 않는 로직
    private void mergeDetails(Map<String, Object> result, Map<String, Object> items) {
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            if (entry.getValue() instanceof String && ((String) entry.getValue()).isEmpty()) {
                continue;
            }
            if (!result.containsKey(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
    }

    // 기존의 result에 새로운 데이터 추가
    private void mergeDetailsList(Map<String, Object> result, List<Map<String, Object>> itemsList) {
        for (Map<String, Object> item : itemsList) {
            mergeDetails(result, item);
        }
    }
}