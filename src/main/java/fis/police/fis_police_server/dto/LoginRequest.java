package fis.police.fis_police_server.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String u_nickname;
    private String u_pwd;
}
