package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.CenterController;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.service.AgentService;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.exceptions.DuplicateSaveException;
import fis.police.fis_police_server.service.serviceImpl.MapServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CenterControllerImpl implements CenterController {

    private final CenterService centerService;
    private final MapServiceImpl mapService;
    private final AgentService agentService;

    @GetMapping("/center/search")
    @Override
    public Result searchCenter(@RequestParam @Nullable String c_name, @RequestParam @Nullable String c_address, @RequestParam @Nullable String c_ph, HttpServletRequest request){
        log.info("[로그인 id 값 : {}] [url : {}] [요청 : 시설검색]", request.getSession().getAttribute("loginUser"), "/center/search");
        try {
            List<CenterSearchResponseDTO> results = centerService.findCenterList(c_name, c_address, c_ph);
            return new Result(results);
        } catch (NullPointerException e){
            throw new NullPointerException("결과 없음.");
        }
    }

    /*
        날짜 : 2022/01/11 8:25 오후
        작성자 : 현승구
        작성내용 : 로직 작성 test 코드 아직 작성 안함
    */
    @GetMapping("/center/select")
    @Override
    public Result selectCenter(@RequestParam Long center_id, HttpServletRequest request) {
        log.info("[로그인 id 값 : {}] [url : {}] [요청 : 시설 선택 시 콜기록, 일정 등 정보 조회]", request.getSession().getAttribute("loginUser"), "/center/select?center_id=" + center_id);
        try{
            Center center = centerService.centerInfo(center_id);
            List<CenterSearchNearCenterDTO> centerSearchNearCenterDTOList = new ArrayList<CenterSearchNearCenterDTO>();
            mapService.centerNearCenter(center).stream()
                    .forEach(e -> {
                        Double distance = mapService.distance(center.getC_latitude(), center.getC_longitude(), e.getC_latitude(), e.getC_longitude()).doubleValue();
                        centerSearchNearCenterDTOList.add(new CenterSearchNearCenterDTO(e, distance));
                    });
            return new Result(new CenterSelectResponseDTO(center, centerSearchNearCenterDTOList));
        } catch (NoResultException e){
            throw new NoResultException("검색 결과 없음.");
        } catch (NonUniqueResultException e){
            throw new NonUniqueResultException("해당 시설 id에 여러 시설 존재, DB 확인 필요");
        }
    }

    /*
        날짜 : 2022/01/12 12:06 오후
        작성자 : 현승구
        작성내용 : selectDate 호출시 주변 현장요원 나온다. -> 현장요원들 좌표 던져준다.
    */

    @Override
    @GetMapping("/center/{center_id}/date")
    public Result selectDate(@PathVariable Long center_id, @RequestParam String date, HttpServletRequest request) {
        log.info("[로그인 id 값 : {}] [url : {}] [요청 : 검색 날짜에 따른 시설 주변 현장요원 조회]", request.getSession().getAttribute("loginUser"), "/center/" + center_id + "date?date=" + date);
        Center center = centerService.findById(center_id);
        LocalDate visit_date = LocalDate.parse(date);
        return new Result(mapService.agentNearCenter(center, 2L).stream()
                .map(e -> new CenterSelectDateResponseDTO(agentService.findById(e.getAgent_id()),visit_date))  // 2022-03-24 원보라 바꿈
                .collect(Collectors.toList()));
    }

    @GetMapping("/center/{center_id}")
    @Override
    public Result searchNearCenter(@PathVariable Long center_id, HttpServletRequest request) {
        log.info("[로그인 id값: {}] [url: {}] [요청: 주변 시설 조회]", request.getSession().getAttribute("loginUser"), "/center/" + center_id);
        try {
            Center center = centerService.findById(center_id);
            List<Center> nearList = mapService.centerNearCenter(center);
            List<CenterSearchNearCenterDTO> centerSearchNearCenterDTOList = new ArrayList<CenterSearchNearCenterDTO>();
            nearList.stream()
                    .forEach(e -> {
                        Double distance = mapService.distance(center.getC_latitude(), center.getC_longitude(), e.getC_latitude(), e.getC_longitude()).doubleValue();
                        centerSearchNearCenterDTOList.add(new CenterSearchNearCenterDTO(e, distance));
                    });
            return new Result(centerSearchNearCenterDTOList);
        } catch (NullPointerException e) {
            throw new NullPointerException("검색 결과 없음.");
        }
    }

    /*
        날짜 : 2022/01/11 8:25 오후
        작성자 : 현승구
        작성내용 : 관리자 페이지에서 시설 저장
    */

    @Override
    @PostMapping("/center")
    public void saveCenter(@RequestBody CenterSaveDTO centerSaveDTO, HttpServletResponse response, HttpServletRequest request) throws DuplicateSaveException {
        log.info("[로그인 id 값 : {}] [url : {}] [요청 : 시설 저장]", request.getSession().getAttribute("loginUser"), "/center");
        try {
            Center center = CenterSaveDTO.convertToCenter(centerSaveDTO);
            centerService.saveCenter(center);
        } catch (ParseException e){
            throw new IllegalArgumentException("잘못된 주소 정보 입력됨.");
        } catch (RestClientException e){
            throw new RestClientException("naver map api 호출 중 에러 발생");
        }
    }

    /*
        날짜 : 2022/01/11 9:27 오후
        작성자 : 현승구
        작성내용 : 시설 수정 - 관리자 페이지에서 시설 수정
    */
    @Override
    @PatchMapping("/center")
    public void modifyCenter(@RequestBody CenterModifyDTO centerModifyDTO, HttpServletRequest request) {
        log.info("[로그인 id 값 : {}] [url : {}] [요청 : 시설 수정]", request.getSession().getAttribute("loginUser"), "/center");
        try {
            Center center = CenterModifyDTO.convertToCenter(centerModifyDTO);
            centerService.modifyCenter(center);
        } catch (ParseException e){
            throw new IllegalArgumentException("잘못된 주소 정보 입력됨.");
        } catch (RestClientException e){
            throw new RestClientException("naver map api 호출 중 에러 발생");
        }
    }


    /*
        날짜 : 2022/01/12 12:05 오후
        작성자 : 현승구
        작성내용 : center_id 를 통해서 검색하는 건데 보류
    */

    @Override
    @GetMapping("/center")
    public List<Object> getCenter(@RequestParam Long center, HttpServletRequest request) {
        return null;
    }



}
