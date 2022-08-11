package fis.police.fis_police_server.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppScheduleFilterDTO {
    private LocalDate visit_date;   //방문 날짜
    private LocalTime visit_time;   //방문시간
    private Long center_id;
    private Long count;

    @QueryProjection
    public AppScheduleFilterDTO(LocalDate visit_date, LocalTime visit_time, Long center_id, Long count) {
        this.visit_date = visit_date;
        this.visit_time = visit_time;
        this.center_id = center_id;
        this.count = count;
    }

}
