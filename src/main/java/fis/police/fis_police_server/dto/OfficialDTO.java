package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
    작성 날짜: 2022/02/18 3:36 오후
    작성자: 고준영
    작성 내용:
*/
@Data
@AllArgsConstructor
public class OfficialDTO {
    private Long official_id;
    private String o_name;
    private String o_ph;
    private String o_email;
}
