package CommonUtility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import Model.AttendanceRecord;
import Model.Department;

public final class InputChecker {

    private InputChecker(){

    }

    public static Boolean idCheck(String id, List<String> allEmployeeID){
        for (String oldId : allEmployeeID) {
            if (id.equals(oldId)) {
                return false;
            }
        }
        return true;
    }

    public static boolean nameCheck(String name){
        String ex = "^[a-zA-Z\\s]+$";
        return name!= null && name.matches(ex);
    }

    public static boolean attendanceCheck(AttendanceRecord att, List<AttendanceRecord> list) {
        if (att == null || att.getDate() == null || list == null) {
            System.out.println("Null Variable!");
            return false;
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

        input = input.trim();

        switch (input) {
            case "1": return Department.HR;
            case "2": return Department.IT;
            case "3": return Department.ACCOUNTING;
            case "4": return Department.SALES;
            case "5": return Department.MARKETING;
            default:
                throw new IllegalArgumentException("Department must be 1-5");
        }
    }

    public static boolean isValidDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
