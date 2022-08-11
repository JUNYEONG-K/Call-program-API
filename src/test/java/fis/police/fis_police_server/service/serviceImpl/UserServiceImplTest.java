package fis.police.fis_police_server.service.serviceImpl;

import com.sun.net.httpserver.Authenticator;
import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.domain.enumType.InOut;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.repository.CallRepository;
import fis.police.fis_police_server.repository.UserRepository;
import fis.police.fis_police_server.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CallRepository callRepository;
    @Autowired
    UserService userService;

    @Test
    public void 신규_회원가입() {
        //given
        final UserSaveRequest saveDto1 = new UserSaveRequest(null, "test001", "원보라", "1234", "010-0000-0001", LocalDate.of(2021, 02, 04), UserAuthority.ADMIN);

        //when
        UserSaveResponse res = userService.saveUser(saveDto1);

        //then
        org.junit.jupiter.api.Assertions.assertEquals(res.getUser_id(), userService.findOneUser(res.getUser_id()).getId());
    }

    @Test
    public void 기존_회원수정() {
        //given
        final UserSaveRequest saveDto1 = new UserSaveRequest(null, "test001", "원보라", "1234", "010-0000-0001", LocalDate.of(2021, 02, 04), UserAuthority.ADMIN);
        UserSaveResponse res = userService.saveUser(saveDto1);
        final UserSaveRequest changeUser = new UserSaveRequest(res.getUser_id(), "test002", "2", "2", "010-0000-0002", LocalDate.of(2021, 02, 04), UserAuthority.ADMIN);

        //when
        userService.modifyUser(changeUser);

        //then
        Assertions.assertThat(changeUser.getU_nickname()).isEqualTo((userService.findOneUser(res.getUser_id()).getU_nickname()));
    }


    @Test
    public void 회원가입_닉네임_중복() {
        //given
        final UserSaveRequest saveDto1 = new UserSaveRequest(null, "test001", "원보라", "1234", "010-0000-0001", LocalDate.of(2021, 02, 04), UserAuthority.ADMIN);
        final UserSaveRequest saveDto2 = new UserSaveRequest(null, "test001", "원보라2", "1234", "010-0000-0002", LocalDate.of(2021, 02, 04), UserAuthority.USER);

        //when
        userService.saveUser(saveDto1);

        //then
        try {
            userService.saveUser(saveDto2);    // 예외 발생
            fail();
        } catch (IllegalStateException e) {
            System.out.println("닉네임 중복============================");
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 닉네임 입니다.");
        }
    }


    @Test
    public void 한명_조회() {
        //given
        final UserSaveRequest saveDto1 = new UserSaveRequest(null, "test001", "원보라", "1234", "010-0000-0001", LocalDate.of(2021, 02, 04), UserAuthority.ADMIN);
        UserSaveResponse res = userService.saveUser(saveDto1);

        //when
        User findUser = userService.findOneUser(res.getUser_id());

        //then
        Assertions.assertThat(findUser.getId()).isEqualTo(res.getUser_id());
    }

    @Test
    public void 전체_조회() {
        //given
        final UserSaveRequest saveDto1 = new UserSaveRequest(null, "test001", "원보라", "1234", "010-0000-0001", LocalDate.of(2021, 02, 04), UserAuthority.ADMIN);
        final UserSaveRequest saveDto2 = new UserSaveRequest(null, "test002", "원보라2", "1234", "010-0000-0002", LocalDate.of(2021, 02, 04), UserAuthority.USER);
        final UserSaveRequest saveDto3 = new UserSaveRequest(null, "test003", "원보라2", "1234", "010-0000-0002", LocalDate.of(2021, 02, 04), UserAuthority.USER);
        userService.saveUser(saveDto1);
        userService.saveUser(saveDto2);
        userService.saveUser(saveDto3);

        //when
        List<UserInfoResponse> users = userService.getUser();

        //then
//        org.junit.jupiter.api.Assertions.assertEquals(3,users.size()); //init DB 때문에 안됨 -,,-
        for (UserInfoResponse user : users) {
            System.out.println("user id:" + user.getUser_id()+" nickname:"+ user.getU_nickname());
        }
    }


    @Test
    public void 사용자_오늘_통화건수() {
        //given
        Center center1 = new Center("test_center", "분당구 불정로 6", "123", 123D, 123D);
        em.persist(center1);
        User user1 = new User("testUser1", "111", "111", "111", LocalDate.now(), UserAuthority.USER);
        em.persist(user1);
        User user2 = new User("testUser2", "222", "222", "222", LocalDate.now(), UserAuthority.ADMIN);
        em.persist(user2);

        Call call1 = new Call(center1, user1, "2022-02-04", "12:23:12", Participation.PARTICIPATION, InOut.IN, "담당자1", "010-1111-1111", "@naver", 20, "...", "...");
        em.persist(call1);
        Call call2 = new Call(center1, user1, "2022-02-04", "18:49:48", Participation.PARTICIPATION, InOut.IN, "담당자2", "010-2222-2222", "@naver", 20, "...", "...");
        em.persist(call2);
        Call call3 = new Call(center1, user1, "2022-02-04", "10:37:12", Participation.PARTICIPATION, InOut.IN, "담당자3", "010-3333-3333", "@naver", 20, "...", "...");
        em.persist(call3);

        Call call4 = new Call(center1, user2, "2022-02-04", "14:11:37", Participation.PARTICIPATION, InOut.IN, "담당자4", "010-4444-4444", "@naver", 20, "...", "...");
        em.persist(call4);

        //when
        List<CallTodayDTO> call = userService.todayCallNum("2022-02-04");

        //then
        Assertions.assertThat(call.get(0).getCall_num()).isEqualTo(3); //user1 2022-02-04 통화 3건
        Assertions.assertThat(call.get(1).getCall_num()).isEqualTo(1); //user2 2022-02-04 통화 1건
    }

    @Test
    public void 사용자_평균_통화건수() {
        //given
        Center center1 = new Center("test_center", "분당구 불정로 6", "123", 123D, 123D);
        em.persist(center1);
        User user1 = new User("testUser1", "111", "111", "111", LocalDate.now(), UserAuthority.USER);
        em.persist(user1);
        User user2 = new User("testUser2", "222", "222", "222", LocalDate.now(), UserAuthority.ADMIN);
        em.persist(user2);

        Call call0 = new Call(center1, user1, "2022-02-01", "12:23:12", Participation.PARTICIPATION, InOut.IN, "담당자1", "010-1111-1111", "@naver", 20, "...", "...");
        em.persist(call0);
        Call call1 = new Call(center1, user1, "2022-02-01", "12:23:12", Participation.PARTICIPATION, InOut.IN, "담당자1", "010-1111-1111", "@naver", 20, "...", "...");
        em.persist(call1);
        Call call2 = new Call(center1, user1, "2022-02-03", "18:49:48", Participation.PARTICIPATION, InOut.IN, "담당자2", "010-2222-2222", "@naver", 20, "...", "...");
        em.persist(call2);
        Call call3 = new Call(center1, user1, "2022-02-03", "10:37:12", Participation.PARTICIPATION, InOut.IN, "담당자3", "010-3333-3333", "@naver", 20, "...", "...");
        em.persist(call3);
        Call call4 = new Call(center1, user1, "2022-02-04", "18:49:48", Participation.PARTICIPATION, InOut.IN, "담당자2", "010-2222-2222", "@naver", 20, "...", "...");
        em.persist(call4);
        Call call5 = new Call(center1, user1, "2022-02-04", "10:37:12", Participation.PARTICIPATION, InOut.IN, "담당자3", "010-3333-3333", "@naver", 20, "...", "...");
        em.persist(call5);

        Call call6 = new Call(center1, user2, "2022-02-01", "14:11:37", Participation.PARTICIPATION, InOut.IN, "담당자4", "010-4444-4444", "@naver", 20, "...", "...");
        em.persist(call6);
        Call call7 = new Call(center1, user2, "2022-02-04", "14:11:37", Participation.PARTICIPATION, InOut.IN, "담당자4", "010-4444-4444", "@naver", 20, "...", "...");
        em.persist(call7);

        //when
        List<CallAvgDTO> call = userService.totalCallNum();

        //then
        for (CallAvgDTO callAvgDTO : call) {
            System.out.println("callAvgDTO = " + callAvgDTO);
        }
    }
}