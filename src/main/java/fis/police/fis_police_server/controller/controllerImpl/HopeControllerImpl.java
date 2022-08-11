package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.HopeController;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Hope;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.dto.HopeSaveRequest;
import fis.police.fis_police_server.dto.HopeStatusResponse;
import fis.police.fis_police_server.dto.Result;
import fis.police.fis_police_server.dto.WellSaveResponse;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.HopeService;
import fis.police.fis_police_server.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
    작성 날짜: 2022/02/14 11:33 오전
    작성자: 고준영
    작성 내용: 신청서 작성(제출), 신청서 조회
*/
@RestController
@RequiredArgsConstructor
public class HopeControllerImpl implements HopeController {

    private final HopeService hopeService;
    private final TokenService tokenService;
    private final CenterService centerService;

    @Override
    @PostMapping("/app/hope")
    public WellSaveResponse saveHope(HttpServletRequest request, @RequestBody HopeSaveRequest hopeRequest) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            Officials officialFromRequest = tokenService.getOfficialFromRequest(authorizationHeader);
            Center center = centerService.findById(officialFromRequest.getCenter().getId());
            return hopeService.saveHope(hopeRequest, center, officialFromRequest);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoOfficial");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoOfficial");
        }
    }

    @Override
    @GetMapping("/hope")
    public Result getHope() {
        return hopeService.listHope();
    }

    @Override
    @PostMapping("/hope/{hope_id}")
    public WellSaveResponse updateComplete(@PathVariable Long hope_id) {
         return hopeService.updateHopeComplete(hope_id);
    }

    @Override
    @GetMapping("/app/hope/status")
    public Result currentSituation(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        Officials officialFromRequest = tokenService.getOfficialFromRequest(authorization);
        Center center = centerService.findById(officialFromRequest.getCenter().getId());
        return hopeService.findHopeStatusByCenter(center);
    }

}
