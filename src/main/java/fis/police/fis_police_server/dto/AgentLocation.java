package fis.police.fis_police_server.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    날짜 : 2022/03/11 1:51 오후
    작성자 : 원보라
    작성내용 : 현장요원 현재 위치
*/
@Data
@NoArgsConstructor
public class AgentLocation {
    private Long agent_id;
    private String a_cur_lat;                          //현장 요원 현재 위도
    private String a_cur_long;                         //현장 요원 현재 경도


    @QueryProjection
    public AgentLocation(Long agent_id, String a_cur_lat, String a_cur_long) {
        this.agent_id = agent_id;
        this.a_cur_lat = a_cur_lat;
        this.a_cur_long = a_cur_long;
    }
}
