package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.dto.*;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
    작성날짜: 2022/01/12 3:38 PM
    작성자: 이승범
    작성내용: json 배열을 객체로 감싸기 위해 getAgent 반환값 변경
*/
// 이승범
public interface AgentController {
    // 현장요원 추기
    void saveAgent(AgentSaveRequest request, HttpServletRequest httpServletRequest) throws ParseException;

    // 현장요원 수정
    void modifyAgent(AgentModifyRequest request, HttpServletRequest httpServletRequest) throws ParseException;

    // 현장요원 조회
    AgentGetResult getAgent(HttpServletRequest httpServletRequest, HttpServletResponse response);

    /*
        날짜 : 2022/02/15 1:22 오후
        작성자 : 원보라
        작성내용 : 현장요원 사진 추가
    */
    void updatePicture(Long Agent_id, MultipartFile multipartFile);

    void saveCurrentLocation(AgentLocation request, HttpServletResponse response, HttpServletRequest req);
}