package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
    작성 날짜: 2022/02/14 11:32 오전
    작성자: 고준영
    작성 내용: 확인서 제출(저장), 확인서 조회, 확인서 결재
*/
public interface ConfirmController {
    // 현장요원 -> 확인서 제출
    WellSaveResponse postConfirm(ConfirmFromAgentRequest formRequest, HttpServletRequest request, @PathVariable Long schedule_id);

    // 스케쥴에 해당하는 확인서 열람
    ConfirmFormResponse confirmBySchedule(HttpServletRequest request, @PathVariable Long schedule_id);

    // /confirm/check -> 시설이 확인서에 결재 후 전송 => 확인서의 '확인' 컬럼 업데이트
    WellSaveResponse updateConfirmComplete(@RequestBody UpdateRequest request, @PathVariable Long schedule_id, HttpServletRequest servletRequest) throws IllegalAccessException; // param 을 따로 dto 로 묶을 필요가 있음

    // /confirm -> 시설용 과거 방문 이력들
    Result confirmListForCenter(HttpServletRequest request);  // request 에서 로그인한 사용자 정보(시설 담당자 id) 꺼내와서 그 사람의 시설 id로 confirm list 찾기

    // /confirm/calendar -> 현장요원별 확인서가 제출된 날짜만 출력? 이건 아직 미정
    CalendarResponse confirmDay(HttpServletRequest request);
}
