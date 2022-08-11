package fis.police.fis_police_server.dto;

import com.querydsl.core.annotations.QueryProjection;
import fis.police.fis_police_server.domain.enumType.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppScheduleAgentResponse {
    private Long schedule_id;
    private LocalDate visit_date;   //방문 날짜
    private LocalTime visit_time;   //방문시간
    private Integer estimate_num;   //예상인원
    private String center_etc;      // 시설 특이사항
    private String agent_etc;       // 현장요원 특이사항
    private String total_etc;       // 스케쥴 특이사항
    private Accept accept;          //현장요원 일정 수락 여부
    private String late_comment;    //늦는 사유 멘트 현장요원이 선택하면 시설에 띄워주기

    private Long center_id;
    private String c_name;          //시설명
    private String c_address;       //시설주소
    private String c_zipcode;       //우편번호
    private String c_ph;            //전화번호
    private String c_faxNum;        //팩스번호


    @QueryProjection
    public AppScheduleAgentResponse(Long schedule_id, LocalDate visit_date, LocalTime visit_time, Integer estimate_num, String center_etc, String agent_etc, String total_etc, Accept accept, String late_comment, Long center_id, String c_name, String c_address, String c_zipcode, String c_ph, String c_faxNum) {
        this.schedule_id = schedule_id;
        this.visit_date = visit_date;
        this.visit_time = visit_time;
        this.estimate_num = estimate_num;
        this.center_etc = center_etc;
        this.agent_etc = agent_etc;
        this.total_etc = total_etc;
        this.accept = accept;
        this.late_comment = late_comment;
        this.center_id = center_id;
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_zipcode = c_zipcode;
        this.c_ph = c_ph;
        this.c_faxNum = c_faxNum;
    }
}
