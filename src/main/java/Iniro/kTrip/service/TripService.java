package Iniro.kTrip.service;

import Iniro.kTrip.dto.ResDetailIntro;
import Iniro.kTrip.dto.ResJson;
import Iniro.kTrip.dto.ResStayInfo;
import Iniro.kTrip.dto.ResTripInfo;
import Iniro.kTrip.util.URLBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    public static List<?> recommendTrip(String areaCode, String sigunguCode, String contenttypeid, String pageNo, String cat1) throws URISyntaxException {
        String area_code = areaCode != null ? areaCode : "";
        String sigungu_code = sigunguCode != null ? sigunguCode : "";
        String content_type_id = contenttypeid != null ? contenttypeid : "";
        String page_no = pageNo != null ? pageNo : "1";
        String category1 = cat1 != null ? cat1 : "";
        String reqUrl = AREA_BASED_LIST1.getEndPoint() + "?" +
                AREA_CODE.getParam() + area_code + "&" +
                SIGUNGU_CODE.getParam() + sigungu_code + "&" +
                CONTENT_TYPE_ID.getParam() + content_type_id + "&" +
                PAGE_NO.getParam() + page_no + "&" +
                CAT1.getParam() + category1;

        return fetch(reqUrl);
    }
    public static Object getDetailIntro(String contentId, String contentTypeId) throws URISyntaxException {
        String reqUrl = null;
        String content_type_id = contentTypeId != null ? contentTypeId : "";
        String content_id = contentTypeId != null ? contentId : "";
        try {
            reqUrl = DETAIL_INTRO1.getEndPoint() +
                    "?" + CONTENT_TYPE_ID.getParam() + content_type_id +
                    "&" + CONTENT_ID.getParam() + content_id;

        } catch (Exception e) {
            log.error("{}", e);
        }
        return fetch(reqUrl);
    }
    public static Object getDetailInfo(String contentId, String contentTypeId) throws URISyntaxException {
        String reqUrl = null;
        String content_type_id = contentTypeId != null ? contentTypeId : "";
        String content_id = contentTypeId != null ? contentId : "";
        try {
            reqUrl = DETAIL_INFO1.getEndPoint() +
                    "?" + CONTENT_TYPE_ID.getParam() + content_type_id +
                    "&" + CONTENT_ID.getParam() + content_id;

        } catch (Exception e) {
            log.error("{}", e);
        }
        return fetch(reqUrl);
    }
    public static Object getDetailCommon(String contentId, String contentTypeId) throws URISyntaxException {
        String reqUrl = null;
        String content_type_id = contentTypeId != null ? contentTypeId : "";
        String content_id = contentTypeId != null ? contentId : "";
        String page_no =  "1";
        try {
            reqUrl = DETAIL_COMMON1.getEndPoint() +
                    "?" + CONTENT_TYPE_ID.getParam() + content_type_id +
                    "&" + CONTENT_ID.getParam() + content_id +
                    "&" + PAGE_NO.getParam() + page_no +
                    "&" + DEFAULT_YN.getParam() +
                    "&" + FIRST_IMAGE_YN.getParam() +
                    "&" + AREA_CODE_YN.getParam() +
                    "&" + CAT_CODE_YN.getParam() +
                    "&" + ADDR_INFO_YN.getParam() +
                    "&" + MAP_INFO_YN.getParam() +
                    "&" + OVERVIEW_YN.getParam();
        } catch (Exception e) {
            log.error("{}", e);
        }
        return fetch(reqUrl);
    }
    public Object getTripByKeyword(String keyword, String pageNo) throws URISyntaxException {
        String reqUrl = null;
        String keyWord = keyword != null ? keyword : "";
        String page_no = pageNo != null ? pageNo : "1";
        try {
            reqUrl = SEARCH_KEYWORD1.getEndPoint() +
                    "?" + KEYWORD.getParam() + keyWord +
                    "&" + PAGE_NO.getParam() + page_no;
        } catch (Exception e) {
            log.error("{}", e);
        }
        return fetch(reqUrl);
    }
}