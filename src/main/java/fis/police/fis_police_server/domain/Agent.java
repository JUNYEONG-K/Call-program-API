package fis.police.fis_police_server.domain;

import fis.police.fis_police_server.domain.enumType.AgentStatus;
import fis.police.fis_police_server.domain.enumType.HasCar;
import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.AgentModifyRequest;
import fis.police.fis_police_server.dto.AgentSaveRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agent_id")
    private Long id;         //'primary_key'

    @OneToMany(mappedBy = "agent")
    private List<Schedule> scheduleList = new ArrayList<Schedule>();

    @NotBlank
    @Column(length = 100)
    private String a_name;                             //'현장 요원 이름'

    @NotBlank
    @Column(length = 100)
    private String a_ph;                               //'현장 요원 전화번호',

    @NotBlank
    @Column(length = 100, unique = true)
    private String a_code;                             //'현장 요원 코드'

    @NotBlank
    @Column(length = 100)
    private String a_address;                          //'현장 요원 집 주소',

    @NotNull
    @Enumerated(EnumType.STRING)
    private HasCar a_hasCar;                            //'자차 여부'

    @Column(length = 100)
    private String a_equipment;                         //'장비 번호'

    private LocalDate a_receiveDate;                //'장비 수령 날짜'

    @NotNull
    private Double a_latitude;                          //'현장 요원 위도',
    @NotNull
    private Double a_longitude;                         //'현장 요원 경도',

    @NotNull
    @Enumerated(EnumType.STRING)
    private AgentStatus a_status;                       //'퇴사 여부'

    /*
        작성 날짜: 2022/02/16 4:20 오후
        작성자: 고준영
        작성 내용: 요원 권한 -> AGENT, 요원아이디&비번
    */
//    @NotNull // enum 때문에 notblank 안됨
    @Column
    @Enumerated(EnumType.STRING)
    private UserAuthority u_auth;                  // '권한'

//    @NotBlank
    @Column(length = 100)
    private String a_nickname;              // "현장요원 id"

//    @NotBlank
    @Column(length = 100)
    private String a_pwd;                   // '현장요원 비밀번호',

    /*
        날짜 : 2022/02/10 4:30 오후
        작성자 : 원보라
        작성내용 : 앱 도메인 추가
    */
    @OneToMany(mappedBy = "agent")
    private List<Confirm> confirmList = new ArrayList<Confirm>();


    private String a_picture;   //현장요원 사진

    /*
        날짜 : 2022/03/11 1:42 오후
        작성자 : 원보라
        작성내용 : 현장요원 현재 위치
    */

    private String a_cur_lat;                          //'현장 요원 현재 위도',
    private String a_cur_long;                         //'현장 요원 현재 경도',




    /*
        작성날짜: 2022/01/11 5:05 PM
        작성자: 이승범
        작성내용: AgentService 구현을 위한 연관관계 메서드 및 setter 구현
    */
    // 생성 메서드
    public static Agent createAgent(String a_name, String a_ph, String a_code, String a_address, HasCar a_hasCar,
                             String a_equipment, LocalDate a_receiveDate, Double a_longitude, Double a_latitude, UserAuthority u_auth, String nickname, String pwd) {
        Agent agent = new Agent();
        agent.a_name = a_name;
        agent.a_ph = a_ph;
        agent.a_code = a_code;
        agent.a_address = a_address;
        agent.a_hasCar = a_hasCar;
        agent.a_equipment = a_equipment;
        agent.a_receiveDate = a_receiveDate;
        agent.a_latitude = a_latitude;
        agent.a_longitude = a_longitude;
        agent.a_status = AgentStatus.WORK;
        agent.u_auth = u_auth;
        agent.a_nickname = nickname;
        agent.a_pwd = pwd;
        return agent;
    }

    public static Agent createAgent(String a_name, String a_ph, String a_code, String a_address, HasCar a_hasCar,
                                    String a_equipment, LocalDate a_receiveDate, Double a_longitude, Double a_latitude, UserAuthority u_auth) {
        Agent agent = new Agent();
        agent.a_name = a_name;
        agent.a_ph = a_ph;
        agent.a_code = a_code;
        agent.a_address = a_address;
        agent.a_hasCar = a_hasCar;
        agent.a_equipment = a_equipment;
        agent.a_receiveDate = a_receiveDate;
        agent.a_latitude = a_latitude;
        agent.a_longitude = a_longitude;
        agent.a_status = AgentStatus.WORK;
        agent.u_auth = u_auth;
        return agent;
    }

    public static Agent createAgent(AgentSaveRequest request, HasCar a_hasCar, Double a_longitude, Double a_latitude){
        Agent agent = new Agent();
        agent.a_name = request.getA_name();
        agent.a_ph = request.getA_ph();
        agent.a_code = request.getA_code();
        agent.a_address = request.getA_address();
        agent.a_hasCar = a_hasCar;
        agent.a_equipment = request.getA_equipment();
        agent.a_receiveDate = request.getA_receiveDate();
        agent.a_longitude = a_longitude;
        agent.a_latitude = a_latitude;
        agent.a_status = AgentStatus.WORK;
        agent.u_auth = UserAuthority.AGENT;
        agent.a_nickname = request.getNickname();
        agent.a_pwd = request.getPwd();
        return agent;
    }

    // 현장요원 정보 수정을 위한 setter
    public void modifyAgent(AgentModifyRequest request, HasCar a_hasCar, Double a_longitude, Double a_latitude, AgentStatus a_status) {
        this.a_name = request.getA_name();
        this.a_ph = request.getA_ph();
        this.a_code = request.getA_code();
        this.a_address = request.getA_address();
        this.a_equipment = request.getA_equipment();
        this.a_receiveDate = request.getA_receiveDate();
        this.a_hasCar = a_hasCar;
        this.a_status = a_status;
        this.a_longitude = a_longitude;
        this.a_latitude = a_latitude;
        this.a_nickname = request.getNickname();
        this.a_pwd = request.getPwd();
    }

    /*
        날짜 : 2022/02/04 10:33 오전
        작성자 : 현승구
        작성내용 : 테스트 위한 생성자
    */
    public Agent(String a_name, String a_code) {
        this.a_name = a_name;
        this.a_code = a_code;
    }


    /*
        날짜 : 2022/02/16 10:35 오전
        작성자 : 원보라
        작성내용 : 현장요원 사진 업로드
    */
    public void uploadPicture(String a_picture){
        this.a_picture = a_picture;
    }


    /*
        날짜 : 2022/03/11 1:55 오후
        작성자 : 원보라
        작성내용 : 현장요원 현재 위치 저장
    */
    public void saveCurLocation(String a_cur_lat, String a_cur_long){
        this.a_cur_lat=a_cur_lat;
        this.a_cur_long = a_cur_long;
    }
}


