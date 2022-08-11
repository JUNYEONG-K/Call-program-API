package fis.police.fis_police_server.repository.repoImpl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.QCenter;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.dto.CenterSearchResponseDTO;
import fis.police.fis_police_server.dto.ExcelCenterDTO;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.repository.queryMethod.CenterQueryMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CenterRepositoryImpl extends CenterQueryMethod implements CenterRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    QCenter qCenter = QCenter.center;

    /*
        날짜 : 2022/01/10 10:42 오전
        작성자 : 현승구
        작성내용 : 시설 저장 삭제 검색(id)
    */
    @Override
    public void save(Center center) {
        em.persist(center);
    }

    @Override
    public Center findById(Long id) {
        return em.find(Center.class, id);
    }

    @Override
    public void delete(Center center) {
        Long id = center.getId();
        em.createQuery("delete from Center where Center.id = :id")
                .setParameter("id", id);
    }

    /*
        날짜 : 2022/01/11 1:58 오후
        작성자 : 현승구
        작성내용 : querydsl이용해서 centersearch 구현
    */
    @Override
    public List<CenterSearchResponseDTO> findBySearchCenterDTO(String c_name, String c_address, String c_ph) {
        List<CenterSearchResponseDTO> centerList;
        if(c_name == null && c_address == null && c_ph == null){
            centerList =
                    jpaQueryFactory
                            .select(Projections.constructor(CenterSearchResponseDTO.class,
                                    qCenter.id, qCenter.c_name, qCenter.c_address, qCenter.c_ph, qCenter.participation, qCenter.visited))
                            .from(qCenter)
                            .limit(1000)
                            .fetch();
        }
        else {
            centerList =
                    jpaQueryFactory
                            .select(Projections.constructor(CenterSearchResponseDTO.class,
                                    qCenter.id, qCenter.c_name ,qCenter.c_address, qCenter.c_ph, qCenter.participation, qCenter.visited))
                            .from(qCenter)
                            .where(cNameLike(c_name)
                                    .and(cAddressLike(c_address))
                                    .and(cPhLike(c_ph)))
                            .limit(1000)
                            .fetch();
        }
        return centerList;
    }

    @Override
    public Center findByIdAndFetchAll(Long id) throws NoResultException, NonUniqueResultException {
        //  list "select center from Center center join Center.Call on 조건 join
        return em.createQuery("select distinct center from Center center " +
                "left join fetch center.callList as call " +
                "left join fetch call.user " +
                "where center.id = :id ", Center.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    @Override
    public List<Center> findNearCenter(double latitude, double longitude) {
        double latitude_l = latitude - 0.009;
        double latitude_h = latitude + 0.009D;
        double longitude_l = longitude - 0.009;
        double longitude_h = longitude + 0.009;

        List<Center> centerList = em.createQuery("select distinct center " +
                "from Center center " +
                "where center.c_latitude <= :latitude_h and center.c_latitude >= :latitude_l " +
                "and center.c_longitude <= :longitude_h and center.c_longitude >= :longitude_l", Center.class)
                .setParameter("latitude_h", latitude_h)
                .setParameter("latitude_l", latitude_l)
                .setParameter("longitude_l", longitude_l)
                .setParameter("longitude_h", longitude_h)
                .getResultList();

        return centerList;
    }

    @Override
    public List<Center> findNameAndPh(String c_name, String c_ph) {
        return em.createQuery("select center from Center center " +
                "where center.c_name = :c_name and center.c_ph = :c_ph", Center.class)
                .setParameter("c_name", c_name)
                .setParameter("c_ph", c_ph)
                .getResultList();
    }

    /*
        작성 날짜: 2022/02/04 3:13 오후
        작성자: 고준영
        작성 내용: 센터의 참여여부를 콜 기록을 바탕으로 업데이트 하기 위함! 근데 쿼리가 안날라가~~~~~ -> executeUpdate() 로 해결!
    */
    @Override
    @Modifying
    public void update_participation(Long id, Participation participation) {
        em.createQuery("update Center center set center.participation = : participation where center.id = : id")
                .setParameter("id", id)
                .setParameter("participation", participation)
                .executeUpdate();
    }
}
