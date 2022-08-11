package fis.police.fis_police_server.dto;

import com.querydsl.core.annotations.QueryProjection;
import fis.police.fis_police_server.domain.enumType.Complete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class AppScheduleResponse {
    private Long schedule_id;
    private LocalDate visit_date;
    private LocalTime visit_time;
    private Integer estimate_num;
    private String center_etc;
    private String agent_etc;
    private String total_etc;

    private Long center_id;
    private String c_name;
    private String c_address;
    private String c_zipcode;
    private String c_ph;
    private String c_faxNum;

    private Complete complete; //확인서 작성 여부

    @QueryProjection
    public AppScheduleResponse(Long schedule_id, LocalDate visit_date, LocalTime visit_time, Integer estimate_num, String center_etc, String agent_etc, String total_etc, Long center_id, String c_name, String c_address, String c_zipcode, String c_ph, String c_faxNum, Complete complete) {
        this.schedule_id = schedule_id;
        this.visit_date = visit_date;
        this.visit_time = visit_time;
        this.estimate_num = estimate_num;
        this.center_etc = center_etc;
        this.agent_etc = agent_etc;
        this.total_etc = total_etc;
        this.center_id = center_id;
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_zipcode = c_zipcode;
        this.c_ph = c_ph;
        this.c_faxNum = c_faxNum;
        this.complete = complete;
    }
}
