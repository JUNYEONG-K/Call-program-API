package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.AgentModifyRequest;
import fis.police.fis_police_server.dto.AgentSaveRequest;
import fis.police.fis_police_server.repository.AgentRepository;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


/*
    작성날짜: 2022/01/18 4:39 PM
    작성자: 이승범
    작성내용:
*/
@SpringBootTest
@Transactional
class AgentServiceImplTest {

    @Autowired
    private AgentServiceImpl agentService;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private MapConfig mapConfig;
    @Autowired
    private EntityManager em;

    @Test
    public void saveAgent() throws Exception {
        // given
        AgentSaveRequest agentSaveRequest = new AgentSaveRequest("이승범", "010-6715-0071", "321",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        // when
        Agent saveAgent= agentService.saveAgent(agentSaveRequest);
        // then
        Agent findAgent = agentRepository.findById(saveAgent.getId());
        assertThat(saveAgent).isEqualTo(findAgent);
    }
    @Test
    void saveAgent_현장요원_코드중복() throws Exception {
        // given
        AgentSaveRequest agentSaveRequest1 = new AgentSaveRequest("이승범", "010-6715-0071", "321",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        AgentSaveRequest agentSaveRequest2 = new AgentSaveRequest("이승범", "010-6715-0071", "321",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        agentService.saveAgent(agentSaveRequest1);
        // when

        // then
        assertThatThrownBy(() -> agentService.saveAgent(agentSaveRequest2))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void saveAgent_잘못된주소입력() throws Exception {
        // given
        AgentSaveRequest agentSaveRequest = new AgentSaveRequest("이승범", "123", "12312312",
                "ㅁㄴㅇ", true, "", null, UserAuthority.AGENT, null, null);
        // when

        // then
        assertThatThrownBy(() -> agentService.saveAgent(agentSaveRequest))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void saveAgent_요청데이터불완전() throws Exception {
        // given
        AgentSaveRequest agentSaveRequest = new AgentSaveRequest();
        agentSaveRequest.setA_address("구로구 벚꽃로 68길 10");
        // when
        agentService.saveAgent(agentSaveRequest);
        // then
        assertThatThrownBy(() -> em.flush())
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void modifyAgent() throws Exception {
        // given
        AgentSaveRequest agentSaveRequest = new AgentSaveRequest("이승범", "010-6715-0071", "321",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        Agent saveAgent= agentService.saveAgent(agentSaveRequest);
        // when
        AgentModifyRequest agentModifyRequest = new AgentModifyRequest(null, saveAgent.getId(), "이승범", "010-6715-0071", "123",
                "구로구 벚꽃로 68길 10", false, "", null, false, null, null);
        Agent modifiedAgent = agentService.modifyAgent(agentModifyRequest);
        // then
        Agent findAgent = agentRepository.findById(modifiedAgent.getId());
        assertThat(modifiedAgent).isEqualTo(findAgent);
    }

    @Test
    public void modifyAgent_현장요원코드중복() throws Exception {
        // given
        AgentSaveRequest agentSaveRequest1 = new AgentSaveRequest("이승범", "010-6715-0071", "321",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        AgentSaveRequest agentSaveRequest2 = new AgentSaveRequest("이승범", "010-6715-0071", "123",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        agentService.saveAgent(agentSaveRequest1);
        Agent saveAgent = agentService.saveAgent(agentSaveRequest2);
        // when
        AgentModifyRequest agentModifyRequest = new AgentModifyRequest(null, saveAgent.getId(), "이승범", "010-6715-0071", "321",
                "구로구 벚꽃로 68길 10", false, "", null, false, null, null);
        // then
        assertThatThrownBy(() -> agentService.modifyAgent(agentModifyRequest))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void getAgents() throws Exception {
        // given
        AgentSaveRequest agentSaveRequest1 = new AgentSaveRequest("이승범", "010-6715-0071", "321",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        AgentSaveRequest agentSaveRequest2 = new AgentSaveRequest("이승범", "010-6715-0071", "123",
                "구로구 벚꽃로 68길 10", false, "", null, UserAuthority.AGENT, null, null);
        Agent saveAgent1 = agentService.saveAgent(agentSaveRequest1);
        Agent saveAgent2 = agentService.saveAgent(agentSaveRequest2);
        em.flush();
        em.clear();
        Agent findAgent1 = agentRepository.findById(saveAgent1.getId());
        Agent findAgent2 = agentRepository.findById(saveAgent2.getId());
        // when
        List<Agent> agents = agentService.getAgents();
        // then
        assertThat(agents).contains(findAgent1, findAgent2);
    }
}