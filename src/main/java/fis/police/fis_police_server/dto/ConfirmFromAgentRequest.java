package fis.police.fis_police_server.dto;

import fis.police.fis_police_server.domain.enumType.Complete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    작성 날짜: 2022/02/14 11:40 오전
    작성자: 고준영
    작성 내용: 확인서 작성/제출 양식
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmFromAgentRequest {

    private String new_child;
    private String old_child;
    private String senile;
    private String disabled;
    private String etc;
}
