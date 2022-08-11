package fis.police.fis_police_server.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * fis.police.fis_police_server.dto.QCenterSelectDateResponseDTO is a Querydsl Projection type for CenterSelectDateResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCenterSelectDateResponseDTO extends ConstructorExpression<CenterSelectDateResponseDTO> {

    private static final long serialVersionUID = 1209681061L;

    public QCenterSelectDateResponseDTO(com.querydsl.core.types.Expression<Long> agent_id, com.querydsl.core.types.Expression<String> a_name, com.querydsl.core.types.Expression<String> a_ph, com.querydsl.core.types.Expression<String> a_code, com.querydsl.core.types.Expression<String> a_address, com.querydsl.core.types.Expression<fis.police.fis_police_server.domain.enumType.HasCar> a_hasCar, com.querydsl.core.types.Expression<String> a_equipment, com.querydsl.core.types.Expression<java.time.LocalDate> a_receiveDate, com.querydsl.core.types.Expression<Double> a_latitude, com.querydsl.core.types.Expression<Double> a_longitude) {
        super(CenterSelectDateResponseDTO.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, fis.police.fis_police_server.domain.enumType.HasCar.class, String.class, java.time.LocalDate.class, double.class, double.class}, agent_id, a_name, a_ph, a_code, a_address, a_hasCar, a_equipment, a_receiveDate, a_latitude, a_longitude);
    }

}

