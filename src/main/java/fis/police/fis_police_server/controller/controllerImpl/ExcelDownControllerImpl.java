package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.ExcelDownController;
import fis.police.fis_police_server.dto.ScheduleByDateResponse;
import fis.police.fis_police_server.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
/*
    날짜 : 2022/03/17 3:30 오후
    작성자 : 원보라
    작성내용 : 해당 날짜의 스케줄 엑셀로 다운로드
*/
@RestController
@RequiredArgsConstructor
@Slf4j
public class ExcelDownControllerImpl implements ExcelDownController {

    private final ScheduleService scheduleService;

    @Override
    @GetMapping("/excel/download")
    public void excelDownload(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, HttpServletResponse response) throws IOException {
        //선택된 날짜에 대한 스케줄
        List<ScheduleByDateResponse> list = scheduleService.selectDate(date);

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(date+" 스케줄");

        /*제목 (ex 2022-01-01 스케줄)*/
        //가운데 정렬
        XSSFCellStyle title = (XSSFCellStyle) wb.createCellStyle();
        title.setAlignment(HorizontalAlignment.CENTER);
        title.setVerticalAlignment(VerticalAlignment.CENTER);
        //글씨 크기
        Font titleFont = wb.createFont();
        titleFont.setFontHeight((short)500);
        titleFont.setBold(true);
        title.setFont(titleFont);
        //테두리
        title.setBorderTop(BorderStyle.THIN);
        title.setBorderBottom(BorderStyle.THIN);
        //배경 색상
        title.setFillForegroundColor(new XSSFColor(new byte[] {(byte) 217,(byte) 245,(byte) 221}, null));
        title.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        /*헤더*/
        //가운데 정렬
        XSSFCellStyle header = (XSSFCellStyle) wb.createCellStyle();
        header.setAlignment(HorizontalAlignment.CENTER);
        header.setVerticalAlignment(VerticalAlignment.CENTER);
        //글씨 크기
        Font headerFont = wb.createFont();
        headerFont.setFontHeight((short)300);
        header.setFont(headerFont);
        //테두리
        header.setBorderTop(BorderStyle.THIN);
        header.setBorderBottom(BorderStyle.THIN);
        header.setBorderLeft(BorderStyle.THIN);
        header.setBorderRight(BorderStyle.THIN);
        //배경 색상
        header.setFillForegroundColor(new XSSFColor(new byte[] {(byte) 209,(byte) 233,(byte) 255}, null));
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);



        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        int index=0;
        // Header
        row = sheet.createRow(rowNum++);
        for(int i=0; i<10; i++) {
            cell = row.createCell(index++);
            cell.setCellStyle(title);
            cell.setCellValue(date+"스케줄");
        }
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:J1")); //셀 병합


        index=0;
        row = sheet.createRow(rowNum++);
        row.setHeight((short) 550);
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("현장요원");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("시설이름");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("시설주소");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("시설전화번호");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("방문예정시간");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("예상인원");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("수락/거절");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("통화이력");
        cell = row.createCell(index++);
        cell.setCellStyle(header);
        cell.setCellValue("특이사항");
        cell = row.createCell(index);
        cell.setCellStyle(header);
        cell.setCellValue("변경사항");


        // 데이터 부분 생성
        for(ScheduleByDateResponse res : list) {
            index=0;
            row = sheet.createRow(rowNum++);
            cell = row.createCell(index++);
            cell.setCellValue(res.getA_name());
            cell = row.createCell(index++);
            cell.setCellValue(res.getC_name());
            cell = row.createCell(index++);
            cell.setCellValue(res.getC_address());
            cell = row.createCell(index++);
            cell.setCellValue(res.getC_ph());
            cell = row.createCell(index++);
            cell.setCellValue(String.valueOf(res.getVisit_time()));
            cell = row.createCell(index++);
            cell.setCellValue(res.getEstimate_num());
            cell = row.createCell(index++);
            cell.setCellValue(String.valueOf(res.getAccept()));
            cell = row.createCell(index++);
            cell.setCellValue(res.getCall_check());
            cell = row.createCell(index++);
            cell.setCellValue(res.getTotal_etc());
            cell = row.createCell(index++);
            cell.setCellValue(res.getModified_info());
        }

        //셀 너비 : 제일 긴 셀 너비에 맞춤
        for (int i=0; i<=9; i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+(short)1024);
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        //response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }
}
