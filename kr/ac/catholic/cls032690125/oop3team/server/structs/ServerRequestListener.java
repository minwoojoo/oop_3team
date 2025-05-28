package kr.ac.catholic.cls032690125.oop3team.server.structs;

import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
