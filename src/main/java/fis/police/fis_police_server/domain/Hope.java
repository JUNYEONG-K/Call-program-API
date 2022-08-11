package fis.police.fis_police_server.domain;

import fis.police.fis_police_server.domain.enumType.Accept;
import fis.police.fis_police_server.domain.enumType.Complete;
import fis.police.fis_police_server.dto.HopeSaveRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
public class Hope {

    @Id @GeneratedValue
    @Column(name = "hope_id")
    private Long id;

    // 시설이 적어내는 참여여부
    @Enumerated(EnumType.STRING)
    private Accept accept;

    private String h_date;
    private String h_time;

    // 필요?에 대한 의문
    private String h_mail;
    private String h_ph;

    private String now_date;

    // 일정 잡기 완료/미완료
    @Enumerated(EnumType.STRING)
    private Complete complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "official_id")
    private Officials officials;

    /*
        작성 날짜: 2022/02/14 \
        작성자: 고준영
        작성 내용: 신청서와 시설 연결
    */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    /*
        작성 날짜: 2022/02/14 11:37 오전
        작성자: 고준영
        작성 내용: 신청서, 시설 묶기
    */
    public static Hope createHope(HopeSaveRequest request, Officials officials, Center center) {
        Hope hope = new Hope();
        hope.accept = request.getAccept();
        hope.h_date = request.getH_date();
        hope.h_time = request.getH_time();
        // Officials 회원가입할 때 받은 이메일, 전화번호를 작성해야하는지,, 궁금
        hope.h_mail = request.getH_mail();
        hope.h_ph = request.getH_ph();
        hope.now_date = request.getNow_date();
        hope.officials = officials;
        hope.center = center;
        return hope;
    }

}
