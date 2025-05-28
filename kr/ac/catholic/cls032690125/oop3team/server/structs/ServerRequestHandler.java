package kr.ac.catholic.cls032690125.oop3team.server.structs;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServerRequestHandler {
    Class<? extends ClientOrderBasePacket> value();
}
