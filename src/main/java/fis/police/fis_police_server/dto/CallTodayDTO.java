package fis.police.fis_police_server.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;


/*
    날짜 : 2022/01/18 11:01 오전
    작성자 : 원보라
    작성내용 : user 별 오늘 콜 넘버
*/

@Data
public class CallTodayDTO {
    private Long user_id;
    private Long call_num;


    @QueryProjection
    public CallTodayDTO(Long user_id, Long call_num) {
        this.user_id = user_id;
        this.call_num = call_num;
    }
}
