package fis.police.fis_police_server.repository.queryMethod;

import com.querydsl.core.types.dsl.BooleanExpression;
import fis.police.fis_police_server.domain.QCenter;

/*
    날짜 : 2022/01/27 8:28 오후
    작성자 : 원보라
    작성내용 : like --> contains 로 바꿈 (like는 풀텍스트 일치만 반환, contains가 %like%처럼 동작)
    왜 안되지:?
*/
public class CenterQueryMethod {
    protected BooleanExpression cNameLike(String c_name){
       return c_name == null ? null : QCenter.center.c_name.contains(c_name);
    }

    protected BooleanExpression cPhLike(String c_ph) {
        return c_ph == null ? null : QCenter.center.c_ph.contains(c_ph);
    }

    protected BooleanExpression cAddressLike(String c_address) {
        return c_address == null ? null : QCenter.center.c_address.contains(c_address);
    }
}
