package Iniro.kTrip.service;

import Iniro.kTrip.dto.ResJson;
import Iniro.kTrip.dto.ResStayInfo;
import Iniro.kTrip.dto.ResTripInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Iniro.kTrip.openApi.TripApi.TripEndpoint.*;
import static Iniro.kTrip.openApi.TripApi.TripQueryParam.*;
import static Iniro.kTrip.util.URLBuilder.fetch;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripService {

    public static List<?> recommendTrip(String areaCode, String sigunguCode, String pageNo) throws URISyntaxException {
        String area_code = areaCode != null ? areaCode : "";
        String sigungu_code = sigunguCode != null ? sigunguCode : "";
        String page_no = pageNo != null ? pageNo : "1";
        String reqUrl = AREA_BASED_LIST1.getEndPoint() +
                "?" + AREA_CODE.getParam() + area_code + "&" +
                SIGUNGU_CODE.getParam() + sigungu_code + "&" +
                PAGE_NO.getParam() + page_no;

        return fetch(reqUrl);
    }


    public static ResTripInfo buildTripInfo(JSONObject item) {
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

    private static void setResJson(JSONObject item, ResJson resJson) {
        resJson.setContentId((String) item.get("contentid"));
        resJson.setContentTypeId((String) item.get("contenttypeid"));
    }


}