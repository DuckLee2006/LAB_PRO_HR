package Model;

public enum EmployeeType {
    FULL_TIME("01"),
    PART_TIME("02");

    private final String code;  
    EmployeeType(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
