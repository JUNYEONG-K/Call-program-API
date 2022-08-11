package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.Call;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.Schedule;
import fis.police.fis_police_server.domain.enumType.InOut;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.Visited;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    날짜 : 2022/01/11 5:10 오후
    작성자 : 현승구
    작성내용 : SelectDTO작성
*/

@Getter
@Setter
@Data
public class CenterSelectResponseDTO {
    private Long center_id;     // 'primary_key',
    private String c_sido;        // '시도',
    private String c_sigungu;     // '시군구',
    private String c_name;        // '시설명',
    private String c_type;        // '유형',
    private String c_status;      // '운영현황',
    private String c_address;     // '주소',
    private String c_zipcode;     // '우편번호',
    private String c_ph;          // '전화번호',
    private String c_faxNum;      // '팩스번호',
    private String c_people;      // '현원',
    private String c_hpAddress;   // '홈페이지주소',
    private Double c_latitude;    // '위도',
    private Double c_longitude;   // '경도',
    private Participation participation;
    private Visited visited;
    private List<CenterSearchNearCenterDTO> centerList = new ArrayList<>();
    private List<CallDTO> callList = new ArrayList<CallDTO>();
    private List<ScheduleDTO> scheduleList = new ArrayList<ScheduleDTO>();

    public CenterSelectResponseDTO(Center center, List<CenterSearchNearCenterDTO> centerSearchNearCenterDTOList) {
        this.center_id = center.getId();
        this.c_sido = center.getC_sido();
        this.c_sigungu = center.getC_sigungu();
        this.c_name = center.getC_name();
        this.c_type = center.getC_type();
        this.c_status = center.getC_status();
        this.c_address = center.getC_address();
        this.c_zipcode = center.getC_zipcode();
        this.c_ph = center.getC_ph();
        this.c_faxNum = center.getC_faxNum();
        this.c_people = center.getC_people();
        this.c_hpAddress = center.getC_hpAddress();
        this.c_latitude = center.getC_latitude();
        this.c_longitude = center.getC_longitude();
        this.participation = center.getParticipation();
        this.visited = center.getVisited();
        this.centerList = centerSearchNearCenterDTOList;
        this.callList = center.getCallList().stream()
                .map(call -> new CallDTO(call))
                .collect(Collectors.toList());
        center.getScheduleList().stream()
                .forEach(schedule -> {
                    if(schedule.isValid()) {
                        this.scheduleList.add(new ScheduleDTO(schedule));
                    }
                });
    }

    @Data
    private static class CallDTO{
        private Long call_id;         //BIGINT                 NOT NULL    AUTO_INCREMENT      comment 'primary_key',
        private UserDTO user;
        private String dateTime;       //'입력날짜 및 시간',
        private Participation participation;  // '참여여부(참여/거부/보류/기타)',
        private InOut in_out;          // '접수방법',
        private String c_manager;      //'시설 담당자 성명',
        private String m_ph;           //시설 담당자 전화번호',
        private String m_email;       // '시설 담당자 이메일 ',
        private Integer num;          // '시설 예상인원'
        private String center_etc;    // '시설 특이사항
        private String agent_etc;     // '현장요원 특이사항

        public CallDTO(Call call){
            this.call_id = call.getId();
            this.user = new UserDTO(call.getUser().getId(), call.getUser().getU_name());
            this.dateTime = call.getDate() + call.getTime();
            this.participation = call.getParticipation();
            this.in_out = call.getIn_out();
            this.c_manager = call.getC_manager();
            this.m_ph = call.getM_ph();
            this.m_email = call.getM_email();
            this.num = call.getNum();
            this.center_etc = call.getCenter_etc();
            this.agent_etc = call.getAgent_etc();
        }
    }

    @Data
    private static class ScheduleDTO{
        private Long schedule_id;
        private UserDTO user;        // BIGINT                 NOT NULL                        comment 'user_id'
        private Long user_id;
        private AgentDTO agent;
        private LocalDate receipt_date;             // '접수일'
        private LocalDate visit_date;               // '방문날짜'
        private LocalTime visit_time;               // '방문시간'
        private Integer estimate_num;             // '예상인원'
        private String center_etc;               // 시설 특이사항
        private String agent_etc;                // 현장요원 특이사항
        private String total_etc;               // 비고

        public ScheduleDTO(Schedule schedule) {
            this.schedule_id = schedule.getId();
            this.user = new UserDTO(schedule.getUser().getId(), schedule.getUser().getU_name());
            this.user_id = schedule.getUser().getId();
            this.agent = new AgentDTO(schedule.getAgent().getId(), schedule.getAgent().getA_name(), schedule.getAgent().getA_code());
            this.receipt_date = schedule.getReceipt_date();
            this.visit_date = schedule.getVisit_date();
            this.visit_time = schedule.getVisit_time();
            this.estimate_num = schedule.getEstimate_num();
            this.center_etc = schedule.getCenter_etc();
            this.agent_etc = schedule.getAgent_etc();
            this.total_etc = schedule.getTotal_etc();
        }
    }

    @Data
    @AllArgsConstructor
    public static class UserDTO{
        Long user_id;
        String user_name;
    }

    @Data
    @AllArgsConstructor
    public static class AgentDTO{
        Long agent_id;
        String agent_name;
        String agent_code;
    }
}
