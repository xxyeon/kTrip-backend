package Iniro.kTrip.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ResTripInfo implements ResJson{

    private String addr1;
    private String addr2;
    private int areacode;
    private String booktour;
    private String cat1;
    private String cat2;
    private String cat3;
    private int contentid;
    private int contenttypeid;
    private String firstimage;
    private String firstimage2;
    private String mapx;
    private String mapy;
    private int sigungucode;
    private String title;
    private String zipcode;
    private String tel;
    private String homepage;
    private String overview;

}
