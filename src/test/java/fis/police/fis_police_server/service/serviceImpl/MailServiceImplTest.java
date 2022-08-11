package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.repository.CallRepository;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.repository.repoImpl.CallRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class MailServiceImplTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private CallRepositoryImpl callRepository;
    @Autowired
    private CenterRepository centerRepository;

    @Test
    void 최근통화기록찾기 () {
        Center center1 = new Center("시설이름 1", "서울", "001");
        Center center2 = new Center("시설이름 2", "서울", "002");
        Center center3 = new Center("시설이름 3", "의정부", "003");
        em.persist(center1);
        em.persist(center2);
        em.persist(center3);

        em.flush();
        em.clear();

        Call call1 = new Call(center1, "2022-01-23", "10:14:23", "godoly1211@naver.com");
        Call call2 = new Call(center1, "2022-01-23", "11:12:11 ", "godoly1211@daum.net");
        Call call3 = new Call(center1, "2022-01-23", "14:12:11", "godoly5180@gmail.com");
        em.persist(call1);
        em.persist(call2);
        em.persist(call3);

        em.flush();
        em.clear();

        Long id0123 = center1.getId();
        Call recentcall = callRepository.recentcall(id0123);
        System.out.println("recentcall = " + recentcall);
        System.out.println("call3 = " + call3);

        assertThat(recentcall.getM_email()).isEqualTo("godoly5180@gmail.com");


    }

}