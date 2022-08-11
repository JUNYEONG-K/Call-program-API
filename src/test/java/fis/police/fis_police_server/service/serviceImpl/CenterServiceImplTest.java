package fis.police.fis_police_server.service.serviceImpl;

import com.mysema.commons.lang.Pair;
import fis.police.fis_police_server.domain.*;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.AgentSaveRequest;
import fis.police.fis_police_server.dto.CenterSearchDTO;
import fis.police.fis_police_server.dto.CenterSearchResponseDTO;
import fis.police.fis_police_server.repository.AgentRepository;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.service.AgentService;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.MapService;
import fis.police.fis_police_server.service.exceptions.DuplicateSaveException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class CenterServiceImplTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private CenterRepository centerRepository;
    @Autowired
    private CenterService centerService;
    @Autowired
    private MapService mapService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentRepository agentRepository;

    @Test
    @DisplayName("센터찾기 테스트")
    @Rollback
    void findCenterList() {

        Center center1 = new Center("시설이름 1", "서울", "001");
        Center center2 = new Center("시설이름 2", "서울", "002");
        Center center3 = new Center("시설이름 3", "의정부", "003");
        em.persist(center1);
        em.persist(center2);
        em.persist(center3);

        em.flush();
        em.clear();

        System.out.println("영속성 컨택스트 초기화");

        CenterSearchDTO centerSearchDTO1 = new CenterSearchDTO("시설이름 1", null, null);
        CenterSearchDTO centerSearchDTO2 = new CenterSearchDTO(null, null, null);
        CenterSearchDTO centerSearchDTO3 = new CenterSearchDTO("시설이름 4", "서울", null);
        System.out.println("1차 데이터 검색");
        List<CenterSearchResponseDTO> centerSearchResponseDTOList1 = centerService.findCenterList("시설이름 1", null, null);
        centerSearchResponseDTOList1.stream().forEach(e -> System.out.println("e = " + e));
        System.out.println("2차 데이터 검색");
        List<CenterSearchResponseDTO> centerSearchResponseDTOList2 = centerService.findCenterList(null, null, null);
        centerSearchResponseDTOList2.stream().forEach(e -> System.out.println("e = " + e));
        List<CenterSearchResponseDTO> centerSearchResponseDTOList3;
        try {
            System.out.println("3차 데이터 검색");
            centerSearchResponseDTOList3 = centerService.findCenterList("시설이름", "서울", null);
            centerSearchResponseDTOList3.stream().forEach(e -> System.out.println("e = " + e));
        } catch (Exception e) {
            System.out.println("e = " + e);
        } finally {
            //Assertions.assertThat(centerSearchResponseDTOList2.size()).isEqualTo(3L);
            //Assertions.assertThat(centerSearchResponseDTOList1.size()).isEqualTo(1L);
        }
    }

    @Test
    public void 센터정보가져오기_centerInfo() throws Exception {
        //given
        Center center = new Center("시설이름 1", "의정부 호암로 256 신일유토빌아파트 110동", "001");
        Pair<Double, Double> location = mapService.addressToLocation(center.getC_address());
        center.setLocation(location);
        User user = new User("테스트", "test", "1234", "010-xxxx-xxxx", LocalDate.now(), UserAuthority.USER);
        AgentSaveRequest agent1 = new AgentSaveRequest("이승범", "010-6715-0071", "555",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        agentService.saveAgent(agent1);
        Agent agent = agentRepository.findByA_code("555").get(0);

        Schedule schedule = new Schedule(center, "특이사항");
        schedule.mappingAgent(agent);
        schedule.mappingUser(user);

        Call call = new Call();
        call.mappingCenter(center);
        call.mappingUser(user);

        em.persist(center);
        em.persist(user);
        em.persist(agent);
        em.persist(schedule);
        em.persist(call);

        Long centerId = center.getId();
        em.flush();
        em.clear();
        //when given CenterId from request
        Center center1 = centerService.centerInfo(centerId);

        //then
        Assertions.assertThat(center1.getId()).isEqualTo(center.getId());
        Assertions.assertThat(center1.getScheduleList().get(0).getId()).isEqualTo(schedule.getId());
        Assertions.assertThat(center1.getCallList().get(0).getId()).isEqualTo(call.getId());
        Assertions.assertThat(center1.getScheduleList().get(0).getAgent().getId()).isEqualTo(agent.getId());
        Assertions.assertThat(center1.getScheduleList().get(0).getUser().getId()).isEqualTo(user.getId());
        Assertions.assertThatThrownBy(()->{centerService.centerInfo(600L);}, "no exist CenterId");
    }

    @Test
    public void 센터저장_centerSave() throws Exception {
        //given
        Center center1 = new Center("시설이름 1", "의정부 호암로 256 신일유토빌아파트 110동", "001");
        Center center2 = new Center("시설이름 2", "의정부 fdsf", "003");
        Center center3 = new Center("시설이름 1", "의정부 fdsf", "001");
        //when


        //then

        centerService.saveCenter(center1);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            centerService.saveCenter(center2);
        });
        assertThrows(DuplicateSaveException.class, () -> {
            centerService.saveCenter(center3);
        });
    }

    @Test
    void modifyCenter() {
    }

    @Test
    void getCenter() {
    }
}