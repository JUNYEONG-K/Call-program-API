package fis.police.fis_police_server.service.serviceImpl;

import ch.qos.logback.core.spi.PreSerializationTransformer;
import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.domain.enumType.AgentStatus;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.domain.enumType.HasCar;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.repository.AgentRepository;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.repository.ScheduleRepository;
import fis.police.fis_police_server.repository.UserRepository;
import fis.police.fis_police_server.service.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/*
    작성날짜: 2022/01/12 4:42 PM
    작성자: 이승범
    작성내용: ScheduleServiceImpl 생성
*/
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CenterRepository centerRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    @Override
    @Transactional
    public Schedule assignAgent(ScheduleSaveRequest request, Long userId) {
        Center findCenter = centerRepository.findById(request.getCenter_id());
        User findUser = userRepository.findById(userId);
        Agent findAgent = agentRepository.findById(request.getAgent_id());
        Schedule schedule = Schedule.createSchedule(findCenter, findUser, findAgent, request);
        scheduleRepository.save(schedule);
        return schedule;
    }

    /*
        작성날짜: 2022/01/13 5:54 PM
        작성자: 이승범
        작성내용: 날짜별 현장요원 스케줄 가져오기 구현
    */
    @Override
    public List<ScheduleByDateResponse> selectDate(LocalDate date) {
        return scheduleRepository.findAllByDate(date);
    }

    /*
        작성날짜: 2022/01/14 4:40 PM
        작성자: 이승범
        작성내용: modifySchedule 작성
    */
    @Override
    @Transactional
    public Schedule modifySchedule(ScheduleModifyRequest request) throws NullPointerException {
        Schedule findSchedule = scheduleRepository.findById(request.getSchedule_id());
        List<Agent> findAgentList = agentRepository.findByA_code(request.getA_code());
        // 해당하는 현장요원이 존재하는지 검사
        if (findAgentList.isEmpty()) {
            throw new NullPointerException();
        }
        Agent findAgent = findAgentList.get(0);
        Center findCenter = centerRepository.findById(request.getCenter_id());
        findSchedule.modifySchedule(request, findAgent, findCenter);
        return findSchedule;
    }

    @Override
    public Schedule findById(Long id) {
        try {
            return scheduleRepository.findById(id);
        } catch (NullPointerException e) {
            throw new NullPointerException("해당 스케쥴 없음.");
        }
    }

    @Override
    public List<Schedule> findSameSchedule(Long schedule_id) {
        Schedule schedule = scheduleRepository.findById(schedule_id);
        return scheduleRepository.findSameSchedule(schedule.getCenter().getC_name(), schedule.getVisit_date());
    }

    /*
        작성날짜: 2022/01/19 4:39 PM
        작성자: 이승범
        작성내용: 스케줄 취소 구현
    */
    @Override
    @Transactional
    public void cancelSchedule(Long schedule_id) {
        Schedule findSchedule = scheduleRepository.findById(schedule_id);
        findSchedule.cancel();
    }

    /*
        작성 날짜: 2022/03/23 2:47 오후
        작성자: 고준영
        작성 내용: 해당 스케쥴 방문 완료
    */
    @Override
    @Transactional
    public void updateSchedule(Long schedule_id) {
        scheduleRepository.updateScheduleComplete(schedule_id, Complete.complete);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
        날짜 : 2022/02/11 1:38 오후
        작성자 : 원보라
        작성내용 : 앱
    */
    // 방문 예정 일정들 -center 화면
    @Value("${profileImg.path}")
    private String uploadFolder;

    @Override
    @Transactional
    public List<AppScheduleCenterResponse> findByCenter(Long center_id, LocalDate today) throws IOException {
        List<AppScheduleCenterResponse> allList = scheduleRepository.findByCenter(center_id, today);

        for (AppScheduleCenterResponse appScheduleCenterResponse : allList) {
            Long agent_id = appScheduleCenterResponse.getAgent_id();
            Agent agent = agentRepository.findById(agent_id);
            String a_picture = agent.getA_picture();

            if (a_picture != null) {
                InputStream imageStream = new FileInputStream(uploadFolder + a_picture);
                byte[] imageByteArray = IOUtils.toByteArray(imageStream);
                imageStream.close();

                String encodedImage = Base64.encodeBase64String(imageByteArray); //Base64로 인코딩
//            ResponseEntity<byte[]> byte_picture = new ResponseEntity<>(imageByteArray, HttpStatus.OK);
                appScheduleCenterResponse.setA_picture(encodedImage);
            }
        }
        return allList;
    }

    //시설 - 방문 예정 현장요원 위도 경도
    @Override
    @Transactional
    public List<AgentLocation> findAgentLocation(Long center_id, LocalDate today) {
        return scheduleRepository.findAgentLocation(center_id, today);
    }


    //현장요원 - 오늘 방문 일정
    @Override
    @Transactional
    public List<AppScheduleAgentResponse> findByAgent(Long agent_id, LocalDate today) {
        return scheduleRepository.findByAgent(agent_id, today);
    }

    //현장요원 - 늦은 사유 업데이트
    @Override
    @Transactional
    public Long updateLateComment(AppLateCommentRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getSchedule_id());
        schedule.updateLateComment(request.getLate_comment());
        return schedule.getId();
    }

    //현장요원 - 아직 수락/거절이 정해지지않은 스케줄 리스트
    @Override
    @Transactional
    public List<AppScheduleResponse> findByAgentIncompleteSchedule(Long agent_id) {
        return scheduleRepository.findByAgentIncompleteSchedule(agent_id);
    }

    //현장요원 - 수락/거절 버튼 누를 시 update
    @Override
    @Transactional
    public void updateAccept(AppAcceptScheduleRequest request) {
//        Schedule schedule = scheduleRepository.findById(request.getSchedule_id());
//        schedule.updateAccept(request.getAccept());
        request.getSchedule_id().stream().forEach(id -> (scheduleRepository.findById(id)).updateAccept(request.getAccept()));
    }

    //현장요원 - 확정된 예정 스케줄 리스트
    @Override
    public List<AppScheduleResponse> findByAgentAllSchedule(Long agent_id, LocalDate today) {
        return scheduleRepository.findByAgentAllSchedule(agent_id, today);
    }

    @Override
    public List<AppScheduleResponse> findByAgentOldSchedule(Long agent_id, LocalDate today) {
        // 내노력의 시간 안뇽,,,, 담에 참고할거니까 안지울거임 ㅜㅜㅠㅜ
//        List<AppScheduleResponse> list = scheduleRepository.findByAgentOldSchedule(agent_id, today);
//        List<LocalDate> date = list.stream().map(s -> s.getVisit_date()).collect(Collectors.toList());
//        Set<LocalDate> dateSet = new HashSet<>(date);
//        List<LocalDate> dateList = new ArrayList<>(dateSet);
//        Collections.sort(dateList, Collections.reverseOrder()); //최신 일정이 위로오게
//        List<AppAgentOldSche> CombineScheduleList = new ArrayList<AppAgentOldSche>();
//        for (LocalDate visit_date : dateList) { //같은 날짜 일정 묶기
//            List<AppScheduleResponse> date_list = list.stream().filter(s ->s.getVisit_date().isEqual(visit_date)).collect(Collectors.toList());
//            AppAgentOldSche sche = new AppAgentOldSche();
//            sche.setList(date_list);
//            sche.setVisit_date(visit_date);
//            CombineScheduleList.add(sche);
//        }
//        return CombineScheduleList;
        return scheduleRepository.findByAgentOldSchedule(agent_id, today);
    }
}
