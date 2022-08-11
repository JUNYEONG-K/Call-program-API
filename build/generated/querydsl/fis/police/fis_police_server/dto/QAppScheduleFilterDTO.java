package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QAppScheduleFilterDTO is a Querydsl Projection type for AppScheduleFilterDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAppScheduleFilterDTO extends ConstructorExpression<AppScheduleFilterDTO> {

    private static final long serialVersionUID = -1919728695L;

    public QAppScheduleFilterDTO(com.querydsl.core.types.Expression<java.time.LocalDate> visit_date, com.querydsl.core.types.Expression<java.time.LocalTime> visit_time, com.querydsl.core.types.Expression<Long> center_id, com.querydsl.core.types.Expression<Long> count) {
        super(AppScheduleFilterDTO.class, new Class<?>[]{java.time.LocalDate.class, java.time.LocalTime.class, long.class, long.class}, visit_date, visit_time, center_id, count);
    }

}

