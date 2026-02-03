package UserInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
// import Manager.Report;
import Manager.SalaryManager;

public class MenuConsole {
    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;
    private SalaryManager salaryManager;
    private Map<Integer, Runnable> menu;
    // private Report report;
    private Scanner sc;

    //menucon
    private EmployeeMenu employeeMenu;
    private AttendanceManagerMenu attendanceManagerMenu;
    private SalaryMenu salaryMenu;
    private ReportMenu reportMenu;

    //constructor
    public MenuConsole() {
        //map quản lý
        this.sc = new Scanner(System.in);
        //đối tượng quản lý
        this.employeeManager = new EmployeeManager();
        this.attendanceManager = new AttendanceManager(employeeManager);
        this.salaryManager = new SalaryManager(attendanceManager);
        // this.report = new Report(employeeManager, attendanceManager, salaryManager);
        //menu
        employeeMenu = new EmployeeMenu(employeeManager, sc);
        attendanceManagerMenu = new AttendanceManagerMenu(attendanceManager, employeeManager, sc);
        salaryMenu = new SalaryMenu(employeeManager,salaryManager, sc);
        this.reportMenu = new ReportMenu(employeeManager, attendanceManager, salaryManager, sc);
        this.menu = new HashMap<>();
        menu.put(1,() -> employeeMenu.run());
        menu.put(2,() -> attendanceManagerMenu.run());
        menu.put(3,() -> salaryMenu.run());
        menu.put(4, () -> reportMenu.run());
    }
    //construcor 2 cho test
    public MenuConsole(EmployeeManager employeeManager, AttendanceManager attendanceManager, SalaryManager salaryManager) {
        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
        this.salaryManager = salaryManager;
        this.sc = new Scanner(System.in);
        //menu
        this.employeeMenu = new EmployeeMenu(employeeManager, sc);
        this.attendanceManagerMenu = new AttendanceManagerMenu(attendanceManager, employeeManager, sc);
        this.salaryMenu = new SalaryMenu(employeeManager,salaryManager, sc);
        this.reportMenu = new ReportMenu(employeeManager, attendanceManager, salaryManager, sc);
        this.menu = new HashMap<>();
        menu.put(1,() -> employeeMenu.run());
        menu.put(2,() -> attendanceManagerMenu.run());
        menu.put(3,() -> salaryMenu.run());
        menu.put(4,() -> reportMenu.run());
    }
    //setter
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
