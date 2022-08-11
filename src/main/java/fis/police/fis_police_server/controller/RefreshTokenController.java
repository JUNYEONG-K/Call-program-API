package fis.police.fis_police_server.controller;

import fis.police.fis_police_server.dto.TokenSet;

import javax.servlet.http.HttpServletRequest;

public interface RefreshTokenController {

    TokenSet createAccessToken(HttpServletRequest request);
}