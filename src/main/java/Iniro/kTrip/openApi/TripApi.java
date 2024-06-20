package Iniro.kTrip.openApi;

import Iniro.kTrip.dto.ResAreaInfo;
import Iniro.kTrip.service.AreaService;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.util.function.Function;

public class TripApi {

    @Getter
    public enum TripEndpoint {
        AREA_CODE1("/areaCode1", AreaService::buildAreaInfo);

        private final String endpoint;
        private final Function<JSONObject, Object> mapperFunction;

        TripEndpoint(String endpoint, Function<JSONObject, Object> mapperFunction) {
            this.endpoint = endpoint;
            this.mapperFunction = mapperFunction;
        }

        public String getEndPoint() {
            return endpoint;
        }

        public Function<JSONObject, Object> getMapperFunction() {
            return mapperFunction;
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
        DEFAULT_YN("defaultYN="),
        FIRST_IMAGE_YN("firstImageYN="),
        AREA_CODE_YN("areacodeYN="),
        CAT_CODE_YN("catcodeYN="),
        ADDR_INFO_YN("addrinfoYN="),
        MAP_INFO_YN("mapinfoYN="),
        OVERVIEW_YN("overviewYN="),
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
