package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
    작성 날짜: 2022/01/10 1:52 오후
    작성자: 고준영
    작성 내용: 메일 전송 응답 api
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailSendResponse {
    private Long center_id;
    private String m_email;
    private String status_code;
}
