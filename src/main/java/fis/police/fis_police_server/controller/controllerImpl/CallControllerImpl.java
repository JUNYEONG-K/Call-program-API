package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.CallController;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.dto.CallSaveRequest;
import fis.police.fis_police_server.dto.CallSaveResponse;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.UserService;
import fis.police.fis_police_server.service.serviceImpl.CallServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
    작성 날짜: 2022/01/10 1:13 오후
    작성자: 고준영
    작성 내용: call controller 기본 구상
*/
@RestController
@RequiredArgsConstructor
@Slf4j
public class CallControllerImpl implements CallController {

    private final CallServiceImpl callService;
    private final UserService userService;
    private final CenterService centerService;

    @Override
    @PostMapping("/call")
    public CallSaveResponse saveCall(@RequestBody CallSaveRequest request, HttpServletRequest req) {
        log.info("[로그인 id값: {}] [url: {}] [요청: 콜기록 저장]", req.getSession().getAttribute("loginUser"), "/call");

        String dateTime = request.getDateTime();
        String date;
        String time;
        try {
            date = dateTime.substring(0, 10);
            time = dateTime.substring(11);
        } catch (NullPointerException e) {
            throw new NullPointerException("시간 정보가 없음.");
        }

        Center center = centerService.findById(request.getCenter_id());
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("loginUser");
        User user = userService.findOneUser(userId);
        return callService.saveCall(request, center, user, date, time);
    }

}
