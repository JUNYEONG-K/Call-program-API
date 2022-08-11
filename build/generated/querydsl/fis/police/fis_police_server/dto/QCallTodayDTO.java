package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QCallTodayDTO is a Querydsl Projection type for CallTodayDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCallTodayDTO extends ConstructorExpression<CallTodayDTO> {

    private static final long serialVersionUID = -1044908330L;

    public QCallTodayDTO(com.querydsl.core.types.Expression<Long> user_id, com.querydsl.core.types.Expression<Long> call_num) {
        super(CallTodayDTO.class, new Class<?>[]{long.class, long.class}, user_id, call_num);
    }

}

