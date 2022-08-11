package fis.police.fis_police_server.service.excelService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@Component
public class ExcelService {
    /*
        엑셀 파일 xlsx 와 xls 가 호환됩니다. Object배열을 리턴합니다
        엑셀 파일 생성시에 매핑하는 첫번째 열들을 @ExcelColumn을 활용해서 작성하면 됩니다.
        Class, Field, Method, ----
     */
    public List<Object> excelToJson(MultipartFile file, Class<?> clazz) throws IOException, NoSuchMethodException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        // 엑셀파일 분석하기
        log.info("엑셀 파일 분석중");
        if(!extension.equals("xlsx") && !extension.equals("xls")){
            log.error("업로드 파일 형식 오류 엑셀파일이 아님 현재파일 : {}", extension);
            throw new IOException("엑셀파일이 아닙니다");
        }
        Workbook workbook = null;
        if(extension.equals("xlsx")){
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if(extension.equals("xls")){
            workbook = new HSSFWorkbook(file.getInputStream());
        }
        List<List<List>> excelFile = new ArrayList<>();
        workbook.sheetIterator().forEachRemaining(worksheet -> {
            List<List> data = new ArrayList<>();
            if(worksheet.getRow(worksheet.getFirstRowNum()) == null) return;
            worksheet.rowIterator().forEachRemaining(row -> {
                List<String> rowData = new ArrayList<>();
                //
                for(int cn = 0; cn < row.getLastCellNum(); cn++) {
                    Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    try {
                        rowData.add(cell.getStringCellValue());
                    } catch (IllegalStateException exception) {
                        try {
                            rowData.add(String.valueOf((long) cell.getNumericCellValue()));
                        } catch (IllegalStateException exception1) {
                            try {
                                rowData.add(String.valueOf(cell.getDateCellValue()));
                            } catch (Exception rowException){
                                rowData.add(null);
                            }
                        }
                    }
                    //
                }
                data.add(rowData);
            });
            excelFile.add(data);
        });

        // 엑셀파일과 DTO 매핑하기
        Constructor<?> constructor = clazz.getDeclaredConstructor(null);
        // clazz 분석하기
        Field[] fields = clazz.getDeclaredFields();

        /*
            시나리오
            엑셀 데이터들 다 가져와서 배열로 만들어 놓은 상태
            key 값을 통해서 다시 객체 생성후 할당.
         */
        log.info("엑셀 파일 매핑중");
        List<Object> Json = new ArrayList<>();
        excelFile.forEach(lines -> {
            for(int i = 1; i < lines.size(); i++){
                List keyLine = lines.get(0);
                try {
                    Object dataLine = constructor.newInstance();
                    List targetLine = lines.get(i);
                    Arrays.stream(fields).forEach(field -> {
                        field.setAccessible(true);
                        ExcelColumn excelColumn = field.getDeclaredAnnotation(ExcelColumn.class);
                        String column;
                        if(excelColumn == null){
                            column = field.getName();
                        } else {
                            column = excelColumn.name();
                        }
                        try {
                            int index = keyLine.indexOf(column);
                            try {
                                field.set(dataLine, targetLine.get(index));
                            } catch (Exception exception){
                                field.set(dataLine, null);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                    Json.add(dataLine);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        return Json;
    }

    /*
        엑셀파일 내보내기 형식입니다
        만들고 싶은 엑셀파일 형식을 @ExcelColumn 과 @CellLength 를 사용해서 작성합니다.
        빌더 패턴
        만들고 싶은 엑셀 파일의 행들에 대한 데이터를 보내주면 생성합니다.
     */

    public Workbook dbToExcel(List<?> dataList, Class<?> clazz){
        // datalist class 와 clazz 타입 같은지 확인이 필요

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = null;
        try{
            sheet = workbook.createSheet();
            Font font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL); //폰트스타일
            font.setFontHeightInPoints((short)14); //폰트크기

            /*
                처음 행 생성하기 clazz 분석, ExcelColum을 바탕으로 분석합니다 name 을 통해 해당 위치를 찾습니다
                List에 add 하는 순서가 중요하기 때문에 순서 보장을 위해 순차처리 방식을 사용합니다 stream, foreach 사용하지 않습니다
             */
            List<String> columns = new ArrayList<>();
            Field[] fields = clazz.getDeclaredFields();

            for(int i = 0; i < fields.length; i++){
                Field field = fields[i];
                field.setAccessible(true);
                ExcelColumn declaredAnnotation = field.getDeclaredAnnotation(ExcelColumn.class);
                Optional opt = Optional.ofNullable(declaredAnnotation.name());
                String columnName = (String) opt.orElse(field.getName());
                columns.add(columnName);
            }

            int rowNum = 0;
            Row row = sheet.createRow(rowNum++); //행 객체 추가
            /*
                첫번째 행을 생성합니다 첫번째 행은 데이터의 목록을 나타냅니다.
             */
            for(int col = 0; col < columns.size(); col++){
                Cell cell = row.createCell(col);
                cell.setCellValue(columns.get(col));
            }

            /*
                데이터를 엑셀과 매핑작업 진행
             */
            while(rowNum <= dataList.size()){
                Class data = dataList.get(rowNum - 1).getClass();
                Object Json = dataList.get(rowNum -1);

                Row dataRow = sheet.createRow(rowNum++);

                Arrays.stream(data.getDeclaredFields()).forEach(field -> {
                    field.setAccessible(true);
                    ExcelColumn declaredAnnotation = field.getDeclaredAnnotation(ExcelColumn.class);
                    Optional opt = Optional.ofNullable(declaredAnnotation.name());
                    String columnName = (String) opt.orElse(field.getName());

                    try {
                        /*
                            셀의 데이터 타입을 분석하여 매핑을 해야합니다.
                            아파치 poi의 경우에
                         */
                        field.setAccessible(true);
                        Optional<Object> object = Optional.ofNullable(field.get(Json));
                        if(!object.isPresent()) return;
                        Class fieldType = field.getType();
                        int index = columns.indexOf(columnName);
                        Cell cell = dataRow.createCell(index);
                        if(fieldType.equals(String.class)){
                            cell.setCellValue((String) object.get());
                        } else if(fieldType.equals(Long.class)){
                            cell.setCellValue((Long) object.get());
                        } else if(fieldType.equals(Double.class)){
                            cell.setCellValue((Double) object.get());
                        } else if(fieldType.equals(Boolean.class)){
                            cell.setCellValue((Boolean) object.get());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            }
            return workbook;

        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

}
