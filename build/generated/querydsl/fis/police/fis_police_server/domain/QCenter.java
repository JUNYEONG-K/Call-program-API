package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCenter is a Querydsl query type for Center
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCenter extends EntityPathBase<Center> {

    private static final long serialVersionUID = 1419835668L;

    public static final QCenter center = new QCenter("center");

    public final StringPath c_address = createString("c_address");

    public final StringPath c_faxNum = createString("c_faxNum");

    public final StringPath c_hpAddress = createString("c_hpAddress");

    public final NumberPath<Double> c_latitude = createNumber("c_latitude", Double.class);

    public final NumberPath<Double> c_longitude = createNumber("c_longitude", Double.class);

    public final StringPath c_name = createString("c_name");

    public final StringPath c_people = createString("c_people");

    public final StringPath c_ph = createString("c_ph");

    public final StringPath c_sido = createString("c_sido");

    public final StringPath c_sigungu = createString("c_sigungu");

    public final StringPath c_status = createString("c_status");

    public final StringPath c_type = createString("c_type");

    public final StringPath c_zipcode = createString("c_zipcode");

    public final ListPath<Call, QCall> callList = this.<Call, QCall>createList("callList", Call.class, QCall.class, PathInits.DIRECT2);

    public final ListPath<Confirm, QConfirm> confirmList = this.<Confirm, QConfirm>createList("confirmList", Confirm.class, QConfirm.class, PathInits.DIRECT2);

    public final ListPath<Hope, QHope> hopeList = this.<Hope, QHope>createList("hopeList", Hope.class, QHope.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Participation> participation = createEnum("participation", fis.police.fis_police_server.domain.enumType.Participation.class);

    public final ListPath<Schedule, QSchedule> scheduleList = this.<Schedule, QSchedule>createList("scheduleList", Schedule.class, QSchedule.class, PathInits.DIRECT2);

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Visited> visited = createEnum("visited", fis.police.fis_police_server.domain.enumType.Visited.class);

    public QCenter(String variable) {
        super(Center.class, forVariable(variable));
    }

    public QCenter(Path<? extends Center> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCenter(PathMetadata metadata) {
        super(Center.class, metadata);
    }

}

