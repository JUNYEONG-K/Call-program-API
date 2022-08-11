package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingOfficialDTO {
    private Long official_id;
    private Long center_id;
    private String center_name;
    private String center_address;
    private String o_name;
    private String o_ph;
    private String o_email;
    private String o_nickname;
    private String o_pwd;
}
