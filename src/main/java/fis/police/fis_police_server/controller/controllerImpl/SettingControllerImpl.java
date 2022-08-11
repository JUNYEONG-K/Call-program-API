package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.SettingController;
import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.dto.SettingAgentDTO;
import fis.police.fis_police_server.dto.SettingOfficialDTO;
import fis.police.fis_police_server.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app")
public class SettingControllerImpl implements SettingController {
    private final TokenService tokenService;
    private final OfficialService officialService;
    private final AgentService agentService;
    @Override
    @GetMapping("/official/setting")
    public Object basicOfficialInfo(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        Officials officialFromRequest = tokenService.getOfficialFromRequest(authorizationHeader);
        log.info("[로그인 id 값 : {}] [url : {}] [요청 : 시설 담당자 기본 정보]", officialFromRequest.getId(), "/official/setting");
        try {
            Officials official = officialService.findById(officialFromRequest.getId());
            Center center = official.getCenter();
            return new SettingOfficialDTO(official.getId(), center.getId(), center.getC_name(), center.getC_address(), official.getO_name(), official.getO_ph(), official.getO_email(), official.getO_nickname(), official.getO_pwd());
        } catch (NullPointerException e) {
            throw new NullPointerException("NoOfficial");
        }
    }

    @Override
    @GetMapping("/agent/setting")
    public Object basicAgentInfo(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        Agent agentFromRequest = tokenService.getAgentFromRequest(authorizationHeader);
        log.info("[로그인 id 값 : {}] [url : {}] [요청 : 현장요원 기본 정보]", agentFromRequest.getId(), "/agent/setting");
        try {
            Agent agent = agentService.findById(agentFromRequest.getId());
            return new SettingAgentDTO(agent.getA_name(), agent.getA_nickname(), agent.getA_pwd(), agent.getA_ph());
        } catch (NullPointerException e) {
            throw new NullPointerException("NoAgent");
        }
    }

}
