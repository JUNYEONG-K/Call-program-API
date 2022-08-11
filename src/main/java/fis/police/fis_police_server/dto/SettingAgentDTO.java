package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingAgentDTO {
    private String a_name;
    private String a_nickname;
    private String a_pwd;
    private String a_ph;
}
