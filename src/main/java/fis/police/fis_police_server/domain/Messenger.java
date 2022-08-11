package fis.police.fis_police_server.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
public class Messenger {

    @Id @GeneratedValue
    @Column(name = "messenger_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  @JoinColumn(name = "user_id")
    private User user;

    @Column @Lob
    private String context;

    @Column
    private LocalDateTime sendTime;

    public Messenger(String s, User user) {
        this.user = user;
        this.context = s;
        this.sendTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Messenger{" +
                "id=" + id +
                ", user=" + user.getU_name() +
                ", context='" + context + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
