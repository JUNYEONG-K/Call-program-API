package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCallView is a Querydsl query type for CallView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCallView extends EntityPathBase<CallView> {

    private static final long serialVersionUID = -657669342L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCallView callView = new QCallView("callView");

    public final NumberPath<Long> count = createNumber("count", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QCallView(String variable) {
        this(CallView.class, forVariable(variable), INITS);
    }

    public QCallView(Path<? extends CallView> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCallView(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCallView(PathMetadata metadata, PathInits inits) {
        this(CallView.class, metadata, inits);
    }

    public QCallView(Class<? extends CallView> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

