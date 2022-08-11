package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.dto.CenterSelectDateResponseDTO;

import java.util.List;

//이승범
public interface AgentRepository {
    Agent findById(Long id);

    public List<Agent> findByA_code(String a_code);

    List<Agent> findAll();

    void save(Agent agent);

    List<CenterSelectDateResponseDTO> findNearAgent(Double latitude, Double longitude, Long range);

    List<Agent> findByNickname(String nickname);

    void deletePicture(Long agent_id);
}
