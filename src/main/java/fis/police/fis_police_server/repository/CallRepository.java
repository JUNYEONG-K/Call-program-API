package fis.police.fis_police_server.repository;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.dto.CallAvgDTO;
import fis.police.fis_police_server.dto.CallTodayDTO;

import java.util.List;

/*
    작성 날짜: 2022/01/10 10:17 오전
    작성자: 고준영
    작성 내용:  call repository interface 기본 메서드(save, findById)
*/
public interface CallRepository {
    // 콜 기록 저장
    void save(Call call);
    // 콜 기록 조회
    List<Call> findAll();
    // 콜 기록 조회 (하나)
    Call findById(Long id);
    // 콜 기록 조회 (날짜별)
    List<Call> callByDate(String date);
    // 콜 기록 조회 (시설별)
    List<Call> callByCenter(Long id);

    List<CallTodayDTO> todayCallNum(String today);
    List<CallAvgDTO> totalCallNum();
}
