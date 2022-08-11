package fis.police.fis_police_server.repository.repoImpl;

import fis.police.fis_police_server.domain.RefreshToken;
import fis.police.fis_police_server.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final EntityManager em;

    @Override
    public void save(RefreshToken refreshToken) {
        em.persist(refreshToken);
    }

    @Override
    public RefreshToken find(String refreshToken) {
        return em.find(RefreshToken.class, refreshToken);
    }

    @Override
    public void remove(RefreshToken refreshToken) {
        em.remove(refreshToken);
    }
}