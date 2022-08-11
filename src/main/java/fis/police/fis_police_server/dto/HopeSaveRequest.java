package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.Accept;
import lombok.Data;

/*
    작성 날짜: 2022/02/14 11:41 오전
    작성자: 고준영
    작성 내용: 지문등록 참여 여부 신청서 제출/작성 양식
*/
@Data
public class HopeSaveRequest {
    // 지문등록 참여 여부
    private Accept accept;
    // 지문등록 희망 날짜, 시간
    private String h_date;
    private String h_time;
    // 신청 날짜
    private String now_date;
    // 담당자 전화번호, 이메일
    private String h_ph;
    private String h_mail;
}
