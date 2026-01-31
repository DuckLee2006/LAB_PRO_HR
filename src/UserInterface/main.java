package UserInterface;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CommonUtility.IdGenerator;
import Manager.AttendanceManager;
import Manager.EmployeeManager;
import Manager.Report;
import Manager.SalaryManager;
import Model.AttendanceRecord;
import Model.AttendanceStatus;
import Model.Department;
import Model.Employee;
import Model.EmployeeStatus;
import Model.EmployeeType;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import Model.SalaryRecord;

public class main {
    public static void main(String[] args) {
        
   
    Employee emp1 = new PartTimeEmployee("1111", "hoàng", EmployeeStatus.ACTIVE,Department.HR, LocalDate.of(2025, 1, 1), "làm hr", 12000000) ;
    Employee emp2 = new PartTimeEmployee("2222", "long", EmployeeStatus.RETIRED,Department.HR, LocalDate.of(2025, 1, 2), "làm hr 2", 12000000) ;
    Employee emp3 = new FullTimeEmployee("333", "hoàng", EmployeeStatus.ACTIVE,Department.HR, LocalDate.of(2025, 1, 1), "làm hr", 12000000) ;
    Map<String, Employee> comp = new HashMap<>();
    EmployeeManager employeeManager = new EmployeeManager(comp);
    employeeManager.addEmployee(emp1);
    employeeManager.addEmployee(emp2);
    employeeManager.addEmployee(emp3);
    System.out.println("add");
    List<Employee> emplist = employeeManager.getAllEmployee();
    for (Employee employee : emplist) {
        System.out.println(employee);
    }
    System.out.println();

    //update
    System.out.println("update");
    Employee emptest = new FullTimeEmployee("333", "hoàng test", EmployeeStatus.ACTIVE,Department.HR, LocalDate.of(2025, 1, 1), "làm hr", 13000000) ;
    boolean update = employeeManager.updateEmployee(emptest);
    for (Employee employee : emplist) {
        System.out.println(employee);
    }

    AttendanceRecord att1 = new AttendanceRecord("1111", LocalDate.of(2025, 1, 2), 3, AttendanceStatus.ABSENT);
    AttendanceRecord att2 = new AttendanceRecord("1111", LocalDate.of(2025, 1, 3), 3, AttendanceStatus.ABSENT);
    AttendanceRecord att3 = new AttendanceRecord("1111", LocalDate.of(2025, 2, 4), 3, AttendanceStatus.ABSENT);
    AttendanceRecord att4 = new AttendanceRecord("1111", LocalDate.of(2025, 1, 1), 3, AttendanceStatus.PRESENT);
    AttendanceRecord att5 = new AttendanceRecord("1111", LocalDate.of(2025, 1, 5), 3, AttendanceStatus.PRESENT);
    Map<String, List<AttendanceRecord>> emp1att = new HashMap<>();
    AttendanceManager attMana = new AttendanceManager(emp1att, employeeManager);
    System.out.println("them att1");
    System.out.println( attMana.addRecord(att1));
    System.out.println("them att2");
    System.out.println( attMana.addRecord(att2));
    System.out.println("them att3");
    System.out.println( attMana.addRecord(att3));
    System.out.println("them att4(trùng)");
    System.out.println( attMana.addRecord(att4));
    attMana.addRecord(att5);
    System.out.println(emp1att);
    //test lấy att;
    System.out.println("attentdance trong tháng 1");
    System.out.println(attMana.getAttendanceByMonth("1111", 1, 2025));
    System.out.println("attentdance trong tháng 2");
    System.out.println(attMana.getAttendanceByMonth("1111", 2, 2025));
    //test lấy working, absent, ot
    int working = attMana.getWorkingDay("1111", 1, 2025);
    int absent = attMana.getAbsentDay("1111", 1, 2025);
    int ot = attMana.getOTByMonth("1111", 1, 2025);
    System.out.println("working: "+working+" absent: "+absent+" OT: "+ot);
    //test low attent;
    System.out.println("lowatt");
    List<Employee> lowatt = attMana.isLowAttendance(1, 2025);
    System.out.println(lowatt);

    Map<String, List<SalaryRecord>> salaMana = new HashMap<>();
    SalaryManager salaryManager = new SalaryManager(salaMana,attMana );
    System.out.println("lương emp1");
    salaryManager.createMonthSalaryRecord(emp1, 1, 2025);
    System.out.println(salaryManager.getSalaryByMonth(emp1.getEmployeeID(), 1, 2025));
    System.out.println("nhân viên cao nhất.");
    System.out.println(salaryManager.getHighestEmployee(1, 2025));
    System.out.println("lương tháng 1");
    System.out.println(salaryManager.getSalaryByMonth(emp1.getEmployeeID(), 1, 2025));

    Report rp = new Report(employeeManager, attMana, salaryManager);
    System.out.println("highest paid");
    Employee highEmployee = rp.getHighestPaidEmployee(1, 2025);
    System.out.println("lowAtt");
    System.out.println(rp.getLowAttentdance(1,2025));

    System.out.println(IdGenerator.generateEmployeeId(EmployeeType.FULL_TIME, Department.MARKETING));


 }
}
