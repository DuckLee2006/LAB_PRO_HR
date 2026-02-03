package UserInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import CommonUtility.InputChecker;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
import Model.AttendanceRecord;
import Model.AttendanceStatus;

public class AttendanceManagerMenu {

    private AttendanceManager attendanceManager;
    private EmployeeManager employeeManager;
    private Map<Integer, Runnable> menu;
    private Scanner sc;
    //constructor
    public AttendanceManagerMenu(AttendanceManager attendanceManager, EmployeeManager employeeManager,Scanner sc) {
        this.attendanceManager = attendanceManager;
        this.employeeManager = employeeManager;
        this.menu = new HashMap<>();
        this.sc = sc;
        menu.put(1, () -> add());
        menu.put(2,() -> viewAll());
    }
    //setter
    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }
    public void setAttendanceManager(AttendanceManager attendanceManager){
        this.attendanceManager = attendanceManager;
    }
    //run
    public void run(){
        while (true) {
            Display.showAttendanceMenu();
            
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
    //create new
    public void add(){
        if(attendanceManager==null){
            System.out.println("Attendance manager is null;");
            return;
        }
        String empID;
        while (true) {
            System.out.println("Enter employeeID.");
            empID = sc.nextLine();
            if (employeeManager.findEmployeeByID(empID)==null) {
                System.out.println("ID dose not exist.");
                continue;
            }
            break;
        }
        LocalDate date;
        while (true) {

                System.out.println("Date of record(dd/mm/yyyy): ");
                String dateStr = sc.nextLine();
            try{
                if (!InputChecker.isValidDate(dateStr)) {
                    System.out.println("Invalid date, enter again pls.");
                    continue;
                }
                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    date = LocalDate.parse(dateStr,formatter);
                break;
            }catch(Exception e){
                System.out.println("Anything wrong here :<<");
            }
        }
        AttendanceStatus status;
        while (true) {
            System.out.println("Status: ");
            try {
                status = InputChecker.attendanceStatusCheck(sc.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Just PRESENT OR ABSENT PLS!");
            }catch(Exception e){
                System.out.println("Error :<<");
            }
        }
        int ot;
        while (true) {
            System.out.println("Over Time: ");
            try {
                ot = Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Enter real number only!");
            }
        }

        AttendanceRecord attendanceRecord = new AttendanceRecord(empID, date, ot, status);
        System.out.println("[1] Save     [2] Cancel");
        int confirm = InputChecker.confirm(sc);
        boolean check;
         if (confirm==1){
            check = attendanceManager.addRecord(attendanceRecord);
        }else {
            return;
        }
        
        if (check) {
            System.out.println("Attendance record has added successfully .");
        }else{
            System.out.println("Attendance was exist.");
        }



    }

    public void viewAll(){
        if (this.attendanceManager == null) {
        System.out.println("Attendance Manager dose not exist.");
        return;
    }
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Enter employee ID: ");
        String id = sc.nextLine();
        if(employeeManager.findEmployeeByID(id)==null){
            System.out.println("ID dose not exist!");
            return;
        }
        int month;
        int year;
        while (true) {
            try {
                System.out.println("Month: ");
                month = Integer.parseInt(sc.nextLine());
                System.out.println("Year: ");
                year = Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
        List<AttendanceRecord> list = attendanceManager.getAttendanceByMonth(id, month, year);
        if (list==null) {
            System.out.println("Do not have any attendance record.");
            return;
        }
        System.out.println("Employee ID: "+id);
        System.out.println("--------------------------------------");
        System.out.println("|    Date    |    Status   | Overtime |");
        System.out.println("--------------------------------------");
        for (AttendanceRecord attendanceRecord : list) {
            System.out.printf("|%-12s|%-13s|%-10s|\n",attendanceRecord.getDate().format(dt),attendanceRecord.getStatus(),attendanceRecord.getOT());
        }
        System.out.println("--------------------------------------");
        System.out.println("Press ENTER to return");
        sc.nextLine();

    }
    
}
