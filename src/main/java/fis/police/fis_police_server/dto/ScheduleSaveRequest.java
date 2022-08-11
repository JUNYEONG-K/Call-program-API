package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/*
    작성날짜: 2022/01/12 4:17 PM
    작성자: 이승범
    작성내용: 스케줄 저장을 위한 request DTO
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSaveRequest {
    private Long center_id;
    private Long agent_id;
    private LocalDate receipt_date;
    private LocalDate visit_date;
    private LocalTime visit_time;
    private Integer estimate_num;
    private String center_etc;
    private String agent_etc;
}
