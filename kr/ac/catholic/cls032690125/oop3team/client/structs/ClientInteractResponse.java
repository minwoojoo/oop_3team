package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public interface ClientInteractResponse {
    void run(ServerResponseBasePacket response);
}
