package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.dto.UserInfoResponse;
import fis.police.fis_police_server.dto.UserSaveRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//원보라

public interface UserController {
    // 콜직원 추가
    void saveUser(UserSaveRequest request, HttpServletResponse response, HttpServletRequest req);

    // 콜직원 조회 (처음 화면 접속시 보여주는 리스트)
    List<UserInfoResponse> getUser();
}
