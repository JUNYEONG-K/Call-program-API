package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
    작성날짜: 2022/01/12 3:39 PM
    작성자: 이승범
    작성내용: Response 할때 json 배열을 객체로 감사기 위한 DTO
*/
@Data
@AllArgsConstructor
public class AgentGetResult<T>{
    private T data;
}


