package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/*
    날짜 : 2022/01/19 4:14 오후
    작성자 : 원보라
    작성내용 : login 성공시 보내줄 dto
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String u_name;
    private UserAuthority u_auth;
    private String sc; //status code

    /*
        작성 날짜: 2022/02/15 3:45 오후
        작성자: 고준영
        작성 내용: 토큰
    */
    private String token;
    private String refreshToken;
}
