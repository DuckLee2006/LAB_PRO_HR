package Manager;

import java.util.List;

import Model.Employee;

public class Report {
    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;
    private SalaryManager salaryManager;

    //constructor
    public Report(EmployeeManager employeeManager, AttendanceManager attendanceManager, SalaryManager salaryManager) {
        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
        this.salaryManager = salaryManager;
    }

    public List<Employee> getLowAttentdance (int month, int year){
        return attendanceManager.isLowAttendance(month, year);
    }

    public Employee getHighestPaidEmployee(int month, int year){
        return employeeManager.findEmployeeByID(salaryManager.getHighestEmployee(month, year).getEmployeeID());
    }

}