package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
    작성 날짜: 2022/01/10 1:15 오후
    작성자: 고준영
    작성 내용: call 기록 저장 완료 후 프론트로 응답할 api 스펙
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallSaveResponse {

    private Long center_id;
    private Long call_id;

    private String status_code;
}
