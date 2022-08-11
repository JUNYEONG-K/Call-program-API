package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessenger is a Querydsl query type for Messenger
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessenger extends EntityPathBase<Messenger> {

    private static final long serialVersionUID = 705683956L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessenger messenger = new QMessenger("messenger");

    public final StringPath context = createString("context");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> sendTime = createDateTime("sendTime", java.time.LocalDateTime.class);

    public final QUser user;

    public QMessenger(String variable) {
        this(Messenger.class, forVariable(variable), INITS);
    }

    public QMessenger(Path<? extends Messenger> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessenger(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessenger(PathMetadata metadata, PathInits inits) {
        this(Messenger.class, metadata, inits);
    }

    public QMessenger(Class<? extends Messenger> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

