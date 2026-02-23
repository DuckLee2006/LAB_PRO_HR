package UserInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
// import Manager.Report;
import Manager.SalaryManager;
import StorageData.AttendanceStorage;
import StorageData.EmployeeStorage;
import StorageData.SalaryStorage;

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


    //constructor test
    public MenuConsole(String employeeFileName, String attendanceFileName, String salaryFileName) {
         //load employee data from file
        //map quản lý
        this.sc = new Scanner(System.in);
        //load file khi mới khởi tạo. 

        //load employee data from file
        EmployeeStorage employeeStorage = new EmployeeStorage(employeeFileName);
        this.employeeManager = new EmployeeManager(employeeStorage.loadEmployeesFromFile());
        //load attendance data from file
        AttendanceStorage attendanceStorage = new AttendanceStorage(attendanceFileName);
        this.attendanceManager = new AttendanceManager(attendanceStorage.loadAttendanceFromFile(), this.employeeManager);
        //load salary data from file
        SalaryStorage salaryStorage = new SalaryStorage(salaryFileName);
        this.salaryManager = new SalaryManager(attendanceManager, salaryStorage.loadSalariesFromFile());

        //đối tượng quản lý
        //menu
        employeeMenu = new EmployeeMenu(employeeManager, sc, employeeFileName);
        attendanceManagerMenu = new AttendanceManagerMenu(attendanceManager, employeeManager, sc, attendanceFileName);
        salaryMenu = new SalaryMenu(employeeManager,salaryManager, sc, salaryFileName);
        this.reportMenu = new ReportMenu(employeeManager, attendanceManager, salaryManager, sc);
        this.menu = new HashMap<>();
        menu.put(1,() -> employeeMenu.run());
        menu.put(2,() -> attendanceManagerMenu.run());
        menu.put(3,() -> salaryMenu.run());
        menu.put(4, () -> reportMenu.run());
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
