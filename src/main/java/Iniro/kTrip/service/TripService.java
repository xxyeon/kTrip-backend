package Iniro.kTrip.service;

import Iniro.kTrip.dto.ResJson;
import Iniro.kTrip.dto.ResStayInfo;
import Iniro.kTrip.dto.ResTripInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.tree.ExpandVetoException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static Iniro.kTrip.openApi.TripApi.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripService {

    public Result recommendTrip(String areaCode, String sigunguCode) {
        String area_code = areaCode != null ? areaCode : "";
        String sigungu_code = sigunguCode != null ? sigunguCode : "";
        String reqUrl = AREA_BASED_LIST1.getValue() +
                "?" + AREA_CODE.getValue() + area_code + "&" +
                SIGUNGU_CODE.getValue() + sigungu_code;

//        List<ResJson> resTripInfos = fetch(reqUrl);
        return fetch(reqUrl);
    }

    public Result getTripByKeyword(String keyword, String sigunguCode, Integer areaCode, Integer contentTypeId, String cat1, String cat2, String cat3) {
        List<ResJson> resTripInfos = new ArrayList<>();
        Result result = new Result();
        if (keyword.isBlank()) {
            log.error("키워드를 입력해주세요.");

            return result;
        } else {
            String sigungu_code = sigunguCode != null ? sigunguCode.toString() : "";
            String area_code = areaCode != null ? areaCode.toString() : "";
            String content_type_id = contentTypeId != null ? contentTypeId.toString() : "";
            String cat_1 = cat1 != null ? cat1 : "";
            String cat_2 = cat2 != null ? cat2 : "";
            String cat_3 = cat3 != null ? cat3 : "";


            String reqUrl = SEARCH_KEYWORD1.getValue() +
                    "?" + KEYWORD.getValue() + keyword +
                    "&" + SIGUNGU_CODE.getValue() + sigungu_code +
                    "&" + AREA_CODE.getValue() + area_code +
                    "&" + CONTENT_TYPE_ID.getValue() + content_type_id +
                    "&" + CAT1.getValue() + cat_1 +
                    "&" + CAT2.getValue() + cat_2 +
                    "&" + CAT3.getValue() + cat_3;

                    ;
//            resTripInfos = fetch(reqUrl);
            return fetch(reqUrl);
        }

    }

    public JSONArray reqOpenApi(String url) {
        JSONObject jsonObject = null;

        try {
            RestTemplate restTemplate = new RestTemplate();
//            String jsonString = restTemplate.getForObject(makeUrl(url), String.class);
//            log.info("{}", jsonString);
            URL url1 = new URL(makeUrl(url));
            InputStream in = url1.openStream();

            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            IOUtils.copy(in, bos1);
            in.close();
            bos1.close();

            String mobs = bos1.toString("UTF-8");

            byte[] b = mobs.getBytes("UTF-8");
            String s = new String(b, "UTF-8");



            JSONParser jsonParser = new JSONParser();

            jsonObject = (JSONObject) jsonParser.parse(s);
            log.info("{}", jsonObject);
        } catch (Exception e) {
            log.error("{}", e);
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

        return jsonItemList;
    }
    public Object fetchJson(String url) { //dto로 변환 필요
        return reqOpenApi(url);

    }

    public Result fetch(String url)  {
        Result res = new Result();
        JSONArray jsonItemList = reqOpenApi(url);

        if (url.contains(DETAIL_INFO.getValue())) {
            List<ResJson> result = new ArrayList<>();

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                result.add(buildStayInfo(item));
                System.out.println(item);
            }
//            Result res = new Result();
            res.result = result;
        } else {
            List<ResJson> result = new ArrayList<>();

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                result.add(buildTripInfo(item));
                System.out.println(item);

            }
            res.result= result;

        }
        return res;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Result<T> {
        private T result;
    }

    private ResTripInfo buildTripInfo(JSONObject item) {
        ResTripInfo resTripInfo = ResTripInfo.builder()
                .title((String) item.get("title"))
                .addr1((String) item.get("addr1"))
                .addr2((String) item.get("addr2"))
                .areacode(Integer.parseInt((String) item.get("areacode")))
                .firstimage((String) item.get("firstimage"))
                .firstimage2((String) item.get("firstimage2"))
                .sigungucode(Integer.parseInt((String) item.get("sigungucode")))
                .mapx((String) item.get("mapx"))
                .mapy((String) item.get("mapy"))
                .booktour((String) item.get("booktour"))
                .tel((String) item.get("tel"))
//                .homepage((String) item.get("homepage"))
//                .overview((String) item.get("overview"))
                .build();
        setResJson(item, resTripInfo);
        return resTripInfo;
    }

    private ResStayInfo buildStayInfo(JSONObject item) {

        ResStayInfo resStayInfo = ResStayInfo.builder()
                .roomBaseCount((String) item.get("roombasecount"))
                .roomCode((String) item.get("roomcode"))
                .roomTitle((String) item.get("roomtitle"))
                .roomSize((String) item.get("roomsize"))
                .roomCount((String) item.get("roomcount"))
                .roomBaseCount((String) item.get("roombasecount"))
                .roomMaxCount((String) item.get("roommaxcount"))
                .roomOffSeasonMinFee1((String) item.get("roomoffseasonminfee1"))
                .roomOffSeasonMinFee2((String) item.get("roomoffseasonminfee2"))
                .roomPeakSeasonMinfee1((String) item.get("roompeakseasonminfee1"))
                .roomPeakSeasonMinfee2((String) item.get("roompeakseasonminfee2"))
                .roomIntro((String) item.get("roomintro"))
                .roomBath((String) item.get("roombath"))
                .roomHomeTheater((String) item.get("roomhometheater"))
                .roomAirCondition((String) item.get("roomaircondition"))
                .roomTv((String) item.get("roomtv"))
                .roomPc((String) item.get("roompc"))
                .roomCable((String) item.get("roomcable"))
                .roomInternet((String) item.get("roominternet"))
                .roomRefrigerator((String) item.get("roomrefrigerator"))
                .roomToiletries((String) item.get("roomtoiletries"))
                .roomSofa((String) item.get("roomsofa"))
                .roomCook((String) item.get("roomcook"))
                .roomTable((String) item.get("roomtable"))
                .roomHairdryer((String) item.get("roomhairdryer"))
                .roomSize2((String) item.get("roomsize2"))
                .roomImg1(new ResStayInfo.RoomImg((String) item.get("roomimg1"), (String) item.get("roomimg1alt")))
                .roomImg2(new ResStayInfo.RoomImg((String) item.get("roomimg2"), (String) item.get("roomimg2alt")))
                .roomImg3(new ResStayInfo.RoomImg((String) item.get("roomimg3"), (String) item.get("roomimg3alt")))
                .roomImg4(new ResStayInfo.RoomImg((String) item.get("roomimg4"), (String) item.get("roomimg4alt")))
                .roomImg5(new ResStayInfo.RoomImg((String) item.get("roomimg5"), (String) item.get("roomimg5alt")))
                .roomSize2((String) item.get("roomsize2")).build();
        setResJson(item, resStayInfo);

        return resStayInfo;

    }

    private void setResJson(JSONObject item, ResJson resJson) {
        resJson.setContentId((String) item.get("contentid"));
        resJson.setContentTypeId((String) item.get("contenttypeid"));
    }

    private String makeUrl(String url) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(END_POINT.getValue());
        sbf.append(url);
        sbf.append("&");
        sbf.append(MOBILE_APP.getValue());
        sbf.append("&");
        sbf.append(MOBILE_OS.getValue());
        sbf.append("&");
        sbf.append(SERVICE_KEY.getValue());
        sbf.append("&");
        sbf.append(TYPE.getValue());
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    public Result getStay(String areaCode, String sigunguCode) {
        String area_code = areaCode != null ? areaCode : "";
        String sigugn_code = sigunguCode != null ? sigunguCode : "";

        String reqUrl = SEARCH_STAY1.getValue() +
                "?" + AREA_CODE.getValue() + area_code +
                "&" + SIGUNGU_CODE.getValue() + sigugn_code;
        return fetch(reqUrl);

    }

    public Object getTripInfo(String contentId) {
        String reqUrl = null;
        try {
            reqUrl = DETAIL_COMMON1.getValue() +
                    "?" + CONTENT_ID.getValue() + contentId +
                    "&" + DEFAULT_YN.getValue() + "Y" +
                    "&" + FIRST_IMAGE_YN.getValue() + "Y" +
                    "&" + AREA_CODE_YN.getValue() + "Y" +
                    "&" + CAT_CODE_YN.getValue() + "Y" +
                    "&" + ADDR_INFO_YN.getValue() + " Y" +
                    "&" + MAP_INFO_YN.getValue() + "Y" +
                    "&" + OVERVIEW_YN.getValue() + "Y";

        } catch (Exception e) {
            log.error("{}", e);

        }
        return fetchJson(reqUrl);

    }

    public Object getTripIntro(String contentId, String contentTypeId) {
        String reqUrl = null;
        try {
            reqUrl = DETAIL_INTRO.getValue() +
                    "?" + CONTENT_TYPE_ID.getValue() + contentTypeId +
                    "&" + CONTENT_ID.getValue() + contentId;

        } catch (Exception e) {
            log.error("{}", e);
        }
        return fetchJson(reqUrl);
    }

    public Object getStayInfo(String contentId, String contentTypeId) {
        String reqUrl = null;
        try {
            reqUrl = DETAIL_INFO.getValue() +
                    "?" + CONTENT_TYPE_ID.getValue() + contentTypeId +
                    "&" + CONTENT_ID.getValue() + contentId;
        } catch (Exception e) {
            log.error("{}", e);
        }

        return fetchJson(reqUrl);

    }

    public Object recommendByLocation(String mapX, String mapY, String radius) {
        //radius null이면 기본으로 max값 적용(2000)
        if (radius == null) {
            radius = "2000";
        }
        String reqUrl = LOCATION_BASED_LIST1.getValue()
                + "?" + MAP_X.getValue() + mapX
                + "&" + MAP_Y.getValue() + mapY
                + "&" + RADIUS.getValue() + radius
                ;
        return fetch(reqUrl);
    }


    public Object recommendX(String areaCode, String sigunguCode, String cat1, String cat2, String cat3) {
        cat3 = cat3 != null ? cat3 : "";
        String reqUrl = AREA_BASED_LIST1.getValue()
                + "?" + CAT1.getValue() + cat1
                + "&" + CAT2.getValue() + cat2
                + "&" + CAT3.getValue() + cat3;

        if (areaCode == null) {
            //모든 음식점 조회
            return fetch(reqUrl)
                    ;

        } else {
            reqUrl += "&" + AREA_CODE.getValue() + areaCode;
            if (sigunguCode == null) {
                return fetch(reqUrl);
            } else {
                reqUrl += "&" + SIGUNGU_CODE.getValue() + sigunguCode;
                return fetch(reqUrl);
            }

        }
    }

    public Object recommendFood(String areaCode, String sigunguCode, String cat3) {
        String area_code = areaCode != null ? areaCode : "";
        String sigungu_code = sigunguCode != null ? sigunguCode : "";
        return recommendX(area_code, sigungu_code, "A05", "A0502", cat3);
    }

    public Object recommendShopping(String areaCode, String sigunguCode, String cat3) {
        String area_code = areaCode != null ? areaCode : "";
        String sigungu_code = sigunguCode != null ? sigunguCode : "";
        return recommendX(area_code, sigungu_code, "A04", "A0401", cat3);
    }

}
