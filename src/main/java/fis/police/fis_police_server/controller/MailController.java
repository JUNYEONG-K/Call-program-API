package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.dto.MailSendResponse;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

/*
    작성 날짜: 2022/02/14 11:33 오전
    작성자: 고준영
    작성 내용: 시설 담당자에게 메일 전송
*/
public interface MailController {

    // 시설에 메일 전송
    MailSendResponse sendMail(Long center_id, HttpServletRequest request) throws MessagingException;
}
