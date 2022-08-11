package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Hope;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.repository.HopeRepository;
import fis.police.fis_police_server.repository.OfficialsRepository;
import fis.police.fis_police_server.service.HopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HopeServiceImpl implements HopeService {

    private final HopeRepository hopeRepository;
    private final CenterRepository centerRepository;
    private final OfficialsRepository officialsRepository;


    @Override
    public WellSaveResponse saveHope(HopeSaveRequest request, Center center, Officials officials) {
        Hope hope = Hope.createHope(request, officials, center);
        hopeRepository.saveHope(hope);
        return new WellSaveResponse("200", "created");
    }

    @Override
    public Result listHope() {
        List<Hope> hopes = hopeRepository.listOfHope();
        List<HopeListResponse> collect = hopes.stream()
                .map(hope -> new HopeListResponse(hope.getId(),
                        new CenterSaveDTO(hope.getCenter().getId(), hope.getCenter().getC_name(), hope.getCenter().getC_address(), hope.getCenter().getC_ph(), hope.getCenter().getC_latitude(), hope.getCenter().getC_longitude()),
                        new OfficialDTO(hope.getOfficials().getId(),hope.getOfficials().getO_name(), hope.getOfficials().getO_ph(), hope.getOfficials().getO_email()),
                        hope.getAccept(),
                        hope.getComplete(), hope.getH_date(), hope.getH_time(), hope.getH_mail(), hope.getH_ph()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Override
    public WellSaveResponse updateHopeComplete(Long id) {
        Complete complete = Complete.complete;
        hopeRepository.updateHopeComplete(id, complete);
        return new WellSaveResponse("200", "updated");
    }


    @Override
    public Result findHopeStatusByCenter(Center center) {
        List<Hope> hopes = hopeRepository.findHopesByCenter(center.getId());
        if (hopes.isEmpty()) {
            throw new NullPointerException("신청현황이 없습니다.");
        }
        List<HopeStatusResponse> collect = hopes.stream()
                .map(hope -> new HopeStatusResponse(hope.getCenter().getC_name(), hope.getCenter().getC_address(), hope.getAccept(), hope.getH_date(), hope.getH_time(), hope.getNow_date(), hope.getOfficials().getO_name(), hope.getH_ph(), hope.getH_mail()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

}
