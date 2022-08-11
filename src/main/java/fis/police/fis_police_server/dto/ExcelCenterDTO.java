package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.Visited;
import fis.police.fis_police_server.service.excelService.ExcelColumn;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelCenterDTO {
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

    public static Center createCenter(ExcelCenterDTO centerDTO){
        return new Center(centerDTO.getC_sido(), centerDTO.getC_sigungu(),
                centerDTO.getC_name(), centerDTO.getC_type(),
                centerDTO.getC_status(), centerDTO.getC_address(),
                centerDTO.getC_zipcode(), centerDTO.getC_ph(),
                centerDTO.getC_faxNum(), centerDTO.getC_people(), centerDTO.getC_hpAddress());
    }
}
