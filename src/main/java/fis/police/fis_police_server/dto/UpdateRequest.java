package fis.police.fis_police_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
    작성 날짜: 2022/02/14 11:43 오전
    작성자: 고준영
    작성 내용: 담당자가 결재한 확인서 목록 (현장요원 별로 확인서를 제출해서 결재받기 때문에, confirm_id 여러개가 오고가야함)
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {
    private List<Long> confirm_id;
}
