package fis.police.fis_police_server.error.advice;

import fis.police.fis_police_server.controller.controllerImpl.MailControllerImpl;
import fis.police.fis_police_server.error.error_result.ErrorResult;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
/*
    작성 날짜: 2022/02/18 5:49 오후
    작성자: 고준영
    작성 내용: 메일 전송 예외처리
*/
@Slf4j
@RestControllerAdvice(assignableTypes = MailControllerImpl.class)
public class MailControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddressException.class)
    public ErrorResult addrExHandler(AddressException e) {
        log.error("[AddressExceptionHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MailException.class)
    public ErrorResult mailExHandler(MailException e) {
        log.error("메일 설정을 확인하세요. POP3/IMAP 모두 '사용으로 설정되어 있는지 확인하세요.");
        log.error("[MailExHandler] ex", e);
        return new ErrorResult("500", "메일 전송 오류");   // mail exception is abstract; cannot be instatied? 뭐임?> 이게?
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MessagingException.class)
    public ErrorResult messagingExHandler(MessagingException e) {
        log.error("[MessagingExHandler] ex", e);
        return new ErrorResult("500", "메일 설정 오류");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult nullExHandler(NullPointerException e) {
        log.error("[NullPointerExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[ExceptionHandler] ex", e);
        return new ErrorResult("500", "ServerError");
    }
}
