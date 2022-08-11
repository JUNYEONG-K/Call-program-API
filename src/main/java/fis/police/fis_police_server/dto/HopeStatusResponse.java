package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HopeStatusResponse {
    private String center_name;
    private String center_address;
    private Accept accept;
    private String h_date;
    private String h_time;
    private String date;    // 신청날짜
    private String o_name;
    private String o_ph;
    private String o_email;
}
