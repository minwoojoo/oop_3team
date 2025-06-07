package kr.ac.catholic.cls032690125.oop3team.features.setting.serverside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.*;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import java.util.List;

public class SsettingController extends ServerRequestListener {
    private final SettingDAO settingDAO;

    public SsettingController(Server server) {
        super(server);
        this.settingDAO = new SettingDAO(server);
    }

    @ServerRequestHandler(CWorkStatusUpdateReq.class)
    public void handleWorkStatusUpdate(ServerClientHandler sch, CWorkStatusUpdateReq req) {
        System.out.println("[SsettingController] 업무상태 변경 요청 받음: userId=" + req.getUserId() + ", workStatus=" + req.getWorkStatus());
        boolean success = settingDAO.updateWorkStatus(req.getUserId(), req.getWorkStatus());
        System.out.println("[SsettingController] DB 업데이트 결과: " + success);
        sch.send(new SWorkStatusUpdateRes(req.getRequestId(), success));
    }

    @ServerRequestHandler(CWorkStatusGetReq.class)
    public void handleWorkStatusGet(ServerClientHandler sch, CWorkStatusGetReq req) {
        String workStatus = settingDAO.getWorkStatus(req.getUserId());
        boolean success = !workStatus.isEmpty();
        sch.send(new SWorkStatusGetRes(req.getRequestId(), success, workStatus));
    }

    @ServerRequestHandler(CBlockListReq.class)
    public void handleBlockList(ServerClientHandler sch, CBlockListReq req) {
        List<String> blockedNames = settingDAO.getBlockedUserNames(req.getUserId());
        sch.send(new SBlockListRes(req.getRequestId(), blockedNames));
    }

    @ServerRequestHandler(CUnblockUserReq.class)
    public void handleUnblockUser(ServerClientHandler sch, CUnblockUserReq req) {
        boolean success = settingDAO.unblockUser(req.getUserId(), req.getBlockedUserName());
        sch.send(new SUnblockUserRes(req.getRequestId(), success));
    }

    @ServerRequestHandler(CUserNameGetReq.class)
    public void handleGetUserName(ServerClientHandler sch, CUserNameGetReq req) {
        String userId = req.getUserId();
        String name = settingDAO.getUserName(userId);
        SUserNameGetRes res = new SUserNameGetRes(req.getRequestId(), name);
        sch.send(res);
    }
}
