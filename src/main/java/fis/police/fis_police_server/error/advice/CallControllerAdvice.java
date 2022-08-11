package fis.police.fis_police_server.error.advice;

import fis.police.fis_police_server.error.error_result.ErrorResult;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;

/*
    작성 날짜: 2022/02/18 5:48 오후
    작성자: 고준영
    작성 내용: 통화 기록 저장 예외 처리
*/
@Slf4j
@RestControllerAdvice(assignableTypes = CallControllerAdvice.class)
public class CallControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult nullExHandler(NullPointerException e) {
        log.error("[NullPointerExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResultException.class)
    public ErrorResult noResultExHandler(NoResultException e) {
        log.error("[NoResultExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[ExceptionHandler] ex", e);
        return new ErrorResult("500", "ServerError");
    }
}
