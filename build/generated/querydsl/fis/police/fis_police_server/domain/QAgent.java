package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgent is a Querydsl query type for Agent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgent extends EntityPathBase<Agent> {

    private static final long serialVersionUID = -925826458L;

    public static final QAgent agent = new QAgent("agent");

    public final StringPath a_address = createString("a_address");

    public final StringPath a_code = createString("a_code");

    public final StringPath a_cur_lat = createString("a_cur_lat");

    public final StringPath a_cur_long = createString("a_cur_long");

    public final StringPath a_equipment = createString("a_equipment");

    public final EnumPath<fis.police.fis_police_server.domain.enumType.HasCar> a_hasCar = createEnum("a_hasCar", fis.police.fis_police_server.domain.enumType.HasCar.class);

    public final NumberPath<Double> a_latitude = createNumber("a_latitude", Double.class);

    public final NumberPath<Double> a_longitude = createNumber("a_longitude", Double.class);

    public final StringPath a_name = createString("a_name");

    public final StringPath a_nickname = createString("a_nickname");

    public final StringPath a_ph = createString("a_ph");

    public final StringPath a_picture = createString("a_picture");

    public final StringPath a_pwd = createString("a_pwd");

    public final DatePath<java.time.LocalDate> a_receiveDate = createDate("a_receiveDate", java.time.LocalDate.class);

    public final EnumPath<fis.police.fis_police_server.domain.enumType.AgentStatus> a_status = createEnum("a_status", fis.police.fis_police_server.domain.enumType.AgentStatus.class);

    public final ListPath<Confirm, QConfirm> confirmList = this.<Confirm, QConfirm>createList("confirmList", Confirm.class, QConfirm.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Schedule, QSchedule> scheduleList = this.<Schedule, QSchedule>createList("scheduleList", Schedule.class, QSchedule.class, PathInits.DIRECT2);

    public final EnumPath<fis.police.fis_police_server.domain.enumType.UserAuthority> u_auth = createEnum("u_auth", fis.police.fis_police_server.domain.enumType.UserAuthority.class);

    public QAgent(String variable) {
        super(Agent.class, forVariable(variable));
    }

    public QAgent(Path<? extends Agent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgent(PathMetadata metadata) {
        super(Agent.class, metadata);
    }

}

