package fis.police.fis_police_server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -721994678L;

    public static final QUser user = new QUser("user");

    public final ListPath<Call, QCall> callList = this.<Call, QCall>createList("callList", Call.class, QCall.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Messenger, QMessenger> messengerList = this.<Messenger, QMessenger>createList("messengerList", Messenger.class, QMessenger.class, PathInits.DIRECT2);

    public final EnumPath<fis.police.fis_police_server.domain.enumType.UserAuthority> u_auth = createEnum("u_auth", fis.police.fis_police_server.domain.enumType.UserAuthority.class);

    public final StringPath u_name = createString("u_name");

    public final StringPath u_nickname = createString("u_nickname");

    public final StringPath u_ph = createString("u_ph");

    public final StringPath u_pwd = createString("u_pwd");

    public final DatePath<java.time.LocalDate> u_sDate = createDate("u_sDate", java.time.LocalDate.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

