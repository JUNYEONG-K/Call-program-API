package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.CallSaveRequest;
import fis.police.fis_police_server.dto.CallSaveResponse;
import fis.police.fis_police_server.dto.LoginRequest;
import fis.police.fis_police_server.dto.UserSaveRequest;
import fis.police.fis_police_server.repository.CallRepository;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.service.LoginService;
import fis.police.fis_police_server.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CallServiceImplTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private CallServiceImpl callService;
    @Autowired
    private CallRepository callRepository;

    @Test
    void 콜기록저장() {

        Center center1 = new Center("시설이름 1", "서울", "001");
        Center center2 = new Center("시설이름 2", "서울", "002");
        Center center3 = new Center("시설이름 3", "의정부", "003");
        em.persist(center1);
        em.persist(center2);
        em.persist(center3);

        em.flush();
        em.clear();

        User user1 = new User();
        user1.setU_nickname("고준영");
        user1.setU_name("고준영");
        user1.setU_pwd("1234");
        user1.setU_auth(UserAuthority.USER);
        em.persist(user1);

        em.flush();
        em.clear();

        CallSaveRequest callSaveRequest = new CallSaveRequest();
        callSaveRequest.setCenter_id(center1.getId());
        callSaveRequest.setM_email("godoly1211@naver.com");
        CallSaveResponse callSaveResponse = callService.saveCall(callSaveRequest, center1, user1, "2022-01-23", "T10:47:32");
        Call savedCall = callRepository.findById(callSaveResponse.getCall_id());

        Call testCall = new Call(center1, "2022-01-23", "T10:47:32", "godoly1211@naver.com");
        testCall.mappingUser(user1);

        assertThat(savedCall.getCenter()).isEqualTo(testCall.getCenter());
        assertThat(savedCall.getUser()).isEqualTo(testCall.getUser());
        assertThat(savedCall.getDate()).isEqualTo(testCall.getDate());
        assertThat(savedCall.getTime()).isEqualTo(testCall.getTime());
        assertThat(savedCall.getM_email()).isEqualTo(testCall.getM_email());

    }
}