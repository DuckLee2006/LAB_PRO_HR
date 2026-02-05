package Model;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class FullTimeEmployee extends Employee {
    private final double OT_Salary = 80000.0;

    public FullTimeEmployee(String employeeID, String employeeName, EmployeeStatus status, Department department, LocalDate starDate, String jobTitle, double basicSalary) {
        super(employeeID, employeeName, status, department, starDate, EmployeeType.FULL_TIME, jobTitle, basicSalary);
    }

    public double getOT_Salary() {
        return OT_Salary;
    }

   public String dataString(){
         DecimalFormat df = new DecimalFormat("####");
        return super.getEmployeeID()+","+super.getEmployeeName()+","+super.getStatus()+","+super.getDepartment()+","+super.getStartDate()+","+super.getJobTitle()+","+df.format(super.getBasicSalary())+","+super.getType();
    }
    //data format: id,name,status,dept,startdate, jobtitle,basicsalary,type

}
