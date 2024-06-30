package Iniro.kTrip.openApi;

import Iniro.kTrip.dto.ResAreaInfo;
import Iniro.kTrip.service.AreaService;
import Iniro.kTrip.service.TripService;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.util.function.Function;

public class TripApi {

    @Getter
    public enum TripEndpoint {
        AREA_CODE1("/areaCode1"),
        AREA_BASED_LIST1("/areaBasedList1"),
        DETAIL_COMMON1("/detailCommon1"),
        DETAIL_INTRO1("/detailIntro1"),
        SEARCH_KEYWORD1("/searchKeyword1"),
        LOCATION_BASED_LIST1("/locationBasedList1"),
        SEARCH_STAY1("/searchStay1"),
        DETAIL_INTRO("/detailIntro1"),
        DETAIL_INFO("/detailInfo1"),
        DETAIL_INFO1("/detailInfo1");


        private final String endpoint;

        TripEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndPoint() {
            return endpoint;
        }

    }

    @Getter
    public static enum TripQueryParam {  // <- 여기
        END_POINT("http://apis.data.go.kr/B551011/KorService1"),
        TYPE("_type=json"),
        MOBILE_OS("MobileOS=ETC"),
        MOBILE_APP("MobileApp=kTrip"),
        SERVICE_KEY("serviceKey=B4FraEdNAEHerMG6ZQUi5OXCzio%2FQJ4IRx9rOOz7%2BeiPBh4L8pX4XAygutNaYnOoL%2BD8vS%2F3qZ53efN6daHZ%2Fg%3D%3D"),
        NUM_OF_ROWS("numOfRows=12"),
        PAGE_NO("pageNo="),
        LIST_YN("listYN="),
        ARRANGE("arrange="),
        CONTENT_TYPE_ID("contentTypeId="),
        AREA_CODE("areaCode="),
        SIGUNGU_CODE("sigunguCode="),
        CAT1("cat1="),
        CAT2("cat2="),
        CAT3("cat3="),
        MODIFIED_TIME("modifiedtime="),
        KEYWORD("keyword="),
        DEFAULT_YN("defaultYN=Y"),
        FIRST_IMAGE_YN("firstImageYN=Y"),
        AREA_CODE_YN("areacodeYN=Y"),
        CAT_CODE_YN("catcodeYN=Y"),
        ADDR_INFO_YN("addrinfoYN=Y"),
        MAP_INFO_YN("mapinfoYN=Y"),
        OVERVIEW_YN("overviewYN=Y"),
        //위치 정보 조회
        MAP_X("mapX="),
        MAP_Y("mapY="),
        RADIUS("radius="),
        CONTENT_ID("contentId=");

        private final String param;

        TripQueryParam(String param) {
            this.param = param;
        }

        public String getParam() {
            return param;
        }
    }
}
