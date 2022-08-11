package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.LoginResponse;
import fis.police.fis_police_server.repository.AgentRepository;
import fis.police.fis_police_server.repository.OfficialsRepository;
import fis.police.fis_police_server.service.TokenService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final AgentRepository agentRepository;
    private final OfficialsRepository officialsRepository;

    @Override
    public Agent getAgentFromRequest(String authorization) {
        Claims token = parseJwtToken(authorization);
        Long agent_id = Long.valueOf(token.get("id").toString());
        return agentRepository.findById(agent_id);
    }

    @Override
    public Officials getOfficialFromRequest(String authorization) {
        Claims token = parseJwtToken(authorization);
        Long official_id = Long.valueOf(token.get("id").toString());
        return officialsRepository.findById(official_id);
    }

    @Override
    public Claims parseJwtToken(String authorizationHeader) {
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalStateException("NoToken");
        }
        String token = authorizationHeader.substring("Bearer ".length());
        try {
            return Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException("ExpiredToken");
        }
    }

    @Override
    public String createToken(Long primaryKey, LoginResponse loginResponse, String type) {
        // 공통
        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .claim("id", primaryKey)
                .claim("username", loginResponse.getU_name())
                .claim("role", loginResponse.getU_auth())
                .signWith(SignatureAlgorithm.HS256, "secret");

        if (Objects.equals(type, "access")) {
            return this.accessToken(type, jwtBuilder).compact();
        } else if (Objects.equals(type, "refresh")) {
            return this.refreshToken(type, jwtBuilder).compact();
        } return null;
    }

    // access token 생성 (1시간)
    private JwtBuilder accessToken(String access, JwtBuilder jwtBuilder) {
        Date now = new Date();
        return jwtBuilder.setExpiration(new Date(now.getTime() + Duration.ofMinutes(60).toMillis()))
                .setIssuer(access);
    }

    // refresh token 생성 (1주일)
    private JwtBuilder refreshToken(String refresh, JwtBuilder jwtBuilder) {
        Date now = new Date();
        return jwtBuilder.setExpiration(new Date(now.getTime() + Duration.ofDays(7).toMillis()))
                .setIssuer(refresh);
    }

    @Override
    public boolean validateToken(String token) {
        token = token.substring("Bearer ".length());

        return !Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }
}