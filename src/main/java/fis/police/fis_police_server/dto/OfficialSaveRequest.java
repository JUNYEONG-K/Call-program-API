package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.UserAuthority;
import lombok.Data;

/*
    작성 날짜: 2022/02/14 11:43 오전
    작성자: 고준영
    작성 내용: 시설 담당자 회원가입 폼
*/
@Data
public class OfficialSaveRequest {
    private Long center_id;
    private String o_name;
    private String o_nickname;
    private String o_pwd;
    private String o_ph;
    private String o_email;
    private UserAuthority u_auth;
}
