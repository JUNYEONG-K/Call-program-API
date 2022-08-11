package fis.police.fis_police_server.domain.enumType;

public enum AgentStatus {
    WORK, FIRED;

    public static boolean converter(AgentStatus agentStatus) {
        return agentStatus == AgentStatus.WORK;
    }
}
