package Model;

public enum Department {
    HR("01"),
    IT("02"),
    ACCOUNTING("03"),
    SALES("04"),
    MARKETING("05");

    private final String code;  
    Department(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    
}
