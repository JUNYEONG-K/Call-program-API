package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QCallAvgDTO is a Querydsl Projection type for CallAvgDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCallAvgDTO extends ConstructorExpression<CallAvgDTO> {

    private static final long serialVersionUID = -404438107L;

    public QCallAvgDTO(com.querydsl.core.types.Expression<Long> user_id, com.querydsl.core.types.Expression<Double> call_avg_num) {
        super(CallAvgDTO.class, new Class<?>[]{long.class, double.class}, user_id, call_avg_num);
    }

}

