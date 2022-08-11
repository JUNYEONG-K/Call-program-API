package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCall is a Querydsl query type for Call
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCall extends EntityPathBase<Call> {

    private static final long serialVersionUID = -722548003L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCall call = new QCall("call");

    public final StringPath agent_etc = createString("agent_etc");

    public final StringPath c_manager = createString("c_manager");

    public final QCenter center;

    public final StringPath center_etc = createString("center_etc");

    public final StringPath date = createString("date");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<fis.police.fis_police_server.domain.enumType.InOut> in_out = createEnum("in_out", fis.police.fis_police_server.domain.enumType.InOut.class);

    public final StringPath m_email = createString("m_email");

    public final StringPath m_ph = createString("m_ph");

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Participation> participation = createEnum("participation", fis.police.fis_police_server.domain.enumType.Participation.class);

    public final StringPath time = createString("time");

    public final QUser user;

    public QCall(String variable) {
        this(Call.class, forVariable(variable), INITS);
    }

    public QCall(Path<? extends Call> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCall(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCall(PathMetadata metadata, PathInits inits) {
        this(Call.class, metadata, inits);
    }

    public QCall(Class<? extends Call> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.center = inits.isInitialized("center") ? new QCenter(forProperty("center")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

