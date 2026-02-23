package UserInterface;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
import Manager.SalaryManager;
import Model.Employee;
import Model.SalaryRecord;

public class ReportMenu {
    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;
    private SalaryManager salaryManager;
    private Scanner sc;
    private Map<Integer, Runnable> menu;
    //constructor
    public ReportMenu(EmployeeManager employeeManager, AttendanceManager attendanceManager, SalaryManager salaryManager,Scanner sc) {
        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
        this.salaryManager = salaryManager;
        this.sc = sc;
        this.menu = new HashMap<>();
        menu.put(1, ()-> LowAttendanceReport());
        menu.put(2,() -> HighestPaidEmployeeReport());

    }
    public void run(){
         while (true) {
            Display.showReportMenu();
            
            int choose;
            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (choose == 0) {
                break; 
            }

            Runnable r = menu.get(choose);
            if (r == null) {
                System.out.println("Invalid input (0-2 only).");
            } else {
                r.run();
            }
        }
    }
    //attendance
    public void LowAttendanceReport(){
        int month, year;
        while (true) {
            try {
                System.out.println("Month: ");
                month = Integer.parseInt(sc.nextLine());
                System.out.println("Year: ");
                year= Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input.");
            }
        }
        
         List<Employee> lowAttendancêEmployees = attendanceManager.isLowAttendance(month, year);

         System.out.println("---------------------LOW ATTENDANCE REPORT--------------------");
         for (Employee employee : lowAttendancêEmployees) {
            System.out.printf("%-12s | %-34s | %-2d absents days\n",employee.getEmployeeID(), employee.getEmployeeName(),attendanceManager.getAbsentDay(employee.getEmployeeID(), month, year));
         }
         System.out.println("--------------------------------------------------------------");
         System.out.println("Press ENTER to return...");
         sc.nextLine();

    }
    public void HighestPaidEmployeeReport(){
        int month, year;
       
         while (true) {
            try {
                System.out.println("Month: ");
                month = Integer.parseInt(sc.nextLine());
                System.out.println("Year: ");
                year= Integer.parseInt(sc.nextLine());
                if (month < 1 || month > 12) {
                    System.out.println("Month must be between 1 and 12.");
                    continue;
        }
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input.");
            }
        }
        SalaryRecord highest = salaryManager.getHighestEmployee(month, year);
        if (highest==null||highest.getTotalSalary()==0) {
            System.out.println("Highest employee is null!(This month's payroll is not yet available.)");
            return;
        }
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("-----------------HIGHEST PAID EMPLOYEE-----------------------------------");
        System.out.printf("%-12s | %-34s | %-18s VND\n",
         highest.getEmployeeID(),
         employeeManager.findEmployeeByID(highest.getEmployeeID()).getEmployeeName(),
         df.format(highest.getTotalSalary()));
        System.out.println("-------------------------------------------------------------------------");
    }

    
}
