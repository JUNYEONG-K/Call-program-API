package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CenterSearchDTO {
    private String c_name;
    private String c_address;
    private String c_ph;
}
