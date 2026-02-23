package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import CommonUtility.IdGenerator;
import Model.AttendanceRecord;
import Model.AttendanceStatus;
import Model.Department;
import Model.Employee;
import Model.EmployeeStatus;
import Model.EmployeeType;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import StorageData.AttendanceStorage;
import StorageData.EmployeeStorage;

public final class Generate {

    public static List<Employee> generate20Employees(List<Employee> employees) {
        int numberOfEmployees = employees.size();
        for (int i = 1; i <= 20; i++) {
            
            String name = "Employee " + (numberOfEmployees+i);
            EmployeeType type = (i % 2 == 0) ? EmployeeType.FULL_TIME : EmployeeType.PART_TIME;
            EmployeeStatus status = (i % 10 == 0) ? EmployeeStatus.RETIRED : EmployeeStatus.ACTIVE;
            Department department = Department.values()[i % Department.values().length];
            LocalDate startDate = LocalDate.now().minusDays(i * 10);
            String jobTitle = "Job Title " + ((i % 5) + 1);
            double basicSalary = 30000000;;
            String id;
            int attempts = 0;
            while (true) {
                attempts++;
                if (attempts==500) {
                    System.out.println("Failed to generate unique ID after 500 attempts for employee " + name);
                    return employees; // Return the list even if it's not complete
                }
                id = IdGenerator.generateEmployeeId(type, department);
                boolean isUnique = true;
                for (Employee employee : employees) {
                    if (id.equals(employee.getEmployeeID())) {
                        isUnique = false;
                        break;
                    }
                    
                }
                if (isUnique) {
                    break;
                }
            }
            Employee employee;
            if (type == EmployeeType.FULL_TIME) {
                employee = new FullTimeEmployee(id, name, status, department, startDate, jobTitle, basicSalary);
            } else {
                employee = new PartTimeEmployee(id, name, status, department, startDate, jobTitle, basicSalary);
            }
            employees.add(employee);
        }

        return employees;
    }

    public static List<AttendanceRecord> generateMonthAttendanceRecords(Employee employee, List<AttendanceRecord> attendanceRecords,int month, int year) {
        Random random = new Random();
        for (int i = 1; i <=26; i++) {
            
            int status = random.nextInt(10);
            int ot=0;
            if (1<=status&&status<=5) {
                ot = random.nextInt(5);
            }
            if (status==0) {
                AttendanceRecord record = new AttendanceRecord(employee.getEmployeeID(), LocalDate.of(year, month, i),0, AttendanceStatus.ABSENT);
                attendanceRecords.add(record);
            } else {
                AttendanceRecord record = new AttendanceRecord(employee.getEmployeeID(), LocalDate.of(year, month, i),ot, AttendanceStatus.PRESENT);
                attendanceRecords.add(record);
            }
            
            
        }
       return attendanceRecords;
    }

    public static void main(String[] args) {

        String employeeFileName = "C:\\Users\\ACER\\Desktop\\lab_pro\\Lab_Hr\\data\\employeeData.txt";
        String attendanceFileName = "C:\\Users\\ACER\\Desktop\\lab_pro\\Lab_Hr\\data\\attendanceData.txt";

        // ===== LOAD STORAGE =====
        EmployeeStorage employeeStorage = new EmployeeStorage(employeeFileName);
        AttendanceStorage attendanceStorage = new AttendanceStorage(attendanceFileName);

        List<Employee> currentEmployees = employeeStorage.loadEmployeesFromFile();
        List<AttendanceRecord> currentAttendanceRecords = attendanceStorage.loadAttendanceFromFile();

        System.out.println("Loaded employees: " + currentEmployees.size());
        System.out.println("Loaded attendance records: " + currentAttendanceRecords.size());

        // ===== IF FILE EMPTY → GENERATE INITIAL DATA =====
        if (currentEmployees.isEmpty()) {
            System.out.println("No employees found. Generating new employees...");
            currentEmployees = generate20Employees(new ArrayList<>());
        }

        // ===== GENERATE NEW ATTENDANCE FOR MONTH =====
        int month = 1;
        int year = 2026;

        for (Employee employee : currentEmployees) {
            generateMonthAttendanceRecords(employee, currentAttendanceRecords, month, year);
        }

        System.out.println("Total employees after generation: " + currentEmployees.size());
        System.out.println("Total attendance records after generation: " + currentAttendanceRecords.size());

        // ===== SAVE BACK TO FILE =====
        employeeStorage.saveEmployeesToFile(currentEmployees);
        attendanceStorage.saveAttendanceToFile(currentAttendanceRecords);

        System.out.println("Data successfully saved.");
    }

}
