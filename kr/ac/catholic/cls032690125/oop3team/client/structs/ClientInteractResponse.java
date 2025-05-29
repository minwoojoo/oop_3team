package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public interface ClientInteractResponse<T extends ServerResponseBasePacket> {
    void run(T response);
}
