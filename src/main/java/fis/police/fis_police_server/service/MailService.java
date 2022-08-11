package fis.police.fis_police_server.service;

import fis.police.fis_police_server.dto.MailSendResponse;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

/*
    작성 날짜: 2022/02/14 11:47 오전
    작성자: 고준영
    작성 내용: 메일 서비스
*/
public interface MailService {
    MailSendResponse sendMail(Long center_id) throws MessagingException;
}
