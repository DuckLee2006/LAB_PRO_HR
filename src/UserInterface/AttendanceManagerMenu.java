package UserInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import BussinessRule.InactiveEmployee;
import BussinessRule.RecordAlreadyExist;
import BussinessRule.RecordDayBeforeStart;
import CommonUtility.Display;
import CommonUtility.InputAndChecker;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
import Model.AttendanceRecord;
import Model.AttendanceStatus;
import StorageData.AttendanceStorage;

public class AttendanceManagerMenu {

    private AttendanceManager attendanceManager;
    private EmployeeManager employeeManager;
    private Map<Integer, Runnable> menu;
    private Scanner sc;
    private AttendanceStorage attendanceStorage;
    //constructor
    public AttendanceManagerMenu(AttendanceManager attendanceManager, EmployeeManager employeeManager,Scanner sc, String attendanceFileName) {
         this.attendanceStorage = new AttendanceStorage(attendanceFileName);
        this.attendanceManager = attendanceManager;
        this.employeeManager = employeeManager;
        this.attendanceStorage = new AttendanceStorage(attendanceFileName);
        this.menu = new HashMap<>();
        this.sc = sc;
        menu.put(1, () -> add());
        menu.put(2,() -> viewAll());
        menu.put(3,() -> Update());
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
        boolean haveChange = false;
        while (true) {
            Display.showAttendanceMenu();
            int choose;
            try {
                choose = Integer.parseInt(sc.nextLine());
                if (choose != 0&&choose != 2) {
                    haveChange = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (choose == 0) {
                if (haveChange) {
                    attendanceStorage.saveAttendanceToFile(attendanceManager.getAllAttendanceRecords());
                    System.out.println("Data saved. Returning to main menu...");
                } else {
                    System.out.println("No changes to save. Returning to main menu...");
                }
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
        System.out.println("Date of record(dd/mm/yyyy): ");
        LocalDate date = InputAndChecker.inputDate(sc);
        System.out.println("Status: ");
        AttendanceStatus status = InputAndChecker.inputAttendanceStatus(sc);
        System.out.println("Over Time: ");
        int ot = InputAndChecker.inputOT(sc);
      

        AttendanceRecord attendanceRecord = new AttendanceRecord(empID, date, ot, status);
        System.out.println("[1] Save     [2] Cancel");
        int confirm = InputAndChecker.confirm(sc);
        boolean check=false;
         if (confirm==1){
            try {
                check = attendanceManager.addRecord(attendanceRecord);
            } catch (RecordDayBeforeStart recordDayBeforeStart) {
                System.out.println("Error: " + recordDayBeforeStart.getMessage());
                return;
            }catch (RecordAlreadyExist recordAlreadyExist) {
                System.out.println("Error: " + recordAlreadyExist.getMessage());
                return;
            }catch (InactiveEmployee inactiveEmployee) {
                System.out.println("Error: " + inactiveEmployee.getMessage());
                return;
            }
            
        }else {
            return;
        }
        
        if (check) {
            System.out.println("Attendance record has added successfully .");
        }else{
            System.out.println("Attendance record was not added.");
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

    public void Update(){
        System.out.println("Employee id: ");
        String id = sc.nextLine();
        if(employeeManager.findEmployeeByID(id)==null){
            System.out.println("ID does not exist.");
            return;
        }
        System.out.println("Date: ");
        LocalDate date = InputAndChecker.inputDate(sc);

        AttendanceRecord oldRecord = attendanceManager.find(id, date);
        if(oldRecord==null){
            System.out.println("Record does not exist.");
            return;
        }
        AttendanceStatus newStatus;
        int ot;
      
        while (true) {
            try {
                System.out.println("New Status: ");
                newStatus = InputAndChecker.inputAttendanceStatus(sc);
                System.out.println("OT: ");
                ot = InputAndChecker.inputOT(sc);
                if(newStatus==AttendanceStatus.ABSENT){
                    ot=0;
                }
                break;
            } catch (NumberFormatException ne ) {
                System.out.println("Invalid OT input.");
            }catch(IllegalArgumentException ie){
                System.out.println("Invalid Status.");
            }catch(Exception e){
                System.out.println("Invalid input.");
            }
        }


         System.out.println("-----Update Attendance-----");
        System.out.println("New Status: "+ newStatus+"| new OT: "+ot);
        System.out.println("[1] Update     [2] Cancel");    

        int confirm =InputAndChecker.confirm(sc);
        if (confirm==1){
            oldRecord.setStatus(newStatus);
            oldRecord.setOT(ot);

            System.out.println("Attendance update successfully.");
        }else return;

       


    }
    
}
