package Model;

import java.time.LocalDate;

public class AttendanceRecord {
    private String employeeID;
    private LocalDate date;
    private int OT;
    private AttendanceStatus status;
    //constructor
    public AttendanceRecord(String employeeID, LocalDate date, int OT, AttendanceStatus status) {
        this.employeeID = employeeID;
        this.date = date;
        this.OT = OT;
        this.status = status;
    }
    //getter and setters
    public String getEmployeeID() {
        return employeeID;
    }
    public LocalDate getDate() {
        return date;
    }
    public int getOT() {
        return OT;
    }
    public AttendanceStatus getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return "AttendanceRecord [employeeID=" + employeeID + ", date=" + date + ", OT=" + OT + ", status=" + status
                + "]";
    }
    

    
}
