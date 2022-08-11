package fis.police.fis_police_server.service.serviceImpl;

import com.mysema.commons.lang.Pair;
import fis.police.fis_police_server.domain.Center;
import fis.police.fis_police_server.dto.CenterSelectDateResponseDTO;
import fis.police.fis_police_server.repository.AgentRepository;
import fis.police.fis_police_server.repository.CenterRepository;
import fis.police.fis_police_server.service.MapService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    /*
        날짜 : 2022/01/13 3:23 오후
        작성자 : 현승구
        작성내용 : map 작성
    */

    private final AgentRepository agentRepository;
    private final CenterRepository centerRepository;
    private final MapConfig mapConfig;
    private final HttpComponentsClientHttpRequestFactory httpRequestFactory;

    @Override
    public List<CenterSelectDateResponseDTO> agentNearCenter(Center center, Long range) {
        Center target = centerRepository.findById(center.getId());
        Double latitude = target.getC_latitude();
        Double longitude = target.getC_longitude();

        List<CenterSelectDateResponseDTO> agentList = agentRepository.findNearAgent(latitude, longitude, range);
        while(agentList.size() <= 5 && range < 30) {
            range = range + 2;
            agentList = agentRepository.findNearAgent(latitude, longitude, range);
        }
        agentList.stream().forEach(agent -> System.out.println("agent.getId() = " + agent.getAgent_id()));

        /**
         * 원보라 : 거리안에서 가까운 순서대로 리스트 반환
         */
        List<CenterSelectDateResponseDTO> agentDto =agentList;

        for (CenterSelectDateResponseDTO centerSelectDateResponseDTO : agentDto) {
            Double dist = distance(centerSelectDateResponseDTO.getA_latitude(), centerSelectDateResponseDTO.getA_longitude(), center.getC_latitude(), center.getC_longitude());
            centerSelectDateResponseDTO.setDistance(dist);
        }

        //시설과 가까운 거리 순 정렬
        Comparator<CenterSelectDateResponseDTO> cp = new Comparator<CenterSelectDateResponseDTO>() {
            @Override
            public int compare(CenterSelectDateResponseDTO o1, CenterSelectDateResponseDTO o2) {
                Double a = o1.getDistance();
                Double b = o2.getDistance();

                if (a > b) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        Collections.sort(agentDto, cp);

        return agentDto;
    }

    @Override
    public List<Center> centerNearCenter(Center center) {
        List<Center> centerList = centerRepository.findNearCenter(center.getC_latitude(), center.getC_longitude());
        if(centerList.isEmpty()) throw new NoResultException("centerNearCenter에서 결과가 없음");
        else return centerList;
    }

    @Override
    public Pair<Double, Double> addressToLocation(String address) throws ParseException, IndexOutOfBoundsException,
            RestClientException {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-NCP-APIGW-API-KEY-ID", mapConfig.getApiId());
        httpHeaders.add("X-NCP-APIGW-API-KEY", mapConfig.getApiKey());
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + address;
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject fullResponse = (JSONObject) jsonParser.parse(responseEntity.getBody());
        JSONArray jsonAddress = (JSONArray) fullResponse.get("addresses");
        JSONObject addressResponse = (JSONObject) jsonAddress.get(0);
        return new Pair<Double, Double>(Double.parseDouble(addressResponse.get("x").toString()), Double.parseDouble(addressResponse.get("y").toString()));
    }

    @Override
    public Double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        return (dist);
    }


    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
