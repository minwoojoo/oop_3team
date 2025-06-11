package kr.ac.catholic.cls032690125.oop3team.features.attendance.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.attendance.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Attendance;
import kr.ac.catholic.cls032690125.oop3team.models.AttendanceEditRequest;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;

import java.sql.SQLException;
import java.util.List;

public class SAttendanceController extends ServerRequestListener {
    private final AttendanceDAO attendanceDAO;

    public SAttendanceController(Server server) {
        super(server);
        this.attendanceDAO = new AttendanceDAO(server);
    }

    @ServerRequestHandler(CCheckInRequest.class)
    public void checkIn(ServerClientHandler handler, CCheckInRequest request) {
        try {
            attendanceDAO.checkIn(request.getUserId(), request.getChatroomId());
            handler.send(new SCheckInResponse(request.getRequestId(), true, "출근 완료"));
        } catch (Exception e) {
            handler.send(new SCheckInResponse(request.getRequestId(), false, e.getMessage()));
        }
    }

    @ServerRequestHandler(CCheckOutRequest.class)
    public void checkOut(ServerClientHandler handler, CCheckOutRequest req) {
        try {
            attendanceDAO.checkOut(req.getUserId(), req.getChatroomId());
            handler.send(new SCheckOutResponse(req.getRequestId(), true, "퇴근 완료"));
        } catch (Exception e) {
            handler.send(new SCheckOutResponse(req.getRequestId(), false, e.getMessage()));
        }
    }

    @ServerRequestHandler(CSubmitEditAttendanceRequest.class)
    public void submitEditRequest(ServerClientHandler handler, CSubmitEditAttendanceRequest request) {
        try {
            attendanceDAO.submitEditRequest(
                    request.getUserId(),
                    request.getDate(),
                    request.getCheckIn(),
                    request.getCheckOut(),
                    request.getReason()
            );

            handler.send(new SSubmitEditAttendanceResponse(request.getRequestId(), true, "요청 완료"));
        } catch (Exception e) {
            handler.send(new SSubmitEditAttendanceResponse(request.getRequestId(), false, e.getMessage()));
        }
    }

    @ServerRequestHandler(CGetAttendanceListRequest.class)
    public void getAttendanceList(ServerClientHandler handler, CGetAttendanceListRequest request) {
        try {
            List<Attendance> records = attendanceDAO.getAttendanceByChatroomId(request.getChatroomId());
            handler.send(new SGetAttendanceListResponse(
                    request.getRequestId(),
                    true,
                    records,
                    "조회 성공"

            ));
        } catch (Exception e) {
            handler.send(new SGetAttendanceListResponse(
                    request.getRequestId(),
                    false,
                    null,
                    "조회 실패: " + e.getMessage()
            ));
        }
    }

    @ServerRequestHandler(CCheckIfAlreadyCheckedInRequest.class)
    public void checkIfAlreadyCheckedIn(ServerClientHandler handler, CCheckIfAlreadyCheckedInRequest request) {
        try {
            boolean exists = attendanceDAO.hasAttendanceOnDate(request.getUserId(), request.getDate());
            handler.send(new SCheckIfAlreadyCheckedInResponse(request.getRequestId(), true, exists));
        } catch (Exception e) {
            handler.send(new SCheckIfAlreadyCheckedInResponse(request.getRequestId(), false, false));
        }
    }

    @ServerRequestHandler(CGetAttendanceEditRequestList.class)
    public void getAttendanceEditRequestList(ServerClientHandler handler, CGetAttendanceEditRequestList request) {
        try {
            List<AttendanceEditRequest> requests = attendanceDAO.getEditRequests();
            SGetAttendanceEditRequestList response = new SGetAttendanceEditRequestList(
                    request.getRequestId(), true, "요청 목록을 불러왔습니다.", requests
            );
            handler.send(response);
        } catch (Exception e) {
            SGetAttendanceEditRequestList response = new SGetAttendanceEditRequestList(
                    request.getRequestId(), false, "DB 오류: " + e.getMessage(), null);
            handler.send(response);
        }
    }

    @ServerRequestHandler(CApproveEditAttendanceRequest.class)
    public void approveEditAttendanceRequest(ServerClientHandler handler, CApproveEditAttendanceRequest request) {
        try {
            attendanceDAO.approveEditRequest(request.getEditRequestId(), request.isApproved());

            String msg = request.isApproved() ? "요청이 승인되었습니다." : "요청이 거절되었습니다.";
            handler.send(new SApproveEditAttendanceResponse(request.getRequestId(), true, msg));
        } catch (SQLException e) {
            handler.send(new SApproveEditAttendanceResponse(request.getRequestId(), false, "처리 실패: " + e.getMessage()));
        }
    }
}

