package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.dto.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

/*
    작성 날짜: 2022/02/14 11:32 오전
    작성자: 고준영
    작성 내용: 콜 기록 저장
*/
public interface CallController {
    // 연락기록 저장
    CallSaveResponse saveCall(CallSaveRequest request, HttpServletRequest req) throws MessagingException;
}
