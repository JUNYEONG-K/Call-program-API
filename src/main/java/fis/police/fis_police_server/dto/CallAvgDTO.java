package fis.police.fis_police_server.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;


/*
    날짜 : 2022/01/18 2:08 오후
    작성자 : 원보라
    작성내용 : user 별 평균 통화 개수
*/

@Data
public class CallAvgDTO {
    private Long user_id;
    private Double call_avg_num;


    @QueryProjection
    public CallAvgDTO(Long user_id, Double call_avg_num) {
        this.user_id = user_id;
        this.call_avg_num = call_avg_num;
    }

}
