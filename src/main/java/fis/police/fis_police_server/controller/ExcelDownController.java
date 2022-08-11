package fis.police.fis_police_server.controller;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/*
    날짜 : 2022/03/17 11:25 오전
    작성자 : 원보라
    작성내용 : 스케쥴 엑셀로 다운로드
*/
public interface ExcelDownController {
    void excelDownload(LocalDate date, HttpServletResponse response) throws IOException;
}
