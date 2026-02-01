package CommonUtility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import Model.AttendanceRecord;
import Model.AttendanceStatus;
import Model.Department;
import Model.EmployeeStatus;
import Model.EmployeeType;

public final class InputChecker {

    private InputChecker(){

    }
    //id
    public static Boolean idCheck(String id, List<String> allEmployeeID){
        for (String oldId : allEmployeeID) {
            if (id.equals(oldId)) {
                return false;
            }
        }
        return true;
    }
    //name
    public static boolean nameCheck(String name){
        name.trim();
        String ex = "^[a-zA-Z\\s]+$";
        return name!= null && name.matches(ex);
    }
    //attendance
    public static boolean attendanceCheck(AttendanceRecord att, List<AttendanceRecord> list) {
        if (att == null || att.getDate() == null || list == null) {
            throw new IllegalArgumentException("Null Variable!");
        }

        for (AttendanceRecord record : list) {
            if (record.getDate() != null &&
                    att.getDate().equals(record.getDate())) {
                return false;
            }
        }
        return true;
    }
    //dept check
    public static Department departmentCheck(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input is null");
        }

        input = input.trim().toUpperCase();

        switch (input) {
            case "HR": return Department.HR;
            case "IT": return Department.IT;
            case "ACCOUNTING": return Department.ACCOUNTING;
            case "SALES": return Department.SALES;
            case "MARKETING": return Department.MARKETING;
            default:
                throw new IllegalArgumentException("INVALID DEPARTMENT");
        }
    }
    //date
    public static boolean isValidDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    //attendanceStatus
    public static AttendanceStatus attendanceStatusCheck(String input){
        if(input == null){
            throw new IllegalArgumentException("Input is null");
        }
        input=input.trim().toUpperCase();
        switch (input) {
            case "ABSENT":
                return AttendanceStatus.ABSENT;
            case "PRESENT":
                return AttendanceStatus.PRESENT;
        
            default:
                throw new IllegalArgumentException("INVALID STATUS");
        }


    }
    //employeeStatus
    public static EmployeeStatus employeeStatusCheck(String input){
        if(input==null){
            throw new IllegalArgumentException("Input is null");
        }
        input=input.trim().toUpperCase();
        switch (input) {
            case "ACTIVE":
                return EmployeeStatus.ACTIVE;
            case "RETIRED":
                return EmployeeStatus.RETIRED;
        
            default:
                throw new IllegalArgumentException("INVALID STATUS");
        }

    }
    //EMPLOYEETYPE
    public static EmployeeType employeeTypeCheck(String input){
        if (input == null) {
            throw new IllegalArgumentException("Input is null");
        }
        input = input.trim().toUpperCase();
        switch (input) {
            case "FULL-TIME": return EmployeeType.FULL_TIME;
            case "PART-TIME": return EmployeeType.PART_TIME;
            default:
                throw new IllegalArgumentException("INVALID DEPARTMENT");
        }
    }
    //confirm
    public static int confirm(Scanner sc){
        int confirm;
        while (true) {
            try {
                confirm = Integer.parseInt(sc.nextLine());
                if (confirm == 1 || confirm == 2) break;
                System.out.println("1 or 2 only pls!");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }

        if (confirm==1){
            return 1;
        }else return 2;
    }
}
