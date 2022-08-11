package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QAppScheduleCenterResponse is a Querydsl Projection type for AppScheduleCenterResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAppScheduleCenterResponse extends ConstructorExpression<AppScheduleCenterResponse> {

    private static final long serialVersionUID = -954087308L;

    public QAppScheduleCenterResponse(com.querydsl.core.types.Expression<Long> schedule_id, com.querydsl.core.types.Expression<java.time.LocalDate> visit_date, com.querydsl.core.types.Expression<java.time.LocalTime> visit_time, com.querydsl.core.types.Expression<Integer> estimate_num, com.querydsl.core.types.Expression<String> center_etc, com.querydsl.core.types.Expression<String> agent_etc, com.querydsl.core.types.Expression<String> total_etc, com.querydsl.core.types.Expression<fis.police.fis_police_server.domain.enumType.Accept> accept, com.querydsl.core.types.Expression<String> late_comment, com.querydsl.core.types.Expression<Long> center_id, com.querydsl.core.types.Expression<String> c_name, com.querydsl.core.types.Expression<Double> c_latitude, com.querydsl.core.types.Expression<Double> c_longitude, com.querydsl.core.types.Expression<Long> agent_id, com.querydsl.core.types.Expression<String> a_name, com.querydsl.core.types.Expression<String> a_ph, com.querydsl.core.types.Expression<String> a_code) {
        super(AppScheduleCenterResponse.class, new Class<?>[]{long.class, java.time.LocalDate.class, java.time.LocalTime.class, int.class, String.class, String.class, String.class, fis.police.fis_police_server.domain.enumType.Accept.class, String.class, long.class, String.class, double.class, double.class, long.class, String.class, String.class, String.class}, schedule_id, visit_date, visit_time, estimate_num, center_etc, agent_etc, total_etc, accept, late_comment, center_id, c_name, c_latitude, c_longitude, agent_id, a_name, a_ph, a_code);
    }

}

