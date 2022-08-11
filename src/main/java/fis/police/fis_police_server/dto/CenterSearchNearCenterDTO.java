package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.Visited;
import lombok.Data;

@Data
public class
CenterSearchNearCenterDTO {
    private Long center_id;
    private String c_name;
    private String c_people;
    private String c_address;
    private String c_ph;
    private Participation participation;
    private Visited visited;
    private double distance;
    private double c_latitude;    // '위도',
    private double c_longitude;   // '경도',

    public CenterSearchNearCenterDTO(Center center, double distance) {
        this.center_id = center.getId();
        this.c_name = center.getC_name();
        this.c_people = center.getC_people();
        this.c_address = center.getC_address();
        this.c_ph = center.getC_ph();
        this.participation = center.getParticipation();
        this.visited = center.getVisited();
        this.c_latitude = center.getC_latitude();
        this.c_longitude = center.getC_longitude();
        this.distance = distance;
    }
}
