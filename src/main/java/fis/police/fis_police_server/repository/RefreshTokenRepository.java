package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);
    RefreshToken find(String refreshToken);
    void remove(RefreshToken refreshToken);
}