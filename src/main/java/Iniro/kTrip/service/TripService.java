package Iniro.kTrip.service;

import Iniro.kTrip.dto.ResTripInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripService {

    private final static String END_POINT = "http://apis.data.go.kr/B551011/KorService1";

    public List<ResTripInfo> recommandTrip(int areaCode, int sigunguCode) {

        String reqUrl = "/areaBasedList1?numOfRows=12&pageNo=1&MobileOS=ETC&MobileApp=App&_type=json&areaCode=" + areaCode + "&sigunguCode=" + sigunguCode + "&serviceKey=jJL7RJtEplRWyT4h%252Bo9Cggy%252FZQEb%252FClx%252BSKxloGuo3wy%252FyoauDsMyxqll890ix4J6hKWnwLQP%252BCrn96rmXvXjA%253D%253D";

        List<ResTripInfo> resTripInfos = fetch(reqUrl);
        return resTripInfos;
    }

//    public List<ResTripInfo> getTripByKeyword(String keyword, int sigunguCode, int areaCode, int contentTypeId, String cat1, String cat2, String cat3) {
//
//
//    }
    public List<ResTripInfo> fetch(String url)  {
        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.getForObject(makeUrl(url), String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
        // 가장 큰 JSON 객체 response 가져오기
        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");

        // 그 다음 body 부분 파싱
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");

        // 그 다음 위치 정보를 배열로 담은 items 파싱
        JSONObject jsonItems = (JSONObject) jsonBody.get("items");

        // items는 JSON ->  그걸 또 배열로 가져온다
        JSONArray jsonItemList = (JSONArray) jsonItems.get("item");
        System.out.println(jsonItemList);
        List<ResTripInfo> result = new ArrayList<>();

        for (Object o : jsonItemList) {
            JSONObject item = (JSONObject) o;
            result.add(buildTripInfo(item));
            System.out.println(item);
        }

        return result;
    }

    private ResTripInfo buildTripInfo(JSONObject item) {
        return ResTripInfo.builder()
                .title((String) item.get("title"))
                .addr1((String) item.get("addr1"))
                .addr2((String) item.get("addr2"))
                .areacode(Integer.parseInt((String) item.get("areacode")))
                .contentid(Integer.parseInt((String) item.get("contentid")))
                .contenttypeid(Integer.parseInt((String) item.get("contenttypeid")))
                .firstimage((String) item.get("firstimage"))
                .firstimage2((String) item.get("firstimage2"))
                .sigungucode(Integer.parseInt((String) item.get("sigungucode")))
                .mapx((String) item.get("mapx"))
                .mapy((String) item.get("mapy"))
                .build();
    }

    private String makeUrl(String url){
        return END_POINT + url;
    }
}
