package fis.police.fis_police_server.service;

import fis.police.fis_police_server.dto.AppLoginRequest;
import fis.police.fis_police_server.dto.LoginRequest;
import fis.police.fis_police_server.dto.LoginResponse;

public interface AppLoginService {
    Long getPrimaryKey(AppLoginRequest request);
    LoginResponse login(AppLoginRequest request);
}
