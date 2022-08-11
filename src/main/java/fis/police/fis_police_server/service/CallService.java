package fis.police.fis_police_server.service;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.dto.CallSaveRequest;
import fis.police.fis_police_server.dto.CallSaveResponse;
import fis.police.fis_police_server.dto.Result;
import fis.police.fis_police_server.dto.UserCallByDateResponse;

import java.time.LocalDateTime;
/*
    작성 날짜: 2022/02/14 11:47 오전
    작성자: 고준영
    작성 내용: 콜 기록 저장
*/
public interface CallService {
    // 연락기록 저장
    CallSaveResponse saveCall(CallSaveRequest request, Center center, User user, String date, String time);
}
