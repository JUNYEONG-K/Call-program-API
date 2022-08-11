package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.dto.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

// 이승범영
// 원보라 - 앱
public interface ScheduleController {

        // 현장요원 배치
        void assignAgent(ScheduleSaveRequest scheduleSaveRequest, HttpServletRequest httpServletRequest, HttpServletResponse response);

        // 날짜 별 현장요원 일정 리스트
        Result selectDate(LocalDate date, HttpServletResponse response, HttpServletRequest httpServletRequest);

        // 일정 수정
        void modifySchedule(ScheduleModifyRequest scheduleModifyRequest, HttpServletResponse response, HttpServletRequest httpServletRequest);

        // 일정 취소
        void cancelSchedule(Long schedule_id, HttpServletResponse response, HttpServletRequest httpServletRequest);


        /*
            날짜 : 2022/02/11 10:29 오전
            작성자 : 원보라
            작성내용 : 앱 schedule
        */
        //시설에 띄워줄 예약내역 리스트
        List<AppScheduleCenterResponse> confirmSchedule(HttpServletRequest httpServletRequest) throws IOException; //시설 담당자의 시설 정보를 꺼내와야함

        //시설 - 방문 예정 현장요원 위도 경도
        List<AgentLocation> findAgentLocation(HttpServletRequest httpServletRequest);

        //현장요원 앱 메인화면에 띄워줄 오늘의 스케쥴 일정
        List<AppScheduleAgentResponse> agentTodaySchedule(HttpServletRequest httpServletRequest);       //현장요원 id 꺼내야함

        //schedule 의 late_comment 컬럼 update
        public void updateLateComment(AppLateCommentRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response);

        //아직 수락/거절이 정해지지않은 스케줄 리스트
        List<AppScheduleResponse> incompleteSchedule(HttpServletRequest httpServletRequest);

        //수락/거절 update
        void updateAcceptSchedule(AppAcceptScheduleRequest request,HttpServletRequest httpServletRequest, HttpServletResponse response);

        //수락된 예정 일정 열람
        List<AppScheduleResponse> agentSchedule(HttpServletRequest httpServletRequest);

        //현장요원 과거 일정
        List<AppScheduleResponse> agentOldSchedule(HttpServletRequest httpServletRequest);
}

