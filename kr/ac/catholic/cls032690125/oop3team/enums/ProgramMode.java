package kr.ac.catholic.cls032690125.oop3team.enums;

public enum ProgramMode {
    CLIENT("CLIENT"),
    SERVER("SERVER");

    private final String mode;
    ProgramMode(String mode) { this.mode=mode; }
    public String mode() { return mode; }
}
