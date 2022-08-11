package fis.police.fis_police_server.dto;


import fis.police.fis_police_server.domain.enumType.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*
    날짜 : 2022/01/10 2:29 오후
    작성자 : 원보라
    작성내용 : user 추가 시
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {
    private Long user_id;
    private String u_nickname;
    private String u_name;
    private String u_pwd;
    private String u_ph;
    private LocalDate u_sDate;
    private UserAuthority u_auth;
}
