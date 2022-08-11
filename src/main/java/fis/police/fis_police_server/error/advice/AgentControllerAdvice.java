package fis.police.fis_police_server.error.advice;

import fis.police.fis_police_server.controller.controllerImpl.AgentControllerImpl;
import fis.police.fis_police_server.error.error_result.ErrorResult;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.io.FileNotFoundException;


@Slf4j
@RestControllerAdvice(assignableTypes = AgentControllerImpl.class)
public class AgentControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public ErrorResult jwtExHandler(JwtException e) {
        log.error("[JwtExHandler] ex", e);
        return new ErrorResult("401", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalStateExHandler (IllegalStateException e) {
        log.error("[IllegalStateExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(RestClientException.class)
    public ErrorResult restClientExHandler(RestClientException e) {
        log.error("[naver map api request error] ex", e);
        return new ErrorResult("501", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(ParseException.class)
    public ErrorResult parseExHandler(ParseException e) {
        log.error("[naver map api parsing error] ex", e);
        return new ErrorResult("502", "naver map 파싱 에러");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ErrorResult indexOutOfBoundsExHandler(IndexOutOfBoundsException e) {
        log.error("[address incorrect error] ex", e);
        return new ErrorResult("403", e.getMessage());
    }

    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    @ExceptionHandler(TransactionSystemException.class)
    public ErrorResult transactionExHandler(TransactionSystemException e) {
        log.error("[request data uncompleted error] ex", e);
        return new ErrorResult("402", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult nullExHandler (NullPointerException e) {
        log.error("[nullExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileNotFoundException.class)
    public ErrorResult fileNotFoundException (FileNotFoundException e) {
        log.error("[FileNotFoundException] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("500", "ServerError");
    }
}
