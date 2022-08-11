package fis.police.fis_police_server.domain;

import fis.police.fis_police_server.domain.enumType.Accept;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.dto.ScheduleModifyRequest;

import fis.police.fis_police_server.dto.ScheduleSaveRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@DynamicInsert
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @Column
    private LocalDate receipt_date;             // '접수일'

    @Column
    private LocalDate visit_date;               // '방문날짜'

    @Column
    private LocalTime visit_time;               // '방문시간'

    private Integer estimate_num;             // '예상인원'

    @Column
    @Lob
    private String center_etc;               // 시설 특이사항

    @Column
    @Lob
    private String agent_etc;                // 현장요원 특이사항

    @Column
    @Lob
    private String total_etc;               // 스케쥴 특이사항

    @Column(length = 20)
    private String call_check;              // 최근 통화 상태

    @Column(length = 100)
    private String call_check_info;         // 최근 통화 상태 설명

    @Column
    @Lob
    private String modified_info;           // 변경 사항

    @NotNull
    @Column
    private boolean valid;                 // 스케줄 유효한지

    /*
        날짜 : 2022/02/10 4:08 오후
        작성자 : 원보라
        작성내용 : 앱 도메인 추가
    */
    //null 이면 아직 수락 거부 안한 상태
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="varchar(32) default 'TBD'")
    private Accept accept;  //현장요원 일정 수락 여부

    private String late_comment;    //늦는 사유 멘트 현장요원이 선택하면 시설에 띄워주기

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition="varchar(32) default 'incomplete'")
    private Complete complete;


    /*
        날짜 : 2022/01/11 5:24 오후
        작성자 : 현승구
        작성내용 : test 코드를 위한 constructor
    */
    public Schedule(Center center, String center_etc) {
        this.center = center;
        this.center_etc = center_etc;
    }

    /*
        작성날짜: 2022/01/12 4:43 PM
        작성자: 이승범
        작성내용: ScheduleService 구현을 위한  연관관계 메서드 및 생성 메서드 구현
    */
    public static Schedule createSchedule(Center center, User user, Agent agent, LocalDate receipt_date,
                                          LocalDate visit_date, LocalTime visit_time, Integer estimate_num,
                                          String center_etc, String agent_etc,Accept accept, String late_comment) {
        Schedule schedule = new Schedule();
        schedule.mappingCenter(center);
        schedule.mappingUser(user);
        schedule.mappingAgent(agent);
        schedule.receipt_date = receipt_date;
        schedule.visit_date = visit_date;
        schedule.visit_time = visit_time;
        schedule.estimate_num = estimate_num;
        schedule.center_etc = center_etc;
        schedule.agent_etc = agent_etc;
        schedule.valid = true;
        /*
            날짜 : 2022/02/11 3:45 오후
            작성자 : 원보라
            작성내용 : 앱 컬럼 추가
        */
        schedule.accept = accept;
        schedule.late_comment = late_comment;
        return schedule;
    }

    public static Schedule createSchedule(Center center, User user, Agent agent, ScheduleSaveRequest request) {
        Schedule schedule = new Schedule();
        schedule.mappingCenter(center);
        schedule.mappingUser(user);
        schedule.mappingAgent(agent);
        schedule.receipt_date = request.getReceipt_date();
        schedule.visit_date = request.getVisit_date();
        schedule.visit_time = request.getVisit_time();
        schedule.estimate_num = request.getEstimate_num();
        schedule.center_etc = request.getCenter_etc();
        schedule.agent_etc = request.getAgent_etc();
        schedule.valid = true;
        return schedule;
    }

    public void modifySchedule(ScheduleModifyRequest request, Agent agent, Center center) {
        this.mappingAgent(agent);
        this.mappingCenter(center);
        this.estimate_num = request.getEstimate_num();
        this.visit_date = request.getVisit_date();
        this.visit_time = request.getVisit_time();
        this.center_etc = request.getCenter_etc();
        this.agent_etc = request.getAgent_etc();
        this.modified_info = request.getModified_info();
        this.total_etc = request.getTotal_etc();
        this.call_check = request.getCall_check();
        this.call_check_info = request.getCall_check_info();
        this.accept = request.getAccept();
    }

    /*
        날짜 : 2022/02/11 3:46 오후
        작성자 : 원보라
        작성내용 : updateLateComment, updateAccept
    */
    public void updateLateComment(String late_comment) {
        this.late_comment = late_comment;
    }

    public void updateAccept(Accept accept) {
        this.accept = accept;
    }



    public void cancel(){
        this.valid = false;
    }

    // ============ 연관관계 메서드 ===============
    public void mappingCenter(Center center) {
        this.center = center;
        center.getScheduleList().add(this);
    }

    public void mappingAgent(Agent agent) {
        this.agent = agent;
        agent.getScheduleList().add(this);
    }

    public void mappingUser(User user) {
        this.user = user;
    }
}
