package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.dto.*;
import fis.police.fis_police_server.service.exceptions.DuplicateSaveException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//현승구
/*
    날짜 : 2022/01/11 3:02 오후
    작성자 : 현승구
    작성내용 : interface 명세
*/
public interface CenterController {

    // 시설검색 서버에서 문제 발생시 오류코드 + null 반환  나머지는 결과 값 전송
    Result searchCenter(String c_name, String c_address, String c_ph, HttpServletRequest request);

    // 시설 선택
    Result selectCenter(Long center_id, HttpServletRequest request);

    // 날짜 선택 시 주변 현장요원 반환
    Result selectDate(Long center_id, String date, HttpServletRequest request);

    // 주변시설 검색
    Result searchNearCenter(Long center_id, HttpServletRequest request);

    // 시설 추가
    void saveCenter(CenterSaveDTO centerSaveDTO, HttpServletResponse response, HttpServletRequest request) throws DuplicateSaveException;

    // 시설 수정
    void modifyCenter(CenterModifyDTO centerModifyDTO, HttpServletRequest request);

    // 시설 조회
    List<Object> getCenter(Long center, HttpServletRequest request);
}
