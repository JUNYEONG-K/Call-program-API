package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.ScheduleController;
import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.service.ScheduleService;
import fis.police.fis_police_server.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/*
    작성날짜: 2022/01/12 4:20 PM
    작성자: 이승범
    작성내용: ScheduleControllerImpl 생성
*/
@RestController
@RequiredArgsConstructor
@Slf4j
public class ScheduleControllerImpl implements ScheduleController {

    private final ScheduleService scheduleService;
    private final TokenService tokenService;


    /*
        작성날짜: 2022/01/13 1:40 PM
        작성자: 이승범
        작성내용: assignAgent 작성
    */
    @Override
    @PostMapping("/schedule")// 현장요원 배치
    public void assignAgent(
            @RequestBody ScheduleSaveRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            HttpSession session = httpServletRequest.getSession();
            Long userId = (Long) session.getAttribute("loginUser");
            scheduleService.assignAgent(request, userId);
        } catch (NullPointerException npe) {
            log.warn("[로그인 id값 : {}] [url: {}] [식별되지않는 시설 or 현장요원 or 콜직원 {}]",
                    httpServletRequest.getSession().getAttribute("loginUser"), "/schedule", npe.getMessage());
            response.setStatus(400);
        } catch (Exception e) {
            log.error("[로그인 id값 : {}] [url: {}] [예상치못한 에러 {}]",
                    httpServletRequest.getSession().getAttribute("loginUser"), "/schedule", e.getMessage());
            response.setStatus(500);
        }
    }

    /*
        작성날짜: 2022/01/13 1:44 PM
        작성자: 이승범
        작성내용: selectDate 작성 날짜별 스케줄
    */
    @Override
    @GetMapping("/schedule")
    public Result selectDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        try {
            return new Result(scheduleService.selectDate(date));
        } catch (Exception e) {
            log.error("[로그인 id값 : {}] [url: {}] [예상치못한 에러 {}]",
                    httpServletRequest.getSession().getAttribute("loginUser"), "/schedule", e.getMessage());
            response.setStatus(500);
            return null;
        }
    }

    /*
        작성날짜: 2022/01/14 4:33 PM
        작성자: 이승범
        작성내용: 스케줄 수정
    */
    @Override
    @PatchMapping("/schedule")
    public void modifySchedule(@RequestBody ScheduleModifyRequest request, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        try {
            scheduleService.modifySchedule(request);
        } catch (NullPointerException ne) {
            log.warn("[로그인 id 값 : {}] [url: {}] [존재하지 않는 코드 or id입니다. {}]",
                    httpServletRequest.getSession().getAttribute("loginUser"), "/schedule", ne.getMessage());
            response.setStatus(400);
        } catch (Exception e) {
            log.error("[로그인 id값 : {}] [url: {}] [예상치못한 에러 {}]",
                    httpServletRequest.getSession().getAttribute("loginUser"), "/schedule", e.getMessage());
            response.setStatus(500);
        }
    }

    /*
        작성날짜: 2022/01/19 4:33 PM
        작성자: 이승범
        작성내용: 스케줄 취소 구현
    */
    @Override
    @GetMapping("/schedule/cancel")
    public void cancelSchedule(@RequestParam("schedule_id") Long schedule_id, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        try {
            scheduleService.cancelSchedule(schedule_id);
        } catch (NullPointerException npe) {
            log.warn("[로그인 id값 : {}] [url: {}] [존재하지않는 스케쥴 id {}]",
                    httpServletRequest.getSession().getAttribute("loginUser"), "/schedule/cancel", npe.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            log.error("[로그인 id값 : {}] [url: {}] [예상치못한 에러 {}]",
                    httpServletRequest.getSession().getAttribute("loginUser"), "/schedule/cancel", e.getMessage());
            response.setStatus(500);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
           날짜 : 2022/02/11 10:29 오전
           작성자 : 원보라
           작성내용 : 앱 schedule
    */
    //시설에 띄워줄 예약내역 리스트
    @Override
    @GetMapping(value = "/app/schedule/confirm")
    public List<AppScheduleCenterResponse> confirmSchedule(HttpServletRequest httpServletRequest) throws IOException {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            Officials officialFromRequest = tokenService.getOfficialFromRequest(authorizationHeader);
            Long center_id = officialFromRequest.getCenter().getId();
            log.info("[로그인 id값: {}] [url: {}] [요청: 시설 예약 내역 조회 ]", tokenService.getOfficialFromRequest(authorizationHeader).getId(), "/app/schedule/confirm");
            log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));
            return scheduleService.findByCenter(center_id, LocalDate.now());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoOfficial");
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            throw new FileNotFoundException("NoSuchFile");
        }
    }


    //시설 - 방문 예정 현장요원 위도 경도
    @Override
    @GetMapping(value = "/app/schedule/location")
    public List<AgentLocation> findAgentLocation(HttpServletRequest httpServletRequest) {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            Officials officialFromRequest = tokenService.getOfficialFromRequest(authorizationHeader);
            Long center_id = officialFromRequest.getCenter().getId();
            log.info("[로그인 id값: {}] [url: {}] [요청: 방문 예정 현장요원 위도 경도]", tokenService.getOfficialFromRequest(authorizationHeader).getId(), "/app/schedule/location");
            log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));
            List<AgentLocation> list = scheduleService.findAgentLocation(center_id, LocalDate.now());
            if (!list.isEmpty()) {
                for (AgentLocation agentLocation : list) {
                    log.info("방문 예정 현장요원 id ====={} 위도====={} 경도 ====={}", agentLocation.getAgent_id(), agentLocation.getA_cur_lat(), agentLocation.getA_cur_long());
                }
            }
            return list;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoOfficial");
        }
    }


    //현장요원 앱 메인화면에 띄워줄 오늘의 스케쥴 일정
    @Override
    @GetMapping("/app/schedule/today")
    public List<AppScheduleAgentResponse> agentTodaySchedule(HttpServletRequest httpServletRequest) {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            Long agent_id = tokenService.getAgentFromRequest(authorizationHeader).getId();
            log.info("[로그인 id값: {}] [url: {}] [요청: 한장요원 오늘 일정 조회]", tokenService.getAgentFromRequest(authorizationHeader).getId(), "/app/schedule/today");
            log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));
            return scheduleService.findByAgent(agent_id, LocalDate.now());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoAgent");
        }
    }

    //schedule 의 late_comment 컬럼 update
    @Override
    @PostMapping("/app/schedule/late")
    public void updateLateComment(@RequestBody AppLateCommentRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            //성공시 200 ok
            log.info("늦는 사유 update");
            scheduleService.updateLateComment(request);
        } catch (NullPointerException ne) {
            log.warn("[로그인 id값 : 보류] [url: /schedule/late] [존재하지않는 스케쥴 id {}]", ne.getMessage());
            response.setStatus(400);
        } catch (Exception e) {
            log.error("[로그인 id값 : 보류] [url: /schedule/late] [예상치못한 에러 {}]", e.getMessage());
            response.setStatus(500);
        }
    }

    //아직 수락/거절이 정해지지않은 스케쥴 리스트
    @Override
    @GetMapping("/app/schedule/incomplete")
    public List<AppScheduleResponse> incompleteSchedule(HttpServletRequest httpServletRequest) {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            Long agent_id = tokenService.getAgentFromRequest(authorizationHeader).getId();
            log.info("[로그인 id값: {}] [url: {}] [요청: 한장요원 TBD인 일정 조회]", tokenService.getAgentFromRequest(authorizationHeader).getId(), "/app/schedule/incomplete");
            log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));
            return scheduleService.findByAgentIncompleteSchedule(agent_id);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoAgent");
        }
    }

    //수락/거절 update
    @Override
    @PostMapping("/app/schedule/accept")
    public void updateAcceptSchedule(@RequestBody AppAcceptScheduleRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            //성공시 200 ok
            log.info("일정 수락/거절 update");
            scheduleService.updateAccept(request);
        } catch (NullPointerException ne) {
            log.warn("[url: app/schedule/accept] [존재하지않는 스케쥴 id {}]", ne.getMessage());
            response.setStatus(400);
        } catch (Exception e) {
            log.error("[url: app/schedule/accept] [예상치못한 에러 {}]", e.getMessage());
            response.setStatus(500);
        }
    }

    //수락된 예정 일정 열람
    @Override
    @GetMapping("/app/schedule/agent")
    public List<AppScheduleResponse> agentSchedule(HttpServletRequest httpServletRequest) {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            Long agent_id = tokenService.getAgentFromRequest(authorizationHeader).getId();
            log.info("[로그인 id값: {}] [url: {}] [요청: 한장요원 수락한 일정 조회]", tokenService.getAgentFromRequest(authorizationHeader).getId(), "/app/schedule/agent");
            log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));
            return scheduleService.findByAgentAllSchedule(agent_id, LocalDate.now());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoAgent");
        }
    }

    //현장요원 완료된 일정
    @Override
    @GetMapping("/app/schedule/old")
    public List<AppScheduleResponse> agentOldSchedule(HttpServletRequest httpServletRequest) {
        try {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            Long agent_id = tokenService.getAgentFromRequest(authorizationHeader).getId();
            log.info("[로그인 id값: {}] [url: {}] [요청: 한장요원 완료된 일정 조회]", tokenService.getAgentFromRequest(authorizationHeader).getId(), "/app/schedule/agent");
            log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));
            return scheduleService.findByAgentOldSchedule(agent_id, LocalDate.now());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoAgent");
        }
    }
}
