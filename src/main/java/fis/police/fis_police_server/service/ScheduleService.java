package fis.police.fis_police_server.service;

import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.dto.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    // 현장요원 배치
    Schedule assignAgent(ScheduleSaveRequest scheduleSaveRequest, Long userId) throws Exception;

    // 날짜 별 현장요원 일정 리스트
    List<ScheduleByDateResponse> selectDate(LocalDate localDate);

    // 일정 수정
    Schedule modifySchedule(ScheduleModifyRequest request);

    Schedule findById(Long id);
    List<Schedule> findSameSchedule(Long schedule_id);

    /*
        작성날짜: 2022/01/19 4:31 PM
        작성자: 이승범
        작성내용: 일정취소 기능 추가
    */
    // 일정 취소
    void cancelSchedule(Long schedule_id);
    void updateSchedule(Long schedule_id);


    /*
        날짜 : 2022/02/11 11:42 오전
        작성자 : 원보라
        작성내용 : 앱 schedule
    */
    //시설 - 방문 예정 일정들
    List<AppScheduleCenterResponse> findByCenter(Long center_id,LocalDate today) throws IOException;

    //시설 - 방문 예정 현장요원 위도 경도
    List<AgentLocation> findAgentLocation(Long center_id, LocalDate today);


    //현장요원 - 오늘 방문 일정
    List<AppScheduleAgentResponse> findByAgent(Long agent_id, LocalDate today);

    //현장요원 - 늦는 사유 update 기본값 null 스케쥴 id 반환
    Long updateLateComment(AppLateCommentRequest request);

    //현장요원 - 아직 수락/거절이 정해지지않은 스케줄 리스트
    List<AppScheduleResponse> findByAgentIncompleteSchedule(Long agent_id);

    //현장요원 - 수락/거절 버튼 누를 시 update
    void updateAccept(AppAcceptScheduleRequest request);

    //현장요원 - 확정된 예정 스케줄 리스트
    List<AppScheduleResponse> findByAgentAllSchedule(Long agent_id, LocalDate today);

    //현장요원 - 완료된 과거 스케줄 리스트
    List<AppScheduleResponse> findByAgentOldSchedule(Long agent_id, LocalDate today);
}
