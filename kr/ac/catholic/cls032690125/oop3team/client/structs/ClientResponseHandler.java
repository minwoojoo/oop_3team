package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ClientResponseHandler {
    Class<? extends ServerResponseBasePacket> value();
}
