package fis.police.fis_police_server.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CalendarResponse {

    private String agent_name;
    private List<String> visited_date = new ArrayList<>();
    private List<String> will_go_date = new ArrayList<>();
}
