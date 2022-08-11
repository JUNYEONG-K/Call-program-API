package fis.police.fis_police_server.dto;

import lombok.Data;

import java.util.List;

/*
    작성 날짜: 2022/02/14 11:40 오전
    작성자: 고준영
    작성 내용: 이게 뭐노? 아마 ... 확인서 결재 후 응답 같은데 모르겄네
*/
@Data
public class ConfirmUpdateCompleteResponse {

    private List<Long> confirm_id;  // 한 시설에 2명 이상이 방문했을 경우, 확인서는 요원별 제출이기 때문에, 확인서 id 리스트가 오고가야할듯
    private String confirmOrNot;
}
