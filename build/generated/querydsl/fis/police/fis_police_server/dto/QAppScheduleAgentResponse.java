package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QAppScheduleAgentResponse is a Querydsl Projection type for AppScheduleAgentResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAppScheduleAgentResponse extends ConstructorExpression<AppScheduleAgentResponse> {

    private static final long serialVersionUID = 2144611816L;

    public QAppScheduleAgentResponse(com.querydsl.core.types.Expression<Long> schedule_id, com.querydsl.core.types.Expression<java.time.LocalDate> visit_date, com.querydsl.core.types.Expression<java.time.LocalTime> visit_time, com.querydsl.core.types.Expression<Integer> estimate_num, com.querydsl.core.types.Expression<String> center_etc, com.querydsl.core.types.Expression<String> agent_etc, com.querydsl.core.types.Expression<String> total_etc, com.querydsl.core.types.Expression<fis.police.fis_police_server.domain.enumType.Accept> accept, com.querydsl.core.types.Expression<String> late_comment, com.querydsl.core.types.Expression<Long> center_id, com.querydsl.core.types.Expression<String> c_name, com.querydsl.core.types.Expression<String> c_address, com.querydsl.core.types.Expression<String> c_zipcode, com.querydsl.core.types.Expression<String> c_ph, com.querydsl.core.types.Expression<String> c_faxNum) {
        super(AppScheduleAgentResponse.class, new Class<?>[]{long.class, java.time.LocalDate.class, java.time.LocalTime.class, int.class, String.class, String.class, String.class, fis.police.fis_police_server.domain.enumType.Accept.class, String.class, long.class, String.class, String.class, String.class, String.class, String.class}, schedule_id, visit_date, visit_time, estimate_num, center_etc, agent_etc, total_etc, accept, late_comment, center_id, c_name, c_address, c_zipcode, c_ph, c_faxNum);
    }

}

