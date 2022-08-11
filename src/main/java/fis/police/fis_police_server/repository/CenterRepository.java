package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.dto.CenterSearchResponseDTO;
import fis.police.fis_police_server.dto.ExcelCenterDTO;

import java.util.List;

//현승구
public interface CenterRepository {

    // 시설 추가
    void save(Center center);

    // 시설 찾기
    Center findById(Long id);

    // 시설 삭제
    void delete(Center center);

    List<CenterSearchResponseDTO> findBySearchCenterDTO(String c_name, String c_address, String c_ph);

    Center findByIdAndFetchAll(Long center_id);

    List<Center> findNearCenter(double latitude, double longitude);

    List<Center> findNameAndPh(String c_name, String c_ph);

    void update_participation(Long id, Participation participation);
}
