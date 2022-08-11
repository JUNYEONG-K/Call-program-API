package fis.police.fis_police_server.error.error_result;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
    작성 날짜: 2022/02/18 2:48 오후
    작성자: 고준영
    작성 내용: 예외 응답 api json 폼
*/
@Data
@AllArgsConstructor
public class ErrorResult {
//    private Long id;
    private String status_code;
    private String message;
}
