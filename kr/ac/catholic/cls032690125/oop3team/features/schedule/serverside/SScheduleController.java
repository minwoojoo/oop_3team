package kr.ac.catholic.cls032690125.oop3team.features.schedule.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.schedule.shared.CScheduleCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.schedule.shared.CScheduleListPacket;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public class SScheduleController extends ServerRequestListener {
    public SScheduleController(Server server) {
        super(server);
        scheduleDAO = new ScheduleDAO(server);
    }

    private ScheduleDAO scheduleDAO;

    @ServerRequestHandler(CScheduleListPacket.class)
    public void scheduleList(ServerClientHandler sch, CScheduleListPacket packet) {
        sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), scheduleDAO.getSchedules(packet.getChatroomId())));
    }

    @ServerRequestHandler(CScheduleCreatePacket.class)
    public void scheduleCreate(ServerClientHandler sch, CScheduleCreatePacket packet) {
        var res = scheduleDAO.createSchedule(packet.getSchedule());
        sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), res != -1));
    }
}
