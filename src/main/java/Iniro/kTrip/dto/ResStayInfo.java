package Iniro.kTrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResStayInfo implements ResJson{
    private String contentId;
    private String contentTypeId;
    private String roomCode;
    private String roomTitle;
    private String roomSize;
    private String roomCount;
    private String roomBaseCount;
    private String roomMaxCount;
    private String roomOffSeasonMinFee1; //비수기주중최소1
    private String roomOffSeasonMinFee2; //비수기주말최소2
    private String roomPeakSeasonMinfee1; //성수기 주중 최소
    private String roomPeakSeasonMinfee2; //성수기 주말 최소
    private String roomIntro;
    private String roomBathFacility;
    private String roomBath;
    private String roomHomeTheater;
    private String roomAirCondition;
    private String roomTv;
    private String roomPc;
    private String roomCable;
    private String roomInternet;
    private String roomRefrigerator;
    private String roomToiletries;
    private String roomSofa;
    private String roomCook;
    private String roomTable;
    private String roomHairdryer;
    private String roomSize2;
    private RoomImg roomImg1;
    private RoomImg roomImg2;
    private RoomImg roomImg3;
    private RoomImg roomImg4;
    private RoomImg roomImg5;




    public static class RoomImg {
        private String roomImg;
        private String roomImgAlt;

        public RoomImg(String roomImg, String roomImgAlt) {
            this.roomImg = roomImg;
            this.roomImgAlt = roomImgAlt;
        }
    }

//    public void setRoomImg(RoomImg roomImg1, RoomImg roomImg2, RoomImg roomImg3, RoomImg roomImg4, RoomImg roomImg5) {
//        this.roomImg1 = roomImg1;
//        this.roomImg2 = roomImg2;
//        this.roomImg3 = roomImg3;
//        this.roomImg4 = roomImg4;
//        this.roomImg5  = roomImg5;
//
//    }

}
