package Iniro.kTrip.util;

import Iniro.kTrip.openApi.TripApi;
import Iniro.kTrip.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static Iniro.kTrip.openApi.TripApi.TripEndpoint.*;
import static Iniro.kTrip.openApi.TripApi.TripQueryParam.*;

@Component
@Slf4j
public class URLBuilder {

    @Autowired
    private AreaService areaService;

    private static String makeUrl(String url) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(END_POINT.getParam());
        sbf.append(url);
        sbf.append("&");
        sbf.append(MOBILE_APP.getParam());
        sbf.append("&");
        sbf.append(MOBILE_OS.getParam());
        sbf.append("&");
        sbf.append(SERVICE_KEY.getParam());
        sbf.append("&");
        sbf.append(TYPE.getParam());
        sbf.append("&");
        sbf.append(NUM_OF_ROWS.getParam());
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    public static List<?> fetch(String url) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String htmlString = makeUrl(url);
        URI uri = new URI(htmlString);
        String jsonString = restTemplate.getForObject(uri, String.class);

        // JSON 유효성 검사 추가
        if (jsonString == null || jsonString.trim().isEmpty() || jsonString.charAt(0) != '{') {
            throw new RuntimeException("Invalid response received: " + jsonString);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage());
        }

        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONArray jsonItemList = null;
        if (jsonBody.containsKey("items")) {
            Object itemsObject = jsonBody.get("items");
            if (itemsObject instanceof JSONObject) {
                JSONObject jsonItems = (JSONObject) itemsObject;
                if (jsonItems.containsKey("item") && jsonItems.get("item") instanceof JSONArray) {
                    jsonItemList = (JSONArray) jsonItems.get("item");
                }
            }
        }

        log.info("jsonItemList: {}", jsonItemList);
        List<Object> result = new ArrayList<>();
        if (jsonItemList != null) {
            for (TripApi.TripEndpoint tripEndpoint : TripApi.TripEndpoint.values()) {
                if (url.contains(tripEndpoint.getEndPoint())) {
                    if(tripEndpoint.getEndPoint().equals("/areaBasedList1") || tripEndpoint.getEndPoint().equals("/searchKeyword1")){ // paging이 필요한 엔드포인트만.
                        Long totalCount = (Long) jsonBody.get("totalCount");
                        Long numOfRows = 12L;
                        result.add(totalCount);
                        result.add(numOfRows);
                    }
                    for (Object o : jsonItemList) {
                        JSONObject item = (JSONObject) o;
                        result.add(item);
                    }
                    break;
                }
            }
        }

        return result.isEmpty() ? Collections.emptyList() : result;
    }
}
