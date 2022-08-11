package fis.police.fis_police_server.service.serviceImpl;

import com.mysema.commons.lang.Pair;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.CenterSearchResponseDTO;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.MapService;
import fis.police.fis_police_server.service.exceptions.DuplicateSaveException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Service //이걸안써서 오류 내\? 후 조심합시디
@Transactional
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;
    private final MapService mapService;

    /*
        날짜 : 2022/01/11 11:48 오전
        작성자 : 현승구
        작성내용 : 센터 검색
    */
   @Override
    public List<CenterSearchResponseDTO> findCenterList(String c_name, String c_address, String c_ph) throws NoResultException {
       List<CenterSearchResponseDTO> centerList = centerRepository.findBySearchCenterDTO(c_name, c_address, c_ph);
       if(centerList.isEmpty())
           throw new NoResultException("조건에 맞는 center 가 존재하지 않습니다");
       else return centerList;
    }

    @Override
    public Center centerInfo(Long center_id)  throws NoResultException, NonUniqueResultException {
        return centerRepository.findByIdAndFetchAll(center_id);
    }

    @Override
    public void saveCenter(Center center) throws ParseException, DuplicateSaveException {
       if(centerRepository.findNameAndPh(center.getC_name(),center.getC_ph()).size() != 0){
           throw new DuplicateSaveException("중복된 센터 존재 에러발생");
       }
       Pair<Double, Double> location = mapService.addressToLocation(center.getC_address());
       center.setLocation(location);
       centerRepository.save(center);
    }

    @Override
    public void modifyCenter(Center center) throws ParseException {
        Center target = centerRepository.findById(center.getId());
        target.modifyCenter(center);
        Pair<Double, Double> location = mapService.addressToLocation(target.getC_address());
        target.setLocation(location);
    }

    @Override
    public List<Center> getCenter() {
        return null;
    }

    @Override
    public Center findById(Long id) throws NoResultException{
       try {
           return centerRepository.findById(id);
       } catch (NullPointerException e) {
           throw new NullPointerException("관련 시설이 존재하지 않음.");
       } catch (NoResultException e) {
           throw new NoResultException("시설 id 존재하지 않음.");
       }
    }
}
