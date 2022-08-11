//package fis.police.fis_police_server.dto;
//
//import fis.police.fis_police_server.domain.enumType.Accept;
//import lombok.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//
//@Data
//public class AppCombineResponse {
//    private Long schedule_id;
//    private LocalDate visit_date;   //방문 날짜
//    private LocalTime visit_time;   //방문시간
//    private Integer estimate_num;   //예상인원
//    private String center_etc;      // 시설 특이사항
//    private String agent_etc;       // 현장요원 특이사항
//    private String total_etc;       // 스케쥴 특이사항
//    private Accept accept;          //현장요원 일정 수락 여부
//    private String late_comment;    //늦는 사유 멘트 현장요원이 선택하면 시설에 띄워주기
//
//    private Long center_id;
//    private Double c_latitude;      //위도
//    private Double c_longitude;     //경도
//
//    private List<AgentList> agentLists; //같이 가는 agent list
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    static public class AgentList {
//        private Long agent_id;
//        private String a_name;          //현장 요원 이름
//        private String a_ph;            //현장 요원 전화번호
//        private String a_code;          //현장 요원 코드
//    }
//}
