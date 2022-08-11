package fis.police.fis_police_server.domain;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class InputTime {
    private Long userid;
    private LocalDateTime inputDate;
}
