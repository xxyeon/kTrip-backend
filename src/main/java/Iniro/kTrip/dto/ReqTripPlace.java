package Iniro.kTrip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReqTripPlace {
    private String keyword;
    private String sigunguCode;
    private String areaCode;
    private String contentTypeId;
    private String cat1;
    private String cat2;
    private String cat3;
}

