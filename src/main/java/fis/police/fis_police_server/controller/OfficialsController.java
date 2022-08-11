package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.OfficialSaveRequest;
import fis.police.fis_police_server.dto.WellSaveResponse;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/*
    작성 날짜: 2022/02/14 11:33 오전
    작성자: 고준영
    작성 내용: 시설 담당자 회원가입
*/
public interface OfficialsController {

    // 시설 담당자 추가 (회원 가입)
    WellSaveResponse saveOfficials(OfficialSaveRequest request);
    WellSaveResponse modifyOfficials(OfficialSaveRequest request, HttpServletRequest httpServletRequest);
}
