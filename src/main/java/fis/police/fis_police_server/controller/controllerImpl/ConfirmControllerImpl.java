package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.ConfirmController;
import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.service.ConfirmService;
import fis.police.fis_police_server.service.ScheduleService;
import fis.police.fis_police_server.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.List;


/*
    작성 날짜: 2022/02/14 11:32 오전
    작성자: 고준영
    작성 내용: 확인서 제출(저장), 확인서 조회, 확인서 결재
*/

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/app")
public class ConfirmControllerImpl implements ConfirmController {

    private final ConfirmService confirmService;
    private final TokenService tokenService;
    private final ScheduleService scheduleService;

    // 현장요원이 확인서 작성하여 제출 (확인서 정보 + 현장요원 정보) 현장요원 별 하나씩
    @Override
    @PostMapping("/confirm/write/{schedule_id}")
    public WellSaveResponse postConfirm(@RequestBody ConfirmFromAgentRequest formRequest, HttpServletRequest request, @PathVariable Long schedule_id) {
        String authorizationHeader = request.getHeader("Authorization");
        Agent agent = tokenService.getAgentFromRequest(authorizationHeader);    // jwt token exception in tokenService -> 따로 잡을 필요 없음.
        log.info("[로그인 id값: {}] [url: {}] [요청: 확인서 저장]", agent.getId(), "/confirm/write/" + schedule_id);
        log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));

        try {
            Schedule schedule = scheduleService.findById(schedule_id);
            return confirmService.saveConfirm(formRequest, schedule);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoSchedule");
        }
    }

    // 스케쥴별 확인서 열람 (작성 안되어 있으면 오류남)
    @Override
    @GetMapping("/confirm/{schedule_id}")
    public ConfirmFormResponse confirmBySchedule(HttpServletRequest request, @PathVariable Long schedule_id) {
        String authorizationHeader = request.getHeader("Authorization");
        log.info("[url: {}] [요청: 확인서 조회]", "/confirm/" + schedule_id);
        log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));

        try {
            Schedule schedule = scheduleService.findById(schedule_id);
            Center center = schedule.getCenter();
            String visit_date = String.valueOf(schedule.getVisit_date());
            return confirmService.showConfirm(center, visit_date);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("NoConfirm");
        } catch (NullPointerException e){
            throw new NullPointerException("NoConfirm");
        }
    }

    // 시설이 확인서에 결재 후 전송 => 확인서의 '확인' 컬럼 업데이트
    @Override
    @PostMapping("/confirm/check/{schedule_id}")
    public WellSaveResponse updateConfirmComplete(@RequestBody UpdateRequest request, @PathVariable Long schedule_id, HttpServletRequest servletRequest) throws IllegalAccessException {
        String authorizationHeader = servletRequest.getHeader("Authorization");
        Officials officialFromRequest = tokenService.getOfficialFromRequest(authorizationHeader);
        log.info("[로그인 id값: {}] [url: {}] [요청: 확인서 결재]", tokenService.getOfficialFromRequest(authorizationHeader).getId(), "/confirm/check");
        log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));

        try {
            List<Long> confirm_id = request.getConfirm_id();
            for (Long aLong : confirm_id) {
                log.info("[확인서 id값: {} [url: {}] [요청: 확인서 결재]", aLong, "/confirm/check");
                confirmService.updateConfirm(schedule_id, aLong, officialFromRequest.getO_name());
            }
            List<Schedule> sameSchedule = scheduleService.findSameSchedule(schedule_id);
            for (Schedule schedule : sameSchedule) {
                scheduleService.updateSchedule(schedule.getId());
            }
            return new WellSaveResponse("200", "updated");
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (HttpMessageNotReadableException e) {
            throw new HttpMessageNotReadableException("NoConfirm", (HttpInputMessage) request);
        } catch (NullPointerException e) {
            throw new NullPointerException("NoConfirm");
        }
    }

    // /confirm -> 시설용 과거 방문 이력들
    // todo (0214 적음) 같은 시설, 같은 날짜에 방문한 정보들을 하나로 통합해야함
    @Override
    @GetMapping("/confirm/center")
    public Result confirmListForCenter(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        Officials officialFromRequest = tokenService.getOfficialFromRequest(authorization);
        log.info("[로그인 id값: {}] [url: {}] [요청: 시설용 과거 방문 일정 조회]", tokenService.getOfficialFromRequest(authorization).getId(), "/confirm/center");
        log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorization).get("role"));

        try {
            Long center_id = officialFromRequest.getCenter().getId();
            return confirmService.confirmForCenter(center_id);
        } catch (NullPointerException e) {
            throw new NullPointerException("NoVisited");
        }
    }

    // /confirm/calendar ->현장요원별 확인서 제출완료 날짜 + 근무 예정 날짜
    @Override
    @GetMapping("confirm/calendar")
    public CalendarResponse confirmDay(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        Agent agentFromRequest = tokenService.getAgentFromRequest(authorizationHeader);
        log.info("[로그인 id값: {}] [url: {}] [요청: 한장요원 근무일자 조회]", tokenService.getAgentFromRequest(authorizationHeader).getId(), "/confirm/calendar");
        log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));
        return confirmService.completeDayForAgent(agentFromRequest);
    }
}

