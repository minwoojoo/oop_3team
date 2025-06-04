package kr.ac.catholic.cls032690125.oop3team.server.structs;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ServerRequestListener을 상속한 클래스 내에서 클라이언트 측 패킷을 받을 매서드 위에 붙여 사용하십시오
 *
 * @see ServerRequestListener
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServerRequestHandler {
    /**
     * @return 서버한테서 받을 패킷의 클래스 (ServerResponseBasePacket을 상속함)
     */
    Class<? extends ClientOrderBasePacket> value();
}
