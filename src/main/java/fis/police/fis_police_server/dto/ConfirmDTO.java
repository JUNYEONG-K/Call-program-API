package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.Complete;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmDTO {

    private Long confirm_id;
    private String visit_date;
    private String visit_time;
    private String center_name;
    private String agent_name;  // list 로 바꿔야함.
    private String new_child;
    private String old_child;   // 기존 아동

    private String senile;  // 치매 환자
    private String disabled;    // 지적장애
    private String etc;
    private Complete complete;  // 시설이 확인했는지 여부
}
