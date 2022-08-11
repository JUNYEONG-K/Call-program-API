package fis.police.fis_police_server.service;

import fis.police.fis_police_server.domain.*;
import fis.police.fis_police_server.dto.*;

import java.util.List;
/*
    작성 날짜: 2022/02/14 11:47 오전
    작성자: 고준영
    작성 내용: 확인서에 대한 서비스
*/
public interface ConfirmService {

    // 확인서 저장
    WellSaveResponse saveConfirm(ConfirmFromAgentRequest request, Schedule schedule) throws NullPointerException;

    // 확인서 하나로 묶기
    ConfirmFormResponse combineConfirm(List<Confirm> dupleList);

    // 확인서 결재하기
    void updateConfirm(Long confirm_id, Long schedule_id, String name) throws IllegalAccessException;

    // [방문이력 조회] 시설별 확인서 조회 (모두)
    Result confirmForCenter(Long center_id);

    // 해당 스케쥴에 대한 확인서 열람 (시설, 현장요원 모두)
    ConfirmFormResponse showConfirm(Center center, String visit_date);

    // [방문이력 조회] 요원별 확인서 조회 (모두)
    Result confirmForAgent(Agent agent);
    CalendarResponse completeDayForAgent(Agent agent);



}
