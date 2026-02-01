package test;

import java.time.LocalDate;

import CommonUtility.IdGenerator;
import Manager.EmployeeManager;
import Model.Department;
import Model.Employee;
import Model.EmployeeStatus;
import Model.EmployeeType;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;
import UserInterface.MenuConsole;


public class Tester {
    public static void main(String[] args) {
        MenuConsole menuConsole = new MenuConsole("test");
        menuConsole.setEmployeeManager(testEmployee());
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
}
