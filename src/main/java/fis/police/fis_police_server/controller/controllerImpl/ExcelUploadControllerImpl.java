package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.controller.ExcelUploadController;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.ExcelCenterDTO;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.service.CenterService;
import fis.police.fis_police_server.service.excelService.ExcelService;
import fis.police.fis_police_server.service.exceptions.DuplicateSaveException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExcelUploadControllerImpl implements ExcelUploadController {

    private final CenterService centerService;
    private final ExcelService excelService;

    @Override
    @PostMapping("excel/read")
    public Object readExcel(@ModelAttribute MultipartFile excelFile) throws IOException, NoSuchMethodException {
        List<Object> object = excelService.excelToJson(excelFile, ExcelCenterDTO.class);
        object.stream().forEach(center -> {
            try {
                ExcelCenterDTO excelCenterDTO = (ExcelCenterDTO) center;
                System.out.println("excelCenterDTO = " + excelCenterDTO);
                centerService.saveCenter(ExcelCenterDTO.createCenter(excelCenterDTO));
            } catch (ParseException | DuplicateSaveException e) {
                e.printStackTrace();
            }
        });
        return object;
    }

    @PostMapping("app/excel/read/test")
    public ExcelCenterDTO r(@RequestBody MultipartFile excelFile) throws IOException, DuplicateSaveException, ParseException {
        List<ExcelCenterDTO> centerList = new ArrayList<>();

        String extension = FilenameUtils.getExtension(excelFile.getOriginalFilename());

        assert extension != null;
        if(!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        } else {
            workbook = new HSSFWorkbook(excelFile.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            ExcelCenterDTO data = new ExcelCenterDTO();

            data.setC_sido(row.getCell(0).getStringCellValue());
            data.setC_sigungu(row.getCell(1).getStringCellValue());
            data.setC_name(row.getCell(2).getStringCellValue());
            data.setC_type(row.getCell(3).getStringCellValue());
            data.setC_status(row.getCell(4).getStringCellValue());
            data.setC_address(row.getCell(5).getStringCellValue());
            data.setC_zipcode(Integer.toString((int) row.getCell(7).getNumericCellValue()));
            data.setC_ph(row.getCell(8).getStringCellValue());
            data.setC_faxNum(row.getCell(9).getStringCellValue());
            data.setC_people(Integer.toString((int) row.getCell(10).getNumericCellValue()));
            data.setC_hpAddress(row.getCell(11).getStringCellValue());

            centerList.add(data);
        }
        for (ExcelCenterDTO dto : centerList) {

            Center center = new Center(dto.getC_sido(), dto.getC_sigungu(),
                    dto.getC_name(), dto.getC_type(),
                    dto.getC_status(), dto.getC_address(),
                    dto.getC_zipcode(), dto.getC_ph(),
                    dto.getC_faxNum(), dto.getC_people(), dto.getC_hpAddress());
            System.out.println("dto.getC_name() = " + dto.getC_name());
            System.out.println("center.getC_name() = " + center.getC_name());
            System.out.println("center = " + center);
//            centerRepository.save(center);
            centerService.saveCenter(center);
        }
        return centerList.get(0);
    }
}
