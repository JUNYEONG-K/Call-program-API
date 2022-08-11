package fis.police.fis_police_server.error.advice;

import fis.police.fis_police_server.error.error_result.ErrorResult;
import fis.police.fis_police_server.service.serviceImpl.TokenServiceImpl;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
    작성 날짜: 2022/02/18 2:47 오후
    작성자: 고준영
    작성 내용: Token Service 예외 처리
*/
@Slf4j
@RestControllerAdvice(assignableTypes = TokenServiceImpl.class)
public class TokenServiceAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public ErrorResult jwtExHandler(JwtException e) {
        log.error("[JwtExHandler] ex", e);
        return new ErrorResult("401", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("500", "ServerError");
    }
}
