package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.Visited;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CenterModifyDTO {
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

    public static Center convertToCenter (CenterModifyDTO centerModifyDTO) {
        return  new Center(centerModifyDTO.getCenter_id(),
                centerModifyDTO.getC_sido(),
                centerModifyDTO.getC_sigungu(),
                centerModifyDTO.getC_name(),
                centerModifyDTO.getC_type(),
                centerModifyDTO.getC_status(),
                centerModifyDTO.getC_address(),
                centerModifyDTO.getC_zipcode(),
                centerModifyDTO.getC_ph(),
                centerModifyDTO.getC_faxNum(),
                centerModifyDTO.getC_people(),
                centerModifyDTO.getC_hpAddress(),
                centerModifyDTO.getC_latitude(),
                centerModifyDTO.getC_longitude(),
                centerModifyDTO.getParticipation(),
                centerModifyDTO.getVisited()
                ,new ArrayList<Call>()
                ,new ArrayList<Schedule>());
    }
}
