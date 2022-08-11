package fis.police.fis_police_server.service;

import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.dto.LoginRequest;
import fis.police.fis_police_server.dto.LoginResponse;
import fis.police.fis_police_server.service.exceptions.LoginServiceException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
//    Object login() throws LoginServiceException;
//
//    Object logout();

    //로그인 시 세션 저장소에 저장할 사용자 pk
    Long loginUserId(LoginRequest request);

    //로그인 res dto
    LoginResponse loginRes(LoginRequest request);

    //로그인 체크 권한 알려주기
    LoginResponse loginCheck(Long loginUser);

    //세션 생성 (로그인)
    void createSession(Object value, HttpServletResponse response);

    //세션 조회 (화면이동)
    Object getSession(HttpServletRequest request);

    //세션 만료 (로그아웃)
    void expire(HttpServletRequest request);

    Cookie findCookie(HttpServletRequest request, String cookieName);
}
