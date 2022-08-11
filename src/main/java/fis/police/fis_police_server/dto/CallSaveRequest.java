package fis.police.fis_police_server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.User;
import fis.police.fis_police_server.domain.enumType.InOut;
import fis.police.fis_police_server.domain.enumType.Participation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
/*
    작성 날짜: 2022/01/10 1:15 오후
    작성자: 고준영
    작성 내용:  call 기록 저장 시 받아올 api 스펙
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallSaveRequest {
    private Long center_id;
//    private Long user_id;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String dateTime;
    private Participation participation;
    private InOut in_out;
    private String c_manager;
    private String m_ph;
    private String m_email;
    private Integer num;
    private String center_etc;
    private String agent_etc;
}
