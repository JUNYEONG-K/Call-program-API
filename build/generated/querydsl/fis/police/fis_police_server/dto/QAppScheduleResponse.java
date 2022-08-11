package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QAppScheduleResponse is a Querydsl Projection type for AppScheduleResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAppScheduleResponse extends ConstructorExpression<AppScheduleResponse> {

    private static final long serialVersionUID = 1011655551L;

    public QAppScheduleResponse(com.querydsl.core.types.Expression<Long> schedule_id, com.querydsl.core.types.Expression<java.time.LocalDate> visit_date, com.querydsl.core.types.Expression<java.time.LocalTime> visit_time, com.querydsl.core.types.Expression<Integer> estimate_num, com.querydsl.core.types.Expression<String> center_etc, com.querydsl.core.types.Expression<String> agent_etc, com.querydsl.core.types.Expression<String> total_etc, com.querydsl.core.types.Expression<Long> center_id, com.querydsl.core.types.Expression<String> c_name, com.querydsl.core.types.Expression<String> c_address, com.querydsl.core.types.Expression<String> c_zipcode, com.querydsl.core.types.Expression<String> c_ph, com.querydsl.core.types.Expression<String> c_faxNum, com.querydsl.core.types.Expression<fis.police.fis_police_server.domain.enumType.Complete> complete) {
        super(AppScheduleResponse.class, new Class<?>[]{long.class, java.time.LocalDate.class, java.time.LocalTime.class, int.class, String.class, String.class, String.class, long.class, String.class, String.class, String.class, String.class, String.class, fis.police.fis_police_server.domain.enumType.Complete.class}, schedule_id, visit_date, visit_time, estimate_num, center_etc, agent_etc, total_etc, center_id, c_name, c_address, c_zipcode, c_ph, c_faxNum, complete);
    }

}

