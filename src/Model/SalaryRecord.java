package Model;

import java.text.DecimalFormat;
import java.time.Year;

public class SalaryRecord {
    private String employeeID;
    private int month;
    private int year;
    private int workingDay;
    private int OT;
    private int absentDay;
    private double TotalSalary;
    //constructor
    public SalaryRecord(Employee emp, int month, int year, int workingDay, int oT, int absentDay) {
        this.employeeID = emp.getEmployeeID();
        this.month = month;
        this.year = year;
        this.workingDay = workingDay;
        OT = oT;
        this.absentDay = absentDay;
        TotalSalary = calculateMonthSalary(emp.getBasicSalary(),emp.getOT_Salary());
    }
    //getter
    public String getEmployeeID() {
        return employeeID;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    public int getWorkingDay() {
        return workingDay;
    }
    public int getOT() {
        return OT;
    }
    public int getAbsentDay() {
        return absentDay;
    }
    public double getTotalSalary() {
        return TotalSalary;
    }
    
    public double calculateMonthSalary(double basicSalary, double overTimePay){
        return basicSalary + (OT*overTimePay) - absentDay*100000;
    }
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###");
        return "Total Working Days: "+ workingDay +"\n"
                +"Overtime Hourse: " +OT+"\n"
                +"Month: " + month+"\n"
                +"Year: "+ year +"\n"
                +"Absent Days: "+absentDay + "\n"
                +"Total Salary: "+df.format(TotalSalary) +"\n";
    }


    public String dataString(){
         DecimalFormat df = new DecimalFormat("####");
        return employeeID+","+month+","+","+year+","+workingDay+","+OT+","+","+absentDay+","+df.format(TotalSalary);
    }
    //format ghi data: id,month,year,workingday,ot,absentday,xxxxxxx
    

}
