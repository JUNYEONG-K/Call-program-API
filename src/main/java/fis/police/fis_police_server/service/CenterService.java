package fis.police.fis_police_server.service;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.CenterSearchResponseDTO;
import fis.police.fis_police_server.service.exceptions.DuplicateSaveException;
import org.json.simple.parser.ParseException;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

public interface CenterService {
    // 시설 검색 검색 실패시 null 반환 아무것도 없을 시에 빈 ArrayList 반환 검색결과로 SearchCenterResponseDTO 반환
    List<CenterSearchResponseDTO> findCenterList(String c_name, String c_address, String c_ph) throws NoResultException;

    //  시설에 해당하는 콜정보, 스케줄정보, 시설정보 반환 로직
    Center centerInfo(Long center_id)  throws NoResultException, NonUniqueResultException;

    // 시설 추기
    void saveCenter(Center center) throws ParseException, DuplicateSaveException;

    // 시설 수정
    void modifyCenter(Center center) throws ParseException;

    // 시설 조회
    List<Center> getCenter();

    Center findById(Long id);
}
