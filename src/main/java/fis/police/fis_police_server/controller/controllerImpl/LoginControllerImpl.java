package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.LoginController;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.dto.LoginRequest;
import fis.police.fis_police_server.dto.LoginResponse;
import fis.police.fis_police_server.service.LoginService;
import fis.police.fis_police_server.service.TokenService;
import fis.police.fis_police_server.service.UserService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.Date;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginControllerImpl implements LoginController {

    private final LoginService loginService;
    private final TokenService tokenService;

    @Override
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginrequest, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        LoginResponse loginResponse = loginService.loginRes(loginrequest);
        Long loginUserId = loginService.loginUserId(loginrequest);

        //로그인 실패
        if (!loginResponse.getSc().equals("success")) {
            log.error("[로그인 id값: {}] [url: {}] [로그인 실패]",loginUserId,"/login");
            return loginResponse;
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(); //디폴트 True: 기존있으면 기존반환, 없을 때 새로 생성  <-> false: 없을 때 새로 생성안함

        //세션에 로그인 회원 정보 보관
        //로그인 페이지로 이동시 세션 만료 됨
        if (!session.isNew()) {
            session.invalidate();
            session = request.getSession();
        }
        session.setAttribute("loginUser", loginUserId);
        log.info("[로그인 id값: {}] [url: {}] [로그인 성공]",loginUserId,"/login");
        return loginResponse;
    }

    @Override
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //세션 지움
        }
        return "logout";
    }

    @Override
    @GetMapping("/checkLogin")
    //이미 로그인 된 사용자를 찾을 때 (이 기능은 세션을 생성하지 않음)
    public LoginResponse loginSuccess(@SessionAttribute(name = "loginUser", required = false) Long loginUser, Model model,HttpServletRequest req) {
        LoginResponse loginResponse = new LoginResponse();
            try {
                loginResponse = loginService.loginCheck(loginUser);
                model.addAttribute("loginUser", loginUser);     //세션이 유지되면
            } catch (IllegalStateException ie) {
                //세션에 데이터 없으면
                log.error("[로그인 id값: {}] [url: {}] [{}]", req.getSession().getAttribute("loginUser"),"/checkLogin",ie.getMessage());
                loginResponse.setSc("fail");
            } finally {
                return loginResponse;
            }
    }
}



