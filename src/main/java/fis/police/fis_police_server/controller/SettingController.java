package fis.police.fis_police_server.controller;

import javax.servlet.http.HttpServletRequest;

public interface SettingController {
    Object basicOfficialInfo(HttpServletRequest request);
    Object basicAgentInfo(HttpServletRequest request);
}
