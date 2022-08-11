package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOfficials is a Querydsl query type for Officials
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOfficials extends EntityPathBase<Officials> {

    private static final long serialVersionUID = -107353655L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOfficials officials = new QOfficials("officials");

    public final QCenter center;

    public final ListPath<Hope, QHope> hopeList = this.<Hope, QHope>createList("hopeList", Hope.class, QHope.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath o_email = createString("o_email");

    public final StringPath o_name = createString("o_name");

    public final StringPath o_nickname = createString("o_nickname");

    public final StringPath o_ph = createString("o_ph");

    public final StringPath o_pwd = createString("o_pwd");

    public final EnumPath<fis.police.fis_police_server.domain.enumType.UserAuthority> u_auth = createEnum("u_auth", fis.police.fis_police_server.domain.enumType.UserAuthority.class);

    public QOfficials(String variable) {
        this(Officials.class, forVariable(variable), INITS);
    }

    public QOfficials(Path<? extends Officials> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOfficials(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOfficials(PathMetadata metadata, PathInits inits) {
        this(Officials.class, metadata, inits);
    }

    public QOfficials(Class<? extends Officials> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.center = inits.isInitialized("center") ? new QCenter(forProperty("center")) : null;
    }

}

