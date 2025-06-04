package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 서버한테서 받은 패킷을 처리하는 리스너 클래스입니다.
 * 패킷을 받을 매서드 위에 ClientResponseHandler을 붙여 사용하십시오.
 * 매서드 이름은 중요하지 않습니다.
 *
 * @see ClientResponseHandler
 * @see Client
 */
public abstract class ClientResponseListener {
    private final Map<Class<? extends ServerResponseBasePacket>, List<Method>> handlerMap = new HashMap<>();
    private Method master = null;
    protected final Client client;

    public ClientResponseListener(Client client) {
        this.client = client;

        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ClientResponseHandler.class)) {
                Class<? extends ServerResponseBasePacket> packetType = method.getAnnotation(ClientResponseHandler.class).value();
                if (packetType == ServerResponseBasePacket.class) {
                    master = method;
                }
                else
                    add(packetType, method);
            }
        }
    }

    private void add(Class<? extends ServerResponseBasePacket> clazz, Method method) {
        if(!handlerMap.containsKey(clazz)) handlerMap.put(clazz, new ArrayList<>());
        handlerMap.get(clazz).add(method);
    }

    /**
     * 내부 사용 코드입니다.
     *
     * @param packet 서버한테서 받은 패킷
     */
    public void dispatch(ServerResponseBasePacket packet) {
        if(master != null) {
            try{
                master.invoke(this, packet);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        List<Method> methods = handlerMap.get(packet.getClass());
        if (methods != null) {
            for(Method method : methods) {
                try {
                    method.invoke(this, packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
