package fis.police.fis_police_server.error.advice;

import fis.police.fis_police_server.controller.controllerImpl.ConfirmControllerImpl;
import fis.police.fis_police_server.error.error_result.ErrorResult;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/*
    작성 날짜: 2022/02/18 2:47 오후
    작성자: 고준영
    작성 내용: ConfirmController 에외 처리
*/
@Slf4j
@RestControllerAdvice(assignableTypes = ConfirmControllerImpl.class)
public class ConfirmControllerAdvice {

    // 토큰 만료 됐을 때
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public ErrorResult jwtExHandler(JwtException e) {
        log.error("[JwtExHandler] ex", e);
        return new ErrorResult("401", e.getMessage());
    }

    // 디비에 정보가 없을 때
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult nullExHandler (NullPointerException e) {
        log.error("[nullExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    // 확인서 없을 때 발생하는 걸로 해놓긴 했는데, 잘 모르겄음
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ErrorResult indexOutExHandler (IndexOutOfBoundsException e) {
        log.error("[IndexOutOfBoundsExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    // 토큰을 보내지 않았을 때
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalStateExHandler (IllegalStateException e) {
        log.error("[IllegalStateExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    // request body 에 아무것도 없을 때
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult httpMessageNotReadableExHandler(HttpMessageNotReadableException e) {
        log.error("[HttpMessageNotReadableExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    // 이미 확인서 결재 됐을 때
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalAccessException.class)
    public ErrorResult illegalAccessExHandler (IllegalAccessException e) {
        log.error("[IllegalAccessExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    // 타입이 맞지 않을 때 (ex. want Long type but come String type)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ErrorResult numFormatExHandler (NumberFormatException e) {
        log.error("[NumberFormatExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResult methodArgTypeMismatchExHandler (MethodArgumentTypeMismatchException e) {
        log.error("[MethodArgTypeMismatchExHandler] ex", e);
        return new ErrorResult("400", "NoScheduleId");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("500", "ServerError");
    }
}
