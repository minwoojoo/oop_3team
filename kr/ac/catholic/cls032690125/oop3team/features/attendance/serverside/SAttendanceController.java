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
            attendanceDAO.checkIn(request.getUserId(),request.getChatroomId());
            handler.send(new SCheckInResponse(request.getRequestId(), true, "출근 완료"));
        } catch (Exception e) {
            handler.send(new SCheckInResponse(request.getRequestId(), false, e.getMessage()));
        }
    }

    @ServerRequestHandler(CCheckOutRequest.class)
    public void checkOut(ServerClientHandler handler, CCheckOutRequest req) {
        try {
            attendanceDAO.checkOut(req.getUserId(),req.getChatroomId());
            handler.send(new SCheckOutResponse( req.getRequestId(),true,"퇴근 완료"));
        }catch (Exception e){
            handler.send(new SCheckOutResponse( req.getRequestId(),false, e.getMessage()));
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
        long requestId = request.getRequestId();
        try {
            List<AttendanceEditRequest> editRequests = attendanceDAO.getEditRequestsByUser(request.getUserId());
            System.out.println("수정 요청 목록 요청 수신: " + request.getRequestId());

            handler.send(new SGetAttendanceEditRequestList(requestId,true,"success", editRequests));
        } catch (SQLException e) {
            handler.send(new SGetAttendanceEditRequestList(requestId,false,"error",null));
        }
    }




}
