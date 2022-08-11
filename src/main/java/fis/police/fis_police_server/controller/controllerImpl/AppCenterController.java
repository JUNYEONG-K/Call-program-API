package fis.police.fis_police_server.controller.controllerImpl;

import fis.police.fis_police_server.dto.CenterSearchDTO;
import fis.police.fis_police_server.dto.CenterSearchResponseDTO;
import fis.police.fis_police_server.dto.Result;
import fis.police.fis_police_server.service.CenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppCenterController {

    private final CenterService centerService;

    // 여기서 만약 오류가 생긴다면, Result<어쩌구> 이거 지우고 그냥 Result 로 하셈
    @GetMapping("app/center/search")
    public Result<List<CenterSearchResponseDTO>> searchCenter(@RequestParam @Nullable String c_name, @RequestParam @Nullable String c_address, @RequestParam @Nullable String c_ph){
        log.info("[url : {}] [요청 : 시설 검색]", "/app/center/search");
        try {
            CenterSearchDTO centerSearchDTO = new CenterSearchDTO(c_name, c_address, c_ph);
            List<CenterSearchResponseDTO> results = centerService.findCenterList(c_name, c_address, c_ph);
            return new Result<>(results);
        } catch (NoResultException noResultException){
            return new Result<>(new ArrayList<CenterSearchResponseDTO>());
//            throw new NoResultException(noResultException.getMessage());
        } catch (NullPointerException nullPointerException){
//            logging(request, "/center/search", "null pointer exception 발생", false);
//            throw new NullPointerException("null pointer exception 발생");
            return null;
        }
    }
}
