package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfirm is a Querydsl query type for Confirm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfirm extends EntityPathBase<Confirm> {

    private static final long serialVersionUID = 1351111137L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfirm confirm = new QConfirm("confirm");

    public final QAgent agent;

    public final QCenter center;

    public final StringPath center_manger = createString("center_manger");

    public final EnumPath<fis.police.fis_police_server.domain.enumType.Complete> complete = createEnum("complete", fis.police.fis_police_server.domain.enumType.Complete.class);

    public final StringPath disabled = createString("disabled");

    public final StringPath etc = createString("etc");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath new_child = createString("new_child");

    public final StringPath old_child = createString("old_child");

    public final StringPath senile = createString("senile");

    public final StringPath visit_date = createString("visit_date");

    public final StringPath visit_time = createString("visit_time");

    public QConfirm(String variable) {
        this(Confirm.class, forVariable(variable), INITS);
    }

    public QConfirm(Path<? extends Confirm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfirm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfirm(PathMetadata metadata, PathInits inits) {
        this(Confirm.class, metadata, inits);
    }

    public QConfirm(Class<? extends Confirm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agent = inits.isInitialized("agent") ? new QAgent(forProperty("agent")) : null;
        this.center = inits.isInitialized("center") ? new QCenter(forProperty("center")) : null;
    }

}

