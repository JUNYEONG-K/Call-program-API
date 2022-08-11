package fis.police.fis_police_server.domain;

import com.mysema.commons.lang.Pair;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.Visited;
import fis.police.fis_police_server.dto.ExcelCenterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Center {

    @Id
    @GeneratedValue
    @Column(name = "center_id")
    private Long id;     // 'primary_key',

    @Column(length = 100)
    private String c_sido;        // '시도',

    @Column(length = 100)
    private String c_sigungu;     // '시군구',

    @Column(length = 100)
    private String c_name;        // '시설명',

    @Column(length = 100)
    private String c_type;        // '유형',

    @Column(length = 100)
    private String c_status;      // '운영현황',

    @Column(length = 100)
    private String c_address;     // '주소',

    @Column(length = 100)
    private String c_zipcode;     // '우편번호',

    @Column(length = 100)
    private String c_ph;          // '전화번호',

    @Column(length = 100)
    private String c_faxNum;      // '팩스번호',

    @Column(length = 100)
    private String c_people;      // '현원',

    @Column(length = 100)
    private String c_hpAddress;   // '홈페이지주소',

    private Double c_latitude;    // '위도',
    private Double c_longitude;   // '경도',

    @Enumerated(EnumType.STRING)
    private Participation participation;

    @Enumerated(EnumType.STRING)
    private Visited visited;

    @OneToMany(mappedBy = "center", cascade = CascadeType.PERSIST)
    private List<Call> callList = new ArrayList<Call>();

    @OneToMany(mappedBy = "center", cascade = CascadeType.PERSIST)
    private List<Schedule> scheduleList = new ArrayList<Schedule>();

    /*
        작성 날짜: 2022/02/14 1:35 오후
        작성자: 고준영
        작성 내용: 신청서와 시설을 연결
    */
    @OneToMany(mappedBy = "center")
    private List<Hope> hopeList = new ArrayList<Hope>();
    /*
        날짜 : 2022/02/10 4:40 오후
        작성자 : 원보라
        작성내용 : 앱 도메인 추가
    */
    @OneToMany(mappedBy = "center")
    private List<Confirm> confirmList = new ArrayList<Confirm>();

    /*
        작성 날짜: 2022/03/17 1:13 오후
        작성자: 고준영
        작성 내용: excel 용 생성자
    */
    public Center(String c_sido, String c_sigungu, String c_name, String c_type, String c_status, String c_address, String c_zipcode, String c_ph, String c_faxNum, String c_people, String c_hpAddress) {
        this.c_sido = c_sido;
        this.c_sigungu = c_sigungu;
        this.c_name = c_name;
        this.c_type = c_type;
        this.c_status = c_status;
        this.c_address = c_address;
        this.c_zipcode = c_zipcode;
        this.c_ph = c_ph;
        this.c_faxNum = c_faxNum;
        this.c_people = c_people;
        this.c_hpAddress = c_hpAddress;
    }

    /*
        날짜 : 2022/01/13 3:51 오후
        작성자 : 현승구
        작성내용 : 위도 경도 설정자
    */

    public void setLocation(Pair<Double, Double> location){
        this.c_longitude = location.getFirst();
        this.c_latitude = location.getSecond();
    }
    /*
        날짜 : 2022/01/11 1:27 오후
        작성자 : 현승구
        작성내용 : 테스트용 생성자
    */

    public Center(String c_name, String c_address, String c_ph){
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_ph = c_ph;
    }
    /*
        작성 날짜: 2022/03/17 10:08 오전
        작성자: 고준영
        작성 내용: confirm test 용 생성자
    */
    public Center(String c_name, String c_ph, String c_people, Participation participation) {
        this.c_ph = c_ph;
        this.c_people = c_people;
        this.participation = participation;
    }

    public Center(String c_name, String c_address, String c_ph, Double c_latitude, Double c_longitude) {
        this.c_name = c_name;
        this.c_address = c_address;
        this.c_ph = c_ph;
        this.c_latitude = c_latitude;
        this.c_longitude = c_longitude;
    }

    public Center(String c_name, Participation participation){
        this.c_name = c_name;
        this.participation = participation;
    }

    /*
        날짜 : 2022/01/12 11:48 오전
        작성자 : 현승구
        작성내용 : Center 수정 로직
    */
    public void modifyCenter(Center center){
        this.c_name = center.getC_name();
        this.c_ph = center.getC_ph();
        this.c_address = center.getC_address();
    }

    /*
        작성 날짜: 2022/02/11 10:32 오전
        작성자: 고준영
        작성 내용: center modify dto, center save dto 용 생성자 (confirm list가
    */

    public Center(Long id, String c_sido, String c_sigungu, String c_name, String c_type, String c_status, String c_address, String c_zipcode, String c_ph, String c_faxNum, String c_people, String c_hpAddress, Double c_latitude, Double c_longitude, Participation participation, Visited visited, List<Call> callList, List<Schedule> scheduleList) {
        this.id = id;
        this.c_sido = c_sido;
        this.c_sigungu = c_sigungu;
        this.c_name = c_name;
        this.c_type = c_type;
        this.c_status = c_status;
        this.c_address = c_address;
        this.c_zipcode = c_zipcode;
        this.c_ph = c_ph;
        this.c_faxNum = c_faxNum;
        this.c_people = c_people;
        this.c_hpAddress = c_hpAddress;
        this.c_latitude = c_latitude;
        this.c_longitude = c_longitude;
        this.participation = participation;
        this.visited = visited;
        this.callList = callList;
        this.scheduleList = scheduleList;
    }

    @Override
    public String toString() {
        return "Center{" +
                "id=" + id +
                ", c_sido='" + c_sido + '\'' +
                ", c_sigungu='" + c_sigungu + '\'' +
                ", c_name='" + c_name + '\'' +
                ", c_type='" + c_type + '\'' +
                ", c_status='" + c_status + '\'' +
                ", c_address='" + c_address + '\'' +
                ", c_zipcode='" + c_zipcode + '\'' +
                ", c_ph='" + c_ph + '\'' +
                ", c_faxNum='" + c_faxNum + '\'' +
                ", c_people='" + c_people + '\'' +
                ", c_hpAddress='" + c_hpAddress + '\'' +
                ", c_latitude='" + c_latitude + '\'' +
                ", c_longitude='" + c_longitude + '\'' +
                ", participation=" + participation +
                ", visited=" + "list 들은 서로 참조 이슈로 인해 제외됨" +
                '}';
    }

    public Center(Center center, List<Schedule> scheduleList, List<Call> callList){
        this.id = center.getId();
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
        callList.stream()
            .forEach(call -> this.callList.add(call));
        scheduleList.stream()
                .forEach(schedule -> this.scheduleList.add(schedule));
    }
}
