package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*
    작성날짜: 2022/01/12 3:03 PM
    작성자: 이승범
    작성내용: Get AgentList 응답을 위한 DTO
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentGetResponse {

    private Long agent_id;                        //'현장 요원 PK'
    private String a_name;                  //'현장 요원 이름'
    private String a_ph;                    //'현장 요원 전화번호',
    private String a_code;                  //'현장 요원 코드'
    private String a_address;               //'현장 요원 집 주소',
    private boolean a_hasCar;               //'자차 여부'
    private String a_equipment;             //'장비 번호'
    private LocalDate a_receiveDate;    //'장비 수령 날짜'
    private boolean a_status;               //'퇴사 여부'

    private String a_picture;  //현장요원 사진 업로드 null 일때 업로드 안된거임
    private String a_nickname;
    private String a_pwd;
}
