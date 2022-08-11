package fis.police.fis_police_server.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelUploadController {
    Object readExcel(@ModelAttribute MultipartFile excelFile) throws IOException, NoSuchMethodException;
}
