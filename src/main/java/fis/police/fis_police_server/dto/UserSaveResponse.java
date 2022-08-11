package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    날짜 : 2022/01/10 2:29 오후
    작성자 : 원보라
    작성내용 : user 추가 후 응답
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveResponse {
    private Long user_id;
    private String status_code;
}
