package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.domain.enumType.Participation;
import fis.police.fis_police_server.domain.enumType.Visited;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CenterSearchResponseDTO {
    private Long center_id;
    private String c_name;
    private String c_address;
    private String c_ph;
    private Participation participation;
    private Visited visited;

    public CenterSearchResponseDTO(Center center){
        this.center_id = center.getId();
        this.c_name = center.getC_name();
        this.c_address = center.getC_address();
        this.c_ph = center.getC_ph();
        this.participation = center.getParticipation();
        this.visited = center.getVisited();
    }

}
