package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import CommonUtility.IdGenerator;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
import Manager.SalaryManager;
import Model.AttendanceRecord;
import Model.AttendanceStatus;
import Model.Department;
import Model.Employee;
import Model.EmployeeStatus;
import Model.EmployeeType;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import UserInterface.MenuConsole;


public class Tester {
    public static void main(String[] args) {
        
        EmployeeManager employeeManager = testEmployee();
        AttendanceManager attendanceManager = testAttendance(employeeManager);
        SalaryManager salaryManager = new SalaryManager(attendanceManager);
        MenuConsole menuConsole = new MenuConsole(employeeManager,attendanceManager, salaryManager);
        menuConsole.run();
    }

    public static EmployeeManager testEmployee(){
        EmployeeManager manager = new EmployeeManager();

        for (int i = 1; i <= 10; i++) {
            // Giả định các giá trị mẫu
            EmployeeType type = (i % 2 == 0) ? EmployeeType.FULL_TIME : EmployeeType.PART_TIME;
            Department dept = Department.IT;
            String id = IdGenerator.generateEmployeeId(type, dept);
            
            Employee emp;
            if (type == EmployeeType.FULL_TIME) {
                emp = new FullTimeEmployee(id, "Employee " + i, EmployeeStatus.ACTIVE, 
                                           dept, LocalDate.now(), "Developer"+i, 1000.0 + (i * 1000000));
            } else {
                emp = new PartTimeEmployee(id, "Employee " + i, EmployeeStatus.ACTIVE, 
                                           dept, LocalDate.now(), "Collaborator"+i, 500.0 + (i * 500000));
            }

            manager.addEmployee(emp);
        }

        for (Employee employee : manager.getAllEmployee()) {
            System.out.println("--------");
            System.out.println(employee);
        }
        return manager;
    }

    public static AttendanceManager testAttendance(EmployeeManager empManager) {
        AttendanceManager attManager = new AttendanceManager(empManager);
        List<Employee> listEmp = empManager.getAllEmployee();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n--- AUTO GENERATING 10 ATTENDANCE RECORDS FOR EACh EMP---");
        System.out.printf("|%-12s|%-13s|%-10s|%-5s|\n", "Emp ID", "Date", "Status", "OT");
        System.out.println("---------------------------------------------------------");

        for (Employee emp : listEmp) {
            // Lấy ID từ nhân viên đã tạo
            for (int i = 0; i < 10; i++) {
                
            String empID = emp.getEmployeeID(); 
            LocalDate date = LocalDate.now().plusDays(i); // Lấy ngày hiện tại +i
            int ot = (i%2==0) ? 2 : 1; 
            AttendanceStatus status;
            if (i%2==0) {
                status = AttendanceStatus.ABSENT;
            }else{
                status = AttendanceStatus.PRESENT; 
             }

            // Tạo record và add vào manager
            AttendanceRecord record = new AttendanceRecord(empID, date, ot, status);
            attManager.addRecord(record);
            System.out.printf("|%-12s|%-13s|%-10s|%-5d|\n", 
                empID, 
                date.format(dt), 
                status, 
                ot);
            }

            // In ra màn hình để kiểm tra bằng format bạn đã cung cấp
            
        }
        return attManager;
    }
}
