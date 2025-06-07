package kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Keyword;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;

import java.util.List;

public class SKeywordController extends ServerRequestListener {
    private final KeywordDAO keywordDAO;

    public SKeywordController(Server server) {
        super(server);
        keywordDAO = new KeywordDAO(server);
    }

    @ServerRequestHandler(CAddKeywordRequest.class)
    public void handleAddKeywordRequest(ServerClientHandler handler, CAddKeywordRequest request) {
        try {
            keywordDAO.addKeyword(request.getUserId(), request.getChatroomId(), request.getKeyword());
            handler.send(new SAddKeywordResponse(request.getRequestId(), true, "키워드 추가 성공"));
        } catch (Exception e) {
            handler.send(new SAddKeywordResponse(request.getRequestId(), false, "추가 실패: " + e.getMessage()));
        }
    }

    @ServerRequestHandler(CDeleteKeywordRequest.class)
    public void handleDeleteKeywordRequest(ServerClientHandler handler, CDeleteKeywordRequest request) {
        try {
            keywordDAO.deleteKeyword(request.getUserId(), request.getChatroomId(), request.getKeyword());
            handler.send(new SDeleteKeywordResponse(request.getRequestId(), true, "키워드 삭제 성공"));
        } catch (Exception e) {
            handler.send(new SDeleteKeywordResponse(request.getRequestId(), false, "삭제 실패: " + e.getMessage()));
        }
    }

    @ServerRequestHandler(CGetKeywordListRequest.class)
    public void handleGetKeywordList(ServerClientHandler handler, CGetKeywordListRequest req) {
        try {
            List<Keyword> keywords = keywordDAO.getKeywords(req.getUserId(), req.getChatroomId());
            handler.send(new SGetKeywordListResponse(req.getRequestId(), keywords));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

