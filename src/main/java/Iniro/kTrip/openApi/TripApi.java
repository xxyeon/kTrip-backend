package Iniro.kTrip.openApi;


import lombok.Getter;

@Getter
public enum TripApi {

    //쿼리 파라미터
    END_POINT("http://apis.data.go.kr/B551011/KorService1"),
    TYPE("_type=json"),
    MOBILE_OS("MobileOS=ETC"),
    MOBILE_APP("MobileApp=kTrip"),
    SERVICE_KEY("serviceKey=jJL7RJtEplRWyT4h%252Bo9Cggy%252FZQEb%252FClx%252BSKxloGuo3wy%252FyoauDsMyxqll890ix4J6hKWnwLQP%252BCrn96rmXvXjA%253D%253D"),
    NUM_OF_ROWS("numOfRows=12"),
    PAGE_NO("pageNo=1"),
    LIST_YN("listYN="),
    ARRANGE("arrange="),
    CONTENT_TYPE_ID("contentTypeId="),
    AREA_CODE("areaCode="),
    SIGUNGU_CODE("sigunguCode="),
    CAT1("cat1="),
    CAT2("cat2="),
    CAT3("cat3="),
    MODIFIED_TIME("modifiedtime="),

    //api목록
    AREABASEDLIST1("/areaBasedList1")
    ;

    private final String value;

    TripApi(String value) {
        this.value = value;
    }
}
