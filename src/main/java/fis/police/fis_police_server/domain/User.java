package fis.police.fis_police_server.domain;

import fis.police.fis_police_server.domain.enumType.UserAuthority;
import fis.police.fis_police_server.dto.UserSaveRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    //user_id         BIGINT              // 'primary_key',

    @NotBlank
    @Column(length = 100)
    private String u_nickname;              // "사용자 id"

    @NotBlank
    @Column(length = 10)
    private String u_name;                  // '사용자 이름',

    @NotBlank
    @Column(length = 100)
    private String u_pwd;                   // '사용자 비밀번호',


    @Column(length = 15)
    private String u_ph;                    // '사용자 전화번호',

    @Column
    private LocalDate u_sDate;                 // '입사일'

    @NotNull // enum 때문에 notblank 안됨
    @Column
    @Enumerated(EnumType.STRING)
    private UserAuthority u_auth;                  // '권한'

    @OneToMany(mappedBy = "user")
    private List<Call> callList = new ArrayList<Call>();

    @OneToMany(mappedBy = "user")
    private List<Messenger> messengerList = new ArrayList<Messenger>();


    /*
            날짜 : 2022/01/10 2:58 오후
            작성자 : 원보라
            작성내용 : 생성 메서드
    */

    public User(String u_nickname, String u_name, String u_pwd, String u_ph, LocalDate u_sDate, UserAuthority u_auth) {
        this.u_nickname = u_nickname;
        this.u_name = u_name;
        this.u_pwd = u_pwd;
        this.u_ph = u_ph;
        this.u_sDate = u_sDate;
        this.u_auth = u_auth;
    }
    public static User creatUser(UserSaveRequest request){
        User user = new User(request.getU_nickname(), request.getU_name(), request.getU_pwd(), request.getU_ph(), request.getU_sDate(), request.getU_auth());
        return user;
    }

/*
    날짜 : 2022/01/10 1:54 오후
    작성자 : 원보라
    작성내용 : 수정 메서드
*/
    public Boolean updateUser(String u_nickname, String u_name, String u_pwd, String u_ph, LocalDate u_sDate, UserAuthority u_auth) {
        this.u_nickname = u_nickname;
        this.u_name = u_name;
        this.u_pwd = u_pwd;
        this.u_ph = u_ph;
        this.u_sDate = u_sDate;
        this.u_auth = u_auth;
        return true;
    }

}