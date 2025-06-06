//package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside;
//
//import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.serverside.AttendanceDAO;
//import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared.*;
//import kr.ac.catholic.cls032690125.oop3team.server.Server;
//import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
//import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
//import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
//
//public class SAttendanceController extends Server {
//    private final AttendanceDAO attendanceDAO;
//
//    public SAttendanceController(Server server, AttendanceDAO attendanceDAO) {
//        super();
//
//        this.attendanceDAO = attendanceDAO;
//    }
//
//    @Override
//    public void dispatch(ServerClientHandler handler, ClientOrderBasePacket order) {
//        if (order instanceof CCheckInRequest request){
//            String userId = request.getUserId();
//            try {
//                attendanceDAO.checkIn(userId);
//                handler.send(new SCheckInResponse(request.getRequestId(), true, "출근 완료"));
//            } catch (Exception e) {
//                handler.send(new SCheckInResponse(request.getRequestId(), false, e.getMessage()));
//            }
//        }
//
//        if (order instanceof CCheckOutRequest request){
//            String userId = request.getUserId();
//            try {
//                attendanceDAO.checkOut(userId);
//                handler.send(new SCheckOutResponse((int) request.getRequestId(), true, "퇴근 완료"));
//            } catch (Exception e) {
//                handler.send(new SCheckOutResponse((int) request.getRequestId(), false, e.getMessage()));
//            }
//        }
//
//        if (order instanceof CSubmitEditAttendanceRequest request) {
//            try {
//                attendanceDAO.submitEditRequest(
//                        request.getUserId(),
//                        request.getDate(),
//                        request.getCheckIn(),
//                        request.getCheckOut(),
//                        request.getReason()
//                );
//                handler.send(new SSubmitEditAttendanceResponse(request.getRequestId(), true, "요청 완료"));
//            } catch (Exception e) {
//                handler.send(new SSubmitEditAttendanceResponse(request.getRequestId(), false, e.getMessage()));
//            }
//        }
//
//
//    }
//}
