package Iniro.kTrip.controller;

import Iniro.kTrip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trip")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
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
    @GetMapping("/search")
    public ResponseEntity<?> getTrip(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageno", required = false) String pageNo) throws URISyntaxException {

        //입력받은 값을 암호화하여 전달
        String encodedKeyword = keyword != null ? URLEncoder.encode(keyword, StandardCharsets.UTF_8) : null;

        return ResponseEntity.ok().body(tripService.getTripByKeyword(encodedKeyword, pageNo));
    }

    // key-value 쌍에서 value가 빈 문자열이거나, 이미 존재하는 key는 데이터에 들어가지 않는 로직
    private void mergeDetails(Map<String, Object> result, Map<String, Object> items) {
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            if (entry.getValue() instanceof String && ((String) entry.getValue()).isEmpty()) {
                result.put(entry.getKey(), "정보 없음");
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


    /**
     * 위치기반 여행지 추천
     */
    @GetMapping("/location")
    public ResponseEntity<?> recommendByLocation(String mapX, String mapY, String radius, @RequestParam(name = "pageno", required = false) String pageNo) {
        log.info("mapX: {} mapY: {}", mapX, mapY);
        return ResponseEntity.ok().body(tripService.recommendByLocation(mapX, mapY, radius, pageNo));
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


    @GetMapping("/stay/info")
    ResponseEntity<?> getStayInfo(String contentId, String contentTypeId) {
        return ResponseEntity.ok().body(tripService.getStayInfo(contentId, contentTypeId));
    }



    /**
     * 지역별 음식점 추천
     */
    @GetMapping("/food")
    public ResponseEntity<?> recommendFood(String areaCode, String sigunguCode, @RequestParam(required = false) String cat3) throws URISyntaxException {
        return ResponseEntity.ok().body(tripService.recommendFood(areaCode, sigunguCode, cat3));
    }

    /**
     * 지역별 쇼핑몰 추천
     */
    @GetMapping("/shopping")
    public ResponseEntity<?> recommendShopping(String areaCode, String sigunguCode, @RequestParam(required = false) String cat3) throws URISyntaxException {
        return ResponseEntity.ok().body(tripService.recommendShopping(areaCode, sigunguCode, cat3));
    }
}