package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.UserAuthority;
import lombok.Data;

@Data
public class AppLoginRequest {
    private String u_nickname;
    private String u_pwd;
    private UserAuthority role;
}
