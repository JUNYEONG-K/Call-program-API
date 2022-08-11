package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = -1183020138L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Accept> accept = createEnum("accept", fis.police.fis_police_server.domain.enumType.Accept.class);

    public final QAgent agent;

    public final StringPath agent_etc = createString("agent_etc");

    public final StringPath call_check = createString("call_check");

    public final StringPath call_check_info = createString("call_check_info");

    public final QCenter center;

    public final StringPath center_etc = createString("center_etc");

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Complete> complete = createEnum("complete", fis.police.fis_police_server.domain.enumType.Complete.class);

    public final NumberPath<Integer> estimate_num = createNumber("estimate_num", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath late_comment = createString("late_comment");

    public final StringPath modified_info = createString("modified_info");

    public final DatePath<java.time.LocalDate> receipt_date = createDate("receipt_date", java.time.LocalDate.class);

    public final StringPath total_etc = createString("total_etc");

    public final QUser user;

    public final BooleanPath valid = createBoolean("valid");

    public final DatePath<java.time.LocalDate> visit_date = createDate("visit_date", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> visit_time = createTime("visit_time", java.time.LocalTime.class);

    public QSchedule(String variable) {
        this(Schedule.class, forVariable(variable), INITS);
    }

    public QSchedule(Path<? extends Schedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchedule(PathMetadata metadata, PathInits inits) {
        this(Schedule.class, metadata, inits);
    }

    public QSchedule(Class<? extends Schedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agent = inits.isInitialized("agent") ? new QAgent(forProperty("agent")) : null;
        this.center = inits.isInitialized("center") ? new QCenter(forProperty("center")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

