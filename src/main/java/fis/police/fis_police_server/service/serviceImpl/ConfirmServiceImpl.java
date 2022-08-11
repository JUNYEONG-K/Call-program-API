package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.*;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.repository.*;
import fis.police.fis_police_server.service.AgentService;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.ConfirmService;
import fis.police.fis_police_server.service.OfficialService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfirmServiceImpl implements ConfirmService {

    private final ConfirmRepository confirmRepository;
    private final CenterService centerService;
    private final AgentService agentService;
    private final ScheduleRepository scheduleRepository;
    private final OfficialService officialService;

    // 확인서 저장
    @Override
    public WellSaveResponse saveConfirm(ConfirmFromAgentRequest request, Schedule schedule) {
        Confirm confirm = Confirm.createConfirm(request, schedule);
        confirmRepository.save(confirm);
        scheduleRepository.updateScheduleWaiting(schedule.getId(), Complete.waiting);
        return new WellSaveResponse("200", "created");
    }

    // 확인서 하나로 묶기
    @Override
    public ConfirmFormResponse combineConfirm(List<Confirm> dupleList) {
        ConfirmFormResponse response = new ConfirmFormResponse();

        // 따로 메서드 파는 걸 추천함... 코드가 너무 지저분;
        response.setCenter_name(dupleList.get(0).getCenter().getC_name());
        response.setCenter_address(dupleList.get(0).getCenter().getC_address());
        response.setCenter_ph(dupleList.get(0).getCenter().getC_ph());
        response.setManager_name(dupleList.get(0).getCenter_manger());
        response.setVisit_date(dupleList.get(0).getVisit_date());
        response.setVisit_time(dupleList.get(0).getVisit_time());
        response.setNew_child(dupleList.get(0).getNew_child());
        response.setOld_child(dupleList.get(0).getOld_child());
        response.setSenile(dupleList.get(0).getSenile());
        response.setDisabled(dupleList.get(0).getDisabled());
        response.setEtc(dupleList.get(0).getEtc());
        response.setComplete(dupleList.get(0).getComplete());

        for (Confirm confirm : dupleList) {
            response.getAgent_name().add(confirm.getAgent().getA_name());
            response.getConfirm_id().add(confirm.getId());
        }

        return response;
    }

    // 확인서 결재하기
    @Override
    public void updateConfirm(Long schedule_id, Long confirm_id, String name) throws IllegalAccessException {
        Complete complete = Complete.complete;
        Confirm confirm = confirmRepository.findById(confirm_id);
        if (confirm.getComplete() == Complete.complete) {
            throw new IllegalAccessException("AlreadyCompleted");
        }
        confirmRepository.updateConfirmComplete(confirm_id, complete, name);
        scheduleRepository.updateScheduleComplete(schedule_id, complete);
    }

    // [방문이력 조회] 시설별 확인서 조회 (모두)
    @Override
    public Result confirmForCenter(Long center_id) {
        Center center = centerService.findById(center_id);
        List<Confirm> completeConfirmListForCenter = confirmRepository.findCompleteConfirmListForCenter(center);
        List<ConfirmDTO> collect = completeConfirmListForCenter.stream()
                .map(confirm -> new ConfirmDTO(confirm.getId(), confirm.getVisit_date(), confirm.getVisit_time(), confirm.getCenter().getC_name(),
                        confirm.getAgent().getA_name(), confirm.getNew_child(), confirm.getOld_child(), confirm.getSenile(), confirm.getDisabled(),
                        confirm.getEtc(), confirm.getComplete()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    // 해당 스케쥴에 대한 확인서 열람 (시설, 현장요원 모두)
    @Override
    public ConfirmFormResponse showConfirm(Center center, String visit_date) {
        List<Confirm> sameCenterDate = confirmRepository.findSameCenterDate(center, visit_date);
        return combineConfirm(sameCenterDate);
    }

    @Override
    public Result confirmForAgent(Agent agent) {
        Complete complete = Complete.complete;
        List<Confirm> completeConfirmListForAgent = confirmRepository.findCompleteConfirmListForAgent(complete, agent);
        List<CalendarDTO> collect = completeConfirmListForAgent.stream()
                .map(confirm -> new CalendarDTO(confirm.getAgent().getA_name(), confirm.getCenter().getC_name(),
                        confirm.getVisit_date(), confirm.getComplete()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Override
    public CalendarResponse completeDayForAgent(Agent agent) {
        Complete complete = Complete.complete;
        List<Confirm> completeConfirmListForAgent = confirmRepository.findCompleteConfirmListForAgent(complete, agent);
        List<AppScheduleResponse> byAgentAllSchedule = scheduleRepository.findByAgentAllSchedule(agent.getId(), LocalDate.now());
        CalendarResponse response = new CalendarResponse();
        response.setAgent_name(agent.getA_name());
        for (Confirm confirm : completeConfirmListForAgent) {
            response.getVisited_date().add(confirm.getVisit_date());
        }
        for (AppScheduleResponse appScheduleResponse : byAgentAllSchedule) {
            response.getWill_go_date().add(appScheduleResponse.getVisit_date().toString());
        }
        return response;
    }

}
