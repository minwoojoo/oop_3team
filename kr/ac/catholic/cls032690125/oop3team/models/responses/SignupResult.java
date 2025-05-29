package kr.ac.catholic.cls032690125.oop3team.models.responses;

public enum SignupResult {
    SUCCESS("S"),
    DUPLICATED("D"),
    FAILED("F");

    private String message;
    SignupResult(String message) {
        this.message = message;
    }
}
