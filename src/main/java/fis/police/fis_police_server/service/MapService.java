package fis.police.fis_police_server.service;

import com.mysema.commons.lang.Pair;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.CenterSelectDateResponseDTO;
import org.json.simple.parser.ParseException;

import java.util.List;

public interface MapService {

    //선택된 시설 중심으로 가까운 agent List 보낸다
    List<CenterSelectDateResponseDTO> agentNearCenter(Center center, Long range);

    //선택된 시설 중심으로 일정거리(distance)의 Center List 보낸다
    List<Center> centerNearCenter(Center center);

    Pair<Double, Double> addressToLocation(String address) throws ParseException;

    Double distance(double lat1, double lon1, double lat2, double lon2);
}
