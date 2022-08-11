package fis.police.fis_police_server.error.advice;

import fis.police.fis_police_server.controller.controllerImpl.CenterControllerImpl;
import fis.police.fis_police_server.controller.controllerImpl.ConfirmControllerImpl;
import fis.police.fis_police_server.error.error_result.ErrorResult;
import fis.police.fis_police_server.service.exceptions.DuplicateSaveException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

@Slf4j
@RestControllerAdvice(assignableTypes = CenterControllerImpl.class)
public class CenterControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult nullExHandler (NullPointerException e) {
        log.error("[nullExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResultException.class)
    public ErrorResult noResultExHandler(NoResultException e) {
        log.error("[NoResultExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NonUniqueResultException.class)
    public ErrorResult noUniqueResultExHandler(NonUniqueResultException e) {
        log.error("[NoUniqueExHandler] ex", e);
        return new ErrorResult("500", e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateSaveException.class)
    public ErrorResult duplicateExHandler(DuplicateSaveException e) {
        log.error("[DuplicateExHandler] ex", e);
        return new ErrorResult("409", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgExHandler(IllegalArgumentException e) {
        log.error("[IllegalArgExHandler] ex", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(RestClientException.class)
    public ErrorResult restClientExHandler(RestClientException e) {
        log.error("[naver map api request error] ex", e);
        return new ErrorResult("501", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult exHandler(Exception e) {
        log.error("[ExHandler] ex", e);
        return new ErrorResult("500", "ServerError");
    }
}
