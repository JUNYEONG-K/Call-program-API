package fis.police.fis_police_server.service.serviceImpl;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.dto.CallSaveRequest;
import fis.police.fis_police_server.dto.CallSaveResponse;
import fis.police.fis_police_server.repository.repoImpl.CallRepositoryImpl;
import fis.police.fis_police_server.repository.repoImpl.CenterRepositoryImpl;
import fis.police.fis_police_server.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    작성 날짜: 2022/01/10 1:17 오후
    작성자: 고준영
    작성 내용:  call 기록 저장
*/
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CallServiceImpl implements CallService {

    private final CallRepositoryImpl callRepository;
    private final CenterRepositoryImpl centerRepository;

    @Transactional
    @Override
    public CallSaveResponse saveCall(CallSaveRequest request, Center center, User user, String date, String time) {


        // 저장할 콜 객체 생성
        Call call = Call.createCall(request, center, user, date, time);
        // 콜 저장 (콜 기록은 수정 불가)
        callRepository.save(call);

        centerRepository.update_participation(request.getCenter_id(), request.getParticipation());


        // 센터의 참여여부 즉시 업데이트
//        center.update_participation(request.getParticipation());


        CallSaveResponse response = new CallSaveResponse();
        response.setCenter_id(call.getCenter().getId());
        response.setCall_id(call.getId());
        response.setStatus_code("200");

        return response;
    }

}
