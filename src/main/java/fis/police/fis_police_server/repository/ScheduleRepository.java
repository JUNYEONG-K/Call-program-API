package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository {

    void save(Schedule schedule);

    Schedule findById(Long id);
    List<Schedule> findSameSchedule(String c_name, LocalDate visit_date);
    /*
        작성날짜: 2022/01/13 4:13 PM
        작성자: 이승범
        작성내용: findAllByDate 작성
    */
    List<ScheduleByDateResponse> findAllByDate(LocalDate date);

    /*
        날짜 : 2022/02/11 11:47 오전
        작성자 : 원보라
        작성내용 : 앱
    */
    //시설 - 방문 예정 일정들
    List<AppScheduleCenterResponse> findByCenter(Long center_id, LocalDate today);
//    List<AppScheduleFilterDTO> findByCenterFilter(Long center_id, LocalDate today);
//    List<Agent> findBySameSchedule(LocalDate visit_date, LocalTime visit_time, Long center_id);
    //시설화면 - 오늘방문하는 현장요원 위도 경도 리스트 넘겨줌
    List<AgentLocation> findAgentLocation(Long center_id, LocalDate today);

    //현장요원 - 오늘 방문 일정
    List<AppScheduleAgentResponse> findByAgent(Long agent_id, LocalDate today);

    //현장요원 - 아직 수락/거절이 정해지지않은 스케줄 리스트
    List<AppScheduleResponse> findByAgentIncompleteSchedule(Long agent_id);

    //현장요원 - 확정된 예정 스케줄 리스트
    List<AppScheduleResponse> findByAgentAllSchedule(Long agent_id, LocalDate today);

    //현장요원 - 완료된 과거 스케줄 리스트
    List<AppScheduleResponse> findByAgentOldSchedule(Long agent_id, LocalDate today);

    void updateScheduleComplete(Long schedule_id, Complete complete);
    void updateScheduleWaiting(Long schedule_id, Complete complete);


}
