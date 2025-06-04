package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import javax.swing.*;

/**
 * ClientInteractResponse와 같으나 Swing 스레드에서 실행됩니다.
 * GUI에 접근할 때에는 반드시 이것을 사용하십시오.
 *
 * @param <T> 서버 측에서 보낸 패킷의 클래스 (ServerResponseBasePacket을 상속한)
 */
public abstract class ClientInteractResponseSwing<T extends ServerResponseBasePacket> implements ClientInteractResponse<T> {
    public void run(T data) {
        SwingUtilities.invokeLater(() -> this.execute(data));
    }

    /**
     * 실행 함수
     *
     * @param data 서버한테서 받은 패킷 (전송 실패 시 null이 반환됨)
     */
    protected abstract void execute(T data);
}
