package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QAgentLocation is a Querydsl Projection type for AgentLocation
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAgentLocation extends ConstructorExpression<AgentLocation> {

    private static final long serialVersionUID = 965976480L;

    public QAgentLocation(com.querydsl.core.types.Expression<Long> agent_id, com.querydsl.core.types.Expression<String> a_cur_lat, com.querydsl.core.types.Expression<String> a_cur_long) {
        super(AgentLocation.class, new Class<?>[]{long.class, String.class, String.class}, agent_id, a_cur_lat, a_cur_long);
    }

}

