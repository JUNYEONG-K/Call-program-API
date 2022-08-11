package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.RefreshTokenController;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.LoginResponse;
import fis.police.fis_police_server.dto.TokenSet;
import fis.police.fis_police_server.service.TokenService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/app")
public class RefreshTokenControllerImpl implements RefreshTokenController {

    private final TokenService tokenService;

    @GetMapping("/refreshToken")
    @Override
    public TokenSet createAccessToken(HttpServletRequest request) {
        log.info("[요청 : 새로운 토큰 발금]");

        LoginResponse loginResponse = new LoginResponse();
        String refreshToken = request.getHeader("RefreshToken");

        boolean validateToken = tokenService.validateToken(refreshToken);
        // todo refresh token 값이 db 에 있는지도 조사해야한다고 함. 이것까지 포함해서 조건문 작성 03/14
        if(validateToken) {
            Long id = Long.valueOf(tokenService.parseJwtToken(refreshToken).get("id").toString());
            String username = tokenService.parseJwtToken(refreshToken).get("username").toString();
            UserAuthority role = UserAuthority.valueOf(tokenService.parseJwtToken(refreshToken).get("role").toString());
            loginResponse.setU_name(username);
            loginResponse.setU_auth(role);
            String AccessToken = tokenService.createToken(id, loginResponse, "access");// 새로운 accessToken 생성
            String RefreshToken = tokenService.createToken(id, loginResponse, "refresh");// 새로운 refreshToken 생성
            return new TokenSet(AccessToken, RefreshToken);
        } else {
            throw new JwtException("Logout Plz");
        }
    }
}
