package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Confirm;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.repository.ConfirmRepository;
import fis.police.fis_police_server.service.ConfirmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ConfirmServiceImplTest {

    @Autowired
    private ConfirmRepository confirmRepository;
    @Autowired
    private ConfirmService confirmService;
    @Autowired
    private CenterRepository centerRepository;
    @Autowired
    private EntityManager em;

    @Test
    void 확인서작성() {
        Center center1 = new Center("First Center", "010-1234-1234", "50", Participation.PARTICIPATION);
        Center center2 = new Center("Second Center", "010-2345-2345", "40", Participation.PARTICIPATION);
        Center center3 = new Center("Third Center", "010-3456-3456", "100", Participation.PARTICIPATION);
        Center center4 = new Center("Fourth Center", "010-4567-4567", "10", Participation.PARTICIPATION);
        Center center5 = new Center("Fifth Center", "010-5678-5678", "70", Participation.PARTICIPATION);
        Center center6 = new Center("Sixth Center", "010-6789-6789", "30", Participation.PARTICIPATION);
        Center center7 = new Center("Seventh Center", "010-7890-7890", "20", Participation.PARTICIPATION);

        em.persist(center1);
        em.persist(center2);
        em.persist(center3);
        em.persist(center4);
        em.persist(center5);
        em.persist(center5);
        em.persist(center6);
        em.persist(center7);


        em.flush();
        em.clear();

        Center byId = centerRepository.findById(center1.getId());
        System.out.println("byId.getC_name() = " + byId.getC_name());
//        Confirm confirm1 = new Confirm(null, );

    }
}