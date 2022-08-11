package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.OfficialsController;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Officials;
import fis.police.fis_police_server.dto.OfficialSaveRequest;
import fis.police.fis_police_server.dto.WellSaveResponse;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.OfficialService;
import fis.police.fis_police_server.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*
    작성 날짜: 2022/02/14 11:35 오전
    작성자: 고준영
    작성 내용: 시설 담당자 회원가입
*/
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/app")
public class OfficialsControllerImpl implements OfficialsController {

    private final OfficialService officialService;
    private final TokenService tokenService;
    private final CenterService centerService;

    @Override
    @PostMapping("/officials")
    public WellSaveResponse saveOfficials(@RequestBody OfficialSaveRequest request) {
        log.info("[url: {}] [요청: 시설 담당자 회원가입]", "/officials");
        Center center = centerService.findById(request.getCenter_id());
        officialService.saveOfficials(request, center);
        return new WellSaveResponse("200", "created");
    }

    @Override
    @PatchMapping("/officials")
    public WellSaveResponse modifyOfficials(@RequestBody OfficialSaveRequest request, HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        log.info("[로그인 id값: {}] [url: {}] [요청: 시설 담당자 정보 수정]", tokenService.getOfficialFromRequest(authorizationHeader).getId(), "/officials");
        Officials officialFromRequest = tokenService.getOfficialFromRequest(authorizationHeader);
        Center center = centerService.findById(request.getCenter_id());
        officialService.modifyOfficials(officialFromRequest, request, center);
        return new WellSaveResponse("200", "updated");
    }
}
