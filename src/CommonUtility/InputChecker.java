package CommonUtility;

import java.time.DateTimeException;
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
        String ex = "^[a-zA-Z \\s]+$";
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
     public static boolean isValidDate(int day, int month, int year) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
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

    public static boolean isValidOT(int ot){
        if (0<=ot&&ot<=24) {
            return true;
        }
        return false;
    }

    public static boolean isValidSalary(double salayry){
        if (salayry>=0) {
            return true;
        }
        return false;
    }
    public static String nameTransform(String name){
        if (name==null||name.matches("^\\s*$")) {
            return "";
        }
        name = name.trim().replaceAll("\\s+", " ");
        String[] nameArr= name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : nameArr) {
            sb.append(Character.toUpperCase(w.charAt(0))+w.substring(1).toLowerCase()).append(" ");
        }

        return sb.toString().trim();
    }

    public static String inputName(Scanner sc){
        String name;
        while (true) {
            try {
            name = sc.nextLine();
            if (InputChecker.nameCheck(name)) {
                name=InputChecker.nameTransform(name);
                break;
            }else{
                System.out.println("Invalid name, enter again pls!");
            }
            } catch (Exception e) {
                System.out.println("Again :))");
            }
        }

        return name;
    }

    public static Department inputDepartment(Scanner sc){
        Department department;
        while (true) {
            System.out.println("Department (HR,IT, ACCOUNTING, SALES, MARKETING): ");
            try {
                department = InputChecker.departmentCheck(sc.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid department.");
            }catch (Exception e){
                System.out.println("uhhh... anything wrong here.");
            }
        }

        return department;
    }

    public static EmployeeType inputEmployeeType(Scanner sc){
        EmployeeType type;
        while (true) {
            try{
                System.out.println("Type(Full-time, Part-time):");
                type = InputChecker.employeeTypeCheck(sc.nextLine());
                break;
            }catch(IllegalArgumentException ie){
                System.out.println("enter again pls");
            }
        }
        return type;
    }

    public static LocalDate inputDate(Scanner sc){
        LocalDate Date;
        while (true) {
            try {
                System.out.print("(dd/mm/yyyy)");
                String date = sc.nextLine();
                if(InputChecker.isValidDate(date)){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    Date = LocalDate.parse(date,formatter);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Enter again, pls!");
            }
        }
        return Date;
    }

    public static EmployeeStatus inputEmployeeStatus(Scanner sc){
        EmployeeStatus status;
        while (true) {
            try {
                status = InputChecker.employeeStatusCheck(sc.nextLine());
                break;
            } catch (IllegalArgumentException ie ) {
                System.out.println("ACTIVE OR RETIRED ONLY!!");
            }catch(Exception e){
                System.out.println("Again pls!");
            }
        }
        return status;
    }

    public static Double inputSalary(Scanner sc){
        double salary;
        while (true) {
            try {
                salary= Double.parseDouble(sc.nextLine());
                if (!isValidSalary(salary)) {
                    System.out.println("Invalid salary.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("enter again, pls!");
            }
        }
        return salary;
    }

    public static AttendanceStatus inputAttendanceStatus(Scanner sc){
        AttendanceStatus status;
        while (true) {
            try {
                status = InputChecker.attendanceStatusCheck(sc.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Just PRESENT OR ABSENT PLS!");
            }catch(Exception e){
                System.out.println("Error :<<");
            }
        }
        return status;
    }

    public static int inputOT(Scanner sc){
        int ot;
        while (true) {
            try {
                ot = Integer.parseInt(sc.nextLine());
                if (ot>=0&&ot<24) {
                    break;
                }else{
                    System.out.println("Ot must be >=0&<24!");
                }
     
            } catch (Exception e) {
                System.out.println("Enter integer number only!");
            }
        }
        return ot;
    }
}
