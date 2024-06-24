package Iniro.kTrip.service;

import Iniro.kTrip.dto.ResAreaInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;


import java.net.URISyntaxException;
import java.util.List;

import static Iniro.kTrip.openApi.TripApi.TripEndpoint.AREA_CODE1;
import static Iniro.kTrip.openApi.TripApi.TripQueryParam.AREA_CODE;
import static Iniro.kTrip.openApi.TripApi.TripQueryParam.PAGE_NO;
import static Iniro.kTrip.util.URLBuilder.fetch;

@Service
@Slf4j
@RequiredArgsConstructor
public class AreaService {

    public static List<?> getAreaInfo(String areaCode, String pageNo) throws URISyntaxException {
        String reqUrl = AREA_CODE1.getEndPoint() + "?" + PAGE_NO.getParam() + pageNo + "&";
        if(areaCode != null){
            reqUrl += AREA_CODE.getParam() + areaCode;
        }
        @SuppressWarnings("unchecked")
        List<ResAreaInfo> resAreaInfos = (List<ResAreaInfo>) fetch(reqUrl);
        return resAreaInfos;
    }
    public static ResAreaInfo buildAreaInfo(JSONObject item) {
        return ResAreaInfo.builder()
                .code((String) item.get("code"))
                .name((String) item.get("name"))
                .build();
    }
}