package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.Visited;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CenterSaveDTO {
    private Long center_id;     // 'primary_key',
    private String c_sido;        // '시도',
    private String c_sigungu;     // '시군구',
    private String c_name;        // '시설명',
    private String c_type;        // '유형',
    private String c_status;      // '운영현황',
    private String c_address;     // '주소',
    private String c_zipcode;     // '우편번호',
    private String c_ph;          // '전화번호',
    private String c_faxNum;      // '팩스번호',
    private String c_people;      // '현원',
    private String c_hpAddress;   // '홈페이지주소',
    private Double c_latitude;    // '위도',
    private Double c_longitude;   // '경도',
    private Participation participation;
    private Visited visited;

    public static Center convertToCenter (CenterSaveDTO centerSaveDTO) {
        return new Center(centerSaveDTO.getCenter_id(),
                centerSaveDTO.getC_sido(),
                centerSaveDTO.getC_sigungu(),
                centerSaveDTO.getC_name(),
                centerSaveDTO.getC_type(),
                centerSaveDTO.getC_status(),
                centerSaveDTO.getC_address(),
                centerSaveDTO.getC_zipcode(),
                centerSaveDTO.getC_ph(),
                centerSaveDTO.getC_faxNum(),
                centerSaveDTO.getC_people(),
                centerSaveDTO.getC_hpAddress(),
                centerSaveDTO.getC_latitude(),
                centerSaveDTO.getC_longitude(),
                centerSaveDTO.getParticipation(),
                centerSaveDTO.getVisited()
                ,new ArrayList<Call>()
                ,new ArrayList<Schedule>());
    }

    public CenterSaveDTO(Long center_id, String c_name, String c_address, String c_ph, Double c_latitude, Double c_longitude) {
        this.center_id = center_id;
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_ph = c_ph;
        this.c_hpAddress = c_hpAddress;
        this.c_latitude = c_latitude;
        this.c_longitude = c_longitude;
    }
}
