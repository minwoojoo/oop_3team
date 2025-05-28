package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ClientResponseListener {
    private final Map<Class<? extends ServerResponseBasePacket>, List<Method>> handlerMap = new HashMap<>();
    protected final Client client;

    //TODO: 정상 등록되는지 확인
    public ClientResponseListener(Client client) {
        this.client = client;

        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ClientResponseHandler.class)) {
                Class<? extends ServerResponseBasePacket> packetType = method.getAnnotation(ClientResponseHandler.class).value();
                add(packetType, method);
            }
        }
    }

    private void add(Class<? extends ServerResponseBasePacket> clazz, Method method) {
        if(!handlerMap.containsKey(clazz)) handlerMap.put(clazz, new ArrayList<>());
        handlerMap.get(clazz).add(method);
    }

    public void dispatch(ServerResponseBasePacket packet) {
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
