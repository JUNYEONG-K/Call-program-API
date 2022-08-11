package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.Complete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class CalendarDTO {
    private String agent_name;  // 현장요원 정보를 주는 것, 다른 내용을 줘도 됨
    private String center_name; // 시설 정보를 주는 것, 다른 내용을 줘도 됨
    private String visit_date;
    private Complete complete;
}
