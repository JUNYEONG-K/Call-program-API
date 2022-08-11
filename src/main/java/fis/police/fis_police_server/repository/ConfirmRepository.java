package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Confirm;
import fis.police.fis_police_server.domain.enumType.Complete;

import java.util.List;

/*
    작성 날짜: 2022/02/14 11:44 오전
    작성자: 고준영
    작성 내용: 확인서 저장, 조회
*/
public interface ConfirmRepository {

    // 확인서 제출 후 저장 (요원별로 저장)
    void save(Confirm confirm);

    Confirm findById(Long id);

    // 해당 스케쥴에 대한 확인서(결재 상관 없이 일단 뽑음) 조회 (만약 해당 스케쥴을 2명 이상이 수행하여, 확인서가 2개 이상 나오기 때문에 list 처리. service 에서 하나로 봉합해야함)
    List<Confirm> findSameCenterDate(Center center, String visit_date);

    // 시설 담당자의 확인서 결재 (확인서의 컬럼 값을 complete 로 바꿔주기)
    void updateConfirmComplete(Long confirm_id, Complete complete, String name);

    // 과거 방문 이력 조회 (결재가 완료된 방문 이력을 모두 조회한다. 이때, 한 스케쥴을 2명 이상이 처리한 경우 하나의 confirm 으로 묶어주는 작업을 service 에서 진행)
    List<Confirm> findCompleteConfirmListForCenter(Center center);

    // 현장요원별에 대한 모든 확인서 (결재 완료인)
    List<Confirm> findCompleteConfirmListForAgent(Complete complete, Agent agent);
}
