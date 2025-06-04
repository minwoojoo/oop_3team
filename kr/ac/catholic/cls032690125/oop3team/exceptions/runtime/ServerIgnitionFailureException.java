package kr.ac.catholic.cls032690125.oop3team.exceptions.runtime;

public class ServerIgnitionFailureException extends RuntimeException {
    public ServerIgnitionFailureException(String message) {
        super(message);
    }

    public ServerIgnitionFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
