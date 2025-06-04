package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

/**
 * Client::request에 사용되는 코드 작성 편의를 위한 콜백 인터페이스입니다.
 * 경고! GUI에 접근할 때에는 ClientInteractResponseSafe를 사용하십시오!
 *
 * @param <T> 서버 측에서 보낸 패킷의 클래스 (ServerResponseBasePacket을 상속한)
 */
public interface ClientInteractResponse<T extends ServerResponseBasePacket> {
    /**
     * @param response 서버한테서 받은 패킷. (전송실패시 null이 반환됨)
     */
    void run(T response);
}
