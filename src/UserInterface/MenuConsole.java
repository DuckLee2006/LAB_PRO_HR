package UserInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
import Manager.Report;
import Manager.SalaryManager;
import Model.AttendanceRecord;
import Model.Employee;
import Model.SalaryRecord;

public class MenuConsole {
    private EmployeeManager employeeManager;
    private Map<String , Employee> employeeMap;

    private AttendanceManager attendanceManager;
    private Map<String, List<AttendanceRecord>> attenMap;

    private SalaryManager salaryManager;
    private Map<String, List<SalaryRecord>> salaryMap;

    private Map<Integer, Runnable> menu;
    private Report report;
    private Scanner sc;

    //menucon

    private EmployeeMenu employeeMenu;
    public MenuConsole() {
        //map quản lý
        this.employeeMap = new HashMap<>();
        this.attenMap = new HashMap<>();
        this.salaryMap = new HashMap<>();
        this.sc = new Scanner(System.in);
        //đối tượng quản lý
        this.employeeManager = new EmployeeManager(employeeMap);
        this.attendanceManager = new AttendanceManager(attenMap, employeeManager);
        this.salaryManager = new SalaryManager(salaryMap, attendanceManager);
        this.report = new Report(employeeManager, attendanceManager, salaryManager);
        //menu
        employeeMenu = new EmployeeMenu(employeeManager, sc);
        this.menu = new HashMap<>();
        menu.put(1,() -> employeeMenu.add(sc));



    }

    public void run(){
        Display.showMainMenu();
    }

   
    
    
}
