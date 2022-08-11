package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*
    날짜 : 2022/01/13 10:41 오전
    작성자 : 원보라
    작성내용 : 콜직원 페이지에 띄울 dto
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long user_id;           //DB id
    private String u_nickname;      //로그인 사용자 id
    private String u_name;          //사용자 이름
    private String u_pwd;           //사용자 비밀번호
    private String u_ph;            //사용자 전화번호
    private LocalDate u_sDate;      //사용자 입사일
    private UserAuthority u_auth;   //사용자 권한 (ADMIN, USER, FIRED)
    private double average_call;    //평균 통화 건수
    private int today_call_num;     //오늘 통화 건수
}
