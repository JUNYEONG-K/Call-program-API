package fis.police.fis_police_server.error.advice;

import fis.police.fis_police_server.controller.controllerImpl.ScheduleControllerImpl;
import fis.police.fis_police_server.controller.controllerImpl.UserControllerImpl;
import fis.police.fis_police_server.error.error_result.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = UserControllerImpl.class)
public class UserControllerAdvice {

    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalStateExHandler(IllegalStateException e) {
        log.error("[IllegalStateExHandler] ex", e);
        return new ErrorResult("402", e.getMessage());
    }
}
