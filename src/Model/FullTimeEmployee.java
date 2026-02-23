package Model;

import java.time.LocalDate;

public class FullTimeEmployee extends Employee {
    private final double OT_Salary = 80000.0;

    public FullTimeEmployee(String employeeID, String employeeName, EmployeeStatus status, Department department, LocalDate starDate, String jobTitle, double basicSalary) {
        super(employeeID, employeeName, status, department, starDate, EmployeeType.FULL_TIME, jobTitle, basicSalary);
    }

    public double getOT_Salary() {
        return OT_Salary;
    }

    //data format: id,name,status,dept,startdate, jobtitle,basicsalary,type

}
