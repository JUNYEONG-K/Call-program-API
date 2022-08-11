package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInputTime is a Querydsl query type for InputTime
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QInputTime extends EntityPathBase<InputTime> {

    private static final long serialVersionUID = -446213992L;

    public static final QInputTime inputTime = new QInputTime("inputTime");

    public final DateTimePath<java.time.LocalDateTime> inputDate = createDateTime("inputDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> userid = createNumber("userid", Long.class);

    public QInputTime(String variable) {
        super(InputTime.class, forVariable(variable));
    }

    public QInputTime(Path<? extends InputTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInputTime(PathMetadata metadata) {
        super(InputTime.class, metadata);
    }

}

