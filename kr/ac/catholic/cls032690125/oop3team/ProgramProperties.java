package kr.ac.catholic.cls032690125.oop3team;

import java.util.Map;

public class ProgramProperties {
    private final ProgramMode mode;

    private final boolean isDebug;

    /* Clientside */
    private final String clientTarget;
    private final int clientTargetPort;

    /* Serverside */
    private final int serverPort;
    private final String serverDBDriver;
    private final String serverDBPath;
    private final String serverDBID;
    private final String serverDBPassword;
    private final String serverDBName;

    public ProgramProperties(Map<String, String> envs) {
        mode = ProgramMode.valueOf(envs.get("CLS032690125oop3team_MODE"));
        isDebug = Boolean.parseBoolean(envs.get("CLS032690125oop3team_DEBUG"));

        switch (mode) {
            case CLIENT:
                clientTarget = envs.get("CLS032690125oop3team_CLIENT_TARGET");
                clientTargetPort = Integer.parseInt(envs.get("CLS032690125oop3team_CLIENT_TARGET_PORT"));

                serverPort = -1;
                serverDBDriver = null;
                serverDBPath = null;
                serverDBID = null;
                serverDBPassword = null;
                serverDBName = null;
                break;
            case SERVER:
                clientTarget = null;
                clientTargetPort = -1;

                serverPort = Integer.parseInt(envs.get("CLS032690125oop3team_SERVER_PORT"));

                serverDBDriver = envs.get("CLS032690125oop3team_SERVER_DB_DRIVER");
                serverDBPath = envs.get("CLS032690125oop3team_SERVER_DB_PATH");
                serverDBID = envs.get("CLS032690125oop3team_SERVER_DB_ID");
                serverDBPassword = envs.get("CLS032690125oop3team_SERVER_DB_PASSWORD");
                serverDBName = envs.get("CLS032690125oop3team_SERVER_DB");
                break;
            default:
                clientTarget = null;
                clientTargetPort = -1;

                serverPort = -1;
                serverDBDriver = null;
                serverDBPath = null;
                serverDBID = null;
                serverDBPassword = null;
                serverDBName = null;
                break;
        }
    }

    public ProgramMode getMode() { return mode; }
    public boolean isDebug() { return isDebug; }

    public String getClientTarget() {
        return clientTarget;
    }

    public int getClientTargetPort() {
        return clientTargetPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerDBDriver() {
        return serverDBDriver;
    }

    public String getServerDBPath() {
        return serverDBPath;
    }

    public String getServerDBID() {
        return serverDBID;
    }

    public String getServerDBPassword() {
        return serverDBPassword;
    }

    public String getServerDBName() {
        return serverDBName;
    }
}