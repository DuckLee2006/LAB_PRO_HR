package UserInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
import Manager.Report;
import Manager.SalaryManager;

public class MenuConsole {
    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;
    private SalaryManager salaryManager;
    private Map<Integer, Runnable> menu;
    private Report report;
    private Scanner sc;

    //menucon
    private EmployeeMenu employeeMenu;
    private AttendanceManagerMenu attendanceManagerMenu;

    //constructor
    public MenuConsole() {
        //map quản lý
        this.sc = new Scanner(System.in);
        //đối tượng quản lý
        this.employeeManager = new EmployeeManager();
        this.attendanceManager = new AttendanceManager(employeeManager);
        this.salaryManager = new SalaryManager(attendanceManager);
        this.report = new Report(employeeManager, attendanceManager, salaryManager);
        //menu
        employeeMenu = new EmployeeMenu(employeeManager, sc);
        attendanceManagerMenu = new AttendanceManagerMenu(attendanceManager, employeeManager, sc);
        this.menu = new HashMap<>();
        menu.put(1,() -> employeeMenu.run());
        menu.put(2,() -> attendanceManagerMenu.run());
    }
    //construcor 2
    public MenuConsole(String test) {
        this.employeeManager = null;
        this.attendanceManager = null;
        this.salaryManager = null;
        this.report = null;
        this.sc = new Scanner(System.in);
        this.employeeMenu = new EmployeeMenu(employeeManager, sc);
        this.attendanceManagerMenu = new AttendanceManagerMenu(attendanceManager, employeeManager, sc);
        this.menu = new HashMap<>();
        menu.put(1,() -> employeeMenu.run());
        menu.put(2,() -> attendanceManagerMenu.run());
    }
    //setter
    
    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;

        if (employeeMenu != null) {
            employeeMenu.setEmployeeManager(employeeManager);
        }

        if (attendanceManagerMenu != null) {
            attendanceManagerMenu.setEmployeeManager(employeeManager);
    }
}
    public void setAttendanceManager(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }
    public void setSalaryManager(SalaryManager salaryManager) {
        this.salaryManager = salaryManager;
    }
    //run
    public void run(){
       
        while (true) {
             Display.showMainMenu();
            int Choose;
            try {
                Choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input!");
                continue;
            }
            if (Choose == 0) {
            System.out.println("END!");
            break; 
        }
            Runnable r = menu.get(Choose);
            if (r ==null) {
                System.out.println("Invalid input");
            }else{
                r.run();
            }
        }
    }
    

    

   
    
    
}
