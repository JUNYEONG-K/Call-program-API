package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHope is a Querydsl query type for Hope
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHope extends EntityPathBase<Hope> {

    private static final long serialVersionUID = -722385477L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHope hope = new QHope("hope");

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Accept> accept = createEnum("accept", fis.police.fis_police_server.domain.enumType.Accept.class);

    public final QCenter center;

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Complete> complete = createEnum("complete", fis.police.fis_police_server.domain.enumType.Complete.class);

    public final StringPath h_date = createString("h_date");

    public final StringPath h_mail = createString("h_mail");

    public final StringPath h_ph = createString("h_ph");

    public final StringPath h_time = createString("h_time");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOfficials officials;

    public QHope(String variable) {
        this(Hope.class, forVariable(variable), INITS);
    }

    public QHope(Path<? extends Hope> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHope(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHope(PathMetadata metadata, PathInits inits) {
        this(Hope.class, metadata, inits);
    }

    public QHope(Class<? extends Hope> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.center = inits.isInitialized("center") ? new QCenter(forProperty("center")) : null;
        this.officials = inits.isInitialized("officials") ? new QOfficials(forProperty("officials"), inits.get("officials")) : null;
    }

}

