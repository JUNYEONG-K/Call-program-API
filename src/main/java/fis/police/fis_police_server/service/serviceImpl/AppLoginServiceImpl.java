package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.AppLoginRequest;
import fis.police.fis_police_server.dto.LoginRequest;
import fis.police.fis_police_server.dto.LoginResponse;
import fis.police.fis_police_server.repository.AgentRepository;
import fis.police.fis_police_server.repository.OfficialsRepository;
import fis.police.fis_police_server.repository.UserRepository;
import fis.police.fis_police_server.service.AppLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/*
    작성 날짜: 2022/03/23 2:38 오후
    작성자: 고준영
    작성 내용: 앱 로그인
*/
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AppLoginServiceImpl implements AppLoginService {

    private final AgentRepository agentRepository;
    private final OfficialsRepository officialsRepository;

    @Override
    public Long getPrimaryKey(AppLoginRequest request) {
        if (request.getRole() == UserAuthority.AGENT) {
            List<Agent> agent = agentRepository.findByNickname(request.getU_nickname());
            if (agent.size() != 0) {
                if (agent.get(0).getA_pwd().equals(request.getU_pwd())) {
                    return agent.get(0).getId();
                }
            }
        } else if (request.getRole() == UserAuthority.OFFICIAL) {
            List<Officials> officials = officialsRepository.findByNickname(request.getU_nickname());
            if (officials.size() != 0) {
                if (officials.get(0).getO_pwd().equals(request.getU_pwd())) {
                    return officials.get(0).getId();
                }
            }
        } else {
            throw new IllegalArgumentException("role 정보 오류");
        }
        return null;
    }

    @Override
    public LoginResponse login(AppLoginRequest request) {
        LoginResponse loginResponse = new LoginResponse();

        String nickname = request.getU_nickname();
        String pwd = request.getU_pwd();
        UserAuthority role = request.getRole();

        if (role == UserAuthority.AGENT) {
            log.info("[로그인 요청 역할 {}]", role);
            List<Agent> agent = agentRepository.findByNickname(nickname);
            if (!agent.isEmpty()) {
                return authenticateAgent(agent, loginResponse, pwd);
            } else {
                loginFail(loginResponse, "idFail");
                return loginResponse;
            }
        } else if (role == UserAuthority.OFFICIAL) {
            log.info("[로그인 요청 역할 {}]", role);
            List<Officials> officials = officialsRepository.findByNickname(nickname);
            if (!officials.isEmpty()) {
                return authenticateOfficial(officials, loginResponse, pwd);
            } else {
                loginFail(loginResponse, "idFail");
                return loginResponse;
            }
        } else {
            throw new IllegalArgumentException("role 정보 오류");
        }
    }

    private LoginResponse authenticateAgent(List<Agent> agent, LoginResponse loginResponse, String pwd) {
        if(!agent.get(0).getA_pwd().equals(pwd)) {
            loginFail(loginResponse, "pwdFail");
        } else {
            loginResponse.setSc("success");
            loginResponse.setU_name(agent.get(0).getA_name());
            loginResponse.setU_auth(agent.get(0).getU_auth());
        }
        return loginResponse;
    }
    private LoginResponse authenticateOfficial(List<Officials> officials, LoginResponse loginResponse, String pwd) {
        if(!officials.get(0).getO_pwd().equals(pwd)) {
            loginFail(loginResponse, "pwdFail");
        } else {
            loginResponse.setSc("success");
            loginResponse.setU_name(officials.get(0).getO_name());
            loginResponse.setU_auth(officials.get(0).getU_auth());
        }
        return loginResponse;
    }

    private void loginFail(LoginResponse response, String message) {
        response.setSc(message);
        response.setU_name(null);
        response.setU_auth(null);
    }

}
