package Model;

import java.text.DecimalFormat;
import java.time.LocalDate;

public abstract class Employee {
    private final String employeeID;
    private String EmployeeName;
    private EmployeeStatus status;
    private Department department;
    private final LocalDate startDate;
    private EmployeeType type;
    private String jobTitle;
    private double basicSalary;
    //constructor
    
    public Employee(String employeeID, String employeeName, EmployeeStatus status, Department department, LocalDate startDate, EmployeeType type, String jobTitle, double basicSalary) {
        this.employeeID = employeeID;
        EmployeeName = employeeName;
        this.status = status;
        this.department = department;
        this.startDate = startDate;
        this.type = type;
        this.jobTitle = jobTitle.toUpperCase();
        this.basicSalary = basicSalary;
    }
    //getter and setters
    public String getEmployeeID() {
        return employeeID;
    }
    public String getEmployeeName() {
        return EmployeeName;
    }
    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }
    public EmployeeStatus getStatus() {
        return status;
    }
    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public EmployeeType getType() {
        return type;
    }
    public void setType(EmployeeType type) {
        this.type = type;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public double getBasicSalary() {
        return basicSalary;
    }
    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###");

        return  "Employee ID: "+employeeID+"\n"
                +"Full Name: "+EmployeeName+"\n"
                +"Department: "+department+"\n"
                +"Job Title: "+jobTitle+"\n"
                +"Type: "+type+"\n"
                +"Date of Joining: "+startDate+"\n"
                +"Basic Salary: "+df.format(basicSalary)+"\n";
    }
    
    public abstract double getOT_Salary();
    public abstract String dataString();
}
