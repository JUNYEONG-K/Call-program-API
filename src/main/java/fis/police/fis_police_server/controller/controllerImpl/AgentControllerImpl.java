package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.AgentController;
import fis.police.fis_police_server.domain.Agent;
import fis.police.fis_police_server.domain.enumType.AgentStatus;
import fis.police.fis_police_server.domain.enumType.HasCar;
import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.service.AgentService;
import fis.police.fis_police_server.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/*
    작성날짜: 2022/01/11 1:39 PM
    작성자: 이승범
    작성내용: AgentControllerImpl 생성
*/
@RestController
@RequiredArgsConstructor
@Slf4j
public class AgentControllerImpl implements AgentController {

    private final AgentService agentService;
    private final TokenService tokenService;

    /*
        작성 날짜: 2022/03/15 4:58 오후
        작성자:고준영
        작성 내용: 리팩토링
    */
    @Override
    @PostMapping("/agent")
    public void saveAgent(@RequestBody AgentSaveRequest request, HttpServletRequest servletRequest) throws ParseException {
        log.info("[로그인 id 값: {}] [url: {}] [요청: 현장요원 추가]", servletRequest.getSession().getAttribute("loginUser"), "/agent");
        try {
            agentService.saveAgent(request);
        } catch (RestClientException e) {
            throw new RestClientException("naver map api 요청 에러");
        } catch (ParseException e) {
            throw new ParseException(502);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("잘못된 주소 입력");
        } catch (TransactionSystemException e) {
            throw new TransactionSystemException("요청 데이터 불완전");
        }
    }

    @Override
    @PatchMapping("/agent")
    public void modifyAgent(AgentModifyRequest request, HttpServletRequest servletRequest) throws ParseException {
        log.info("[로그인 id 값: {}] [url: {}] [요청: 현장요원 수정]", servletRequest.getSession().getAttribute("loginUser"), "/agent");
        try {
            log.info("현장요원 agent_id ===============[{}]", request.getAgent_id());
            agentService.updatePicture(request.getAgent_id(), request.getA_picture()); //사진 저장 원보라
            agentService.modifyAgent(request);
        } catch (RestClientException e) {
            throw new RestClientException("naver map api 요청 에러");
        } catch (ParseException e) {
            throw new ParseException(502);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("잘못된 주소 입력");
        } catch (TransactionSystemException e) {
            throw new TransactionSystemException("요청 데이터 불완전");
        }
    }

    @Override
    @GetMapping("/agent") // 전체 현장요원 리스트 조회
    public AgentGetResult getAgent(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        log.info("[로그인 id값 : {}] [url : {}] [요청 : 현장요원 조회]", httpServletRequest.getSession().getAttribute("loginUser"), "/agent");
        List<Agent> AllAgentList = agentService.getAgents();
        List<AgentGetResponse> collect = AllAgentList.stream()
                .map(a -> new AgentGetResponse(a.getId(), a.getA_name(), a.getA_ph(), a.getA_code(), a.getA_address(),
                        HasCar.converter(a.getA_hasCar()), a.getA_equipment(), a.getA_receiveDate(),
                        AgentStatus.converter(a.getA_status()), a.getA_picture(), a.getA_nickname(), a.getA_pwd())
                ).collect(Collectors.toList());
        return new AgentGetResult(collect);
    }


    /*
        날짜 : 2022/02/15 1:27 오후
        작성자 : 원보라
        작성내용 : 현장요원 사진 추가
    */
    @Override
    @PostMapping("/agent/picture")
    //프론트가 보내주는거랑 이름 맞는지 나중에 확인하자
    public void updatePicture(@RequestParam("agent_id") Long Agent_id, @RequestParam("file") MultipartFile multipartFile) {
        agentService.updatePicture(Agent_id, multipartFile);
    }


    @Value("${profileImg.path}")
    private String uploadFolder;

    /*
        날짜 :  2022/02/17 10:39 오전
        작성자 : 원보라
        작성내용 : 저장된 사진 보내주기
    */
    @GetMapping(value = "/agent/show", produces = MediaType.IMAGE_JPEG_VALUE)
    public Object showPicture(@RequestParam("agent_id") Long Agent_id, @RequestParam("time") String time) throws IOException {
        log.info("[url : {}] [요청 : 저장된 사진 보내주기]", "/agent/show?agent_id=" + Agent_id + "&time=" + time);
        try {
            String a_picture = agentService.getPicture(Agent_id);
            InputStream imageStream = new FileInputStream(uploadFolder + a_picture);
            byte[] imageByteArray = IOUtils.toByteArray(imageStream);
            imageStream.close();
            return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
        }  catch (FileNotFoundException e){
            log.error(e.getMessage());
            throw new FileNotFoundException("NoSuchFile");
        }
    }

    //사진 삭제
    @GetMapping("/agent/picture/delete")
    public void deletePicture(@RequestParam("agent_id") Long agent_id, HttpServletResponse response) {
        log.info("[url : {}] [요청 : 사진 삭제]", "/agent/picture/delete?agent_id=" + agent_id);
        try {
            agentService.deletePicture(agent_id);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(500);
        }
    }


    /*
        날짜 : 2022/03/11 1:47 오후
        작성자 : 원보라
        작성내용 : APP 현장요원 위치 받아오기
    */
    @Override
    @PostMapping("/app/agent/currentLocation")
    public void saveCurrentLocation(@RequestBody AgentLocation request, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        Long agent_id = tokenService.getAgentFromRequest(authorizationHeader).getId();
        log.info("[로그인 id값: {}] [url: {}] [요청: 한장요원 완료된 일정 조회]", tokenService.getAgentFromRequest(authorizationHeader).getId(), "/app/schedule/agent");
        log.info("[로그인 역할: {}]", (String) tokenService.parseJwtToken(authorizationHeader).get("role"));

        try {
            agentService.saveCurrentLocation(agent_id, request);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("NoToken");
        } catch (NullPointerException e) {
            throw new NullPointerException("NoAGENT");
        }
    }

//    @Override
//    @PostMapping("/agent") // 현장요원 추가
//    public void saveAgent(@RequestBody AgentSaveRequest request, HttpServletResponse response, HttpServletRequest httpServletRequest) {
//        try {
//            agentService.saveAgent(request);
//        } catch (IllegalStateException ie) { // 현장요원 코드 중복
//            log.warn("[로그인 id값 : {}] [url: {}] [현장요원코드 중복 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", ie.getMessage());
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            throw ie;
//        } catch (RestClientException re) { // naver Map api 요청 에러
//            log.warn("[로그인 id값 : {}] [url: {}] [naver Map API 요청 에러 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", re.getMessage());
//            response.setStatus(501);
//        } catch (ParseException pe) { // naver Map api 파싱 에러(예외처리 필수)
//            log.warn("[로그인 id값 : {}] [url: {}] [naver Map API 파싱 에러(예외처리 구현 필수) {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", pe.getMessage());
//            response.setStatus(502);
//        } catch (IndexOutOfBoundsException oe) { // 잘못된 주소 입력
//            log.warn("[로그인 id값 : {}] [url: {}] [잘못된 주소 입력 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", oe.getMessage());
//            response.setStatus(403);
//        } catch (TransactionSystemException tse) {
//            log.warn("[로그인 id값 : {}] [url: {}] [요청 데이터 불완전 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", tse.getMessage());
//            response.setStatus(402);
//        } catch (Exception e) {
//            log.error("[로그인 id값 : {}] [url:{}] [예상치못한 에러 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", e.getMessage());
//        }
//    }


//    @Override
//    @PatchMapping("/agent") // 현장요원 정보 수정
//    public void modifyAgent(AgentModifyRequest request,HttpServletResponse response, HttpServletRequest httpServletRequest) {
//        try {
//            agentService.updatePicture(request.getAgent_id(), request.getA_picture()); //사진 저장 원보라
//            agentService.modifyAgent(request);
//        } catch (IllegalStateException ie) { // 현장요원 코드 중복
//            log.warn("[로그인 id값 : {}] [url: {}] [현장요원코드 중복 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", ie.getMessage());
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } catch (RestClientException re) { // naver Map api 요청 에러
//            log.warn("[로그인 id값 : {}] [url: {}] [naver Map API 요청 에러 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", re.getMessage());
//            response.setStatus(501);
//        } catch (ParseException pe) { // naver Map api 파싱 에러(예외처리 필수)
//            log.warn("[로그인 id값 : {}] [url: {}] [naver Map API 파싱 에러(예외처리 구현 필수) {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", pe.getMessage());
//            response.setStatus(502);
//        } catch (IndexOutOfBoundsException oe) { // 잘못된 주소 입력
//            log.warn("[로그인 id값 : {}] [url: {}] [잘못된 주소 입력 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", oe.getMessage());
//            response.setStatus(403);
//        } catch (TransactionSystemException tse) {
//            log.warn("[로그인 id값 : {}] [url: {}] [요청 데이터 불완전 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", tse.getMessage());
//            response.setStatus(402);
//        } catch (Exception e) {
//            log.error("[로그인 id값 : {}] [url:{}] [예상치못한 에러 {}]",
//                    httpServletRequest.getSession().getAttribute("loginUser"), "/agent", e.getMessage());
//        }
//    }
}


