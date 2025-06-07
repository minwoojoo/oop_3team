package kr.ac.catholic.cls032690125.oop3team.features.schedule.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.schedule.shared.CScheduleCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.schedule.shared.CScheduleListPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Schedule;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public class CScheduleController extends StandardClientControl {
    public CScheduleController(Client client) {
        super(client);
    }

    public void requestSchedule(int chatroomId, ClientInteractResponse<ServerResponsePacketSimplefied<Schedule[]>> interactResponse) {
        client.request(new CScheduleListPacket(chatroomId), interactResponse);
    }

    public void sendSchedule(Schedule schedule, ClientInteractResponse<ServerResponsePacketSimplefied<Boolean>> interactResponse) {
        client.request(new CScheduleCreatePacket(schedule), interactResponse);
    }
}
