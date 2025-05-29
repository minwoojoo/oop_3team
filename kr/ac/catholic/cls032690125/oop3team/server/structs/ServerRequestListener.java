package kr.ac.catholic.cls032690125.oop3team.server.structs;

import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 서버한테서 받은 패킷을 처리하는 리스너 클래스입니다. 서버 측 컨트롤러의 기본 클래스로 사용합니다.
 * 패킷을 받을 매서드 위에 ServerRequestHandler을 붙여 사용하십시오.
 * 매서드 이름은 중요하지 않습니다.
 *
 * @see ServerRequestHandler
 * @see Server
 * @apiNote ServerRequestHandler 어노테이션을 붙인 매서드는 ServerClientHandler와 ClientOrderBasePacket을 상속한 클래스(어노테이션으로 지정한)를 인자로 받아야 합니다.
 */
public abstract class ServerRequestListener {
    private final Map<Class<? extends ClientOrderBasePacket>, List<Method>> handlerMap = new HashMap<>();
    protected final Server client;

    public ServerRequestListener(Server client) {
        this.client = client;

        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ServerRequestHandler.class)) {
                Class<? extends ClientOrderBasePacket> packetType = method.getAnnotation(ServerRequestHandler.class).value();
                add(packetType, method);
            }
        }
    }

    private void add(Class<? extends ClientOrderBasePacket> clazz, Method method) {
        if(!handlerMap.containsKey(clazz)) handlerMap.put(clazz, new ArrayList<>());
        handlerMap.get(clazz).add(method);
    }

    public void dispatch(ServerClientHandler sch, ClientOrderBasePacket packet) {
        List<Method> methods = handlerMap.get(packet.getClass());
        if (methods != null) {
            for(Method method : methods) {
                try {
                    method.invoke(this, sch, packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
