package UserInterface;

import java.time.LocalDate;

import CommonUtility.IdGenerator;
import CommonUtility.InputChecker;
import Model.Department;
import Model.Employee;
import Model.EmployeeStatus;
import Model.EmployeeType;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;

public class main {
    public static void main(String[] args) {
        String name = "ha quoc toan";
        EmployeeStatus employeeStatus= InputChecker.employeeStatusCheck("AcTIVE");
        Department department = InputChecker.departmentCheck("Hr");
        EmployeeType employeeType = InputChecker.employeeTypeCheck("Part-tiMe");
        String id = IdGenerator.generateEmployeeId(employeeType, department);
        LocalDate starDate = LocalDate.of(2025,1, 1);
        String jobTitle = "dev";
        double basicSalary = 12000000;
        Employee emp1;

        switch (employeeType) {
            case FULL_TIME:
                emp1 = new FullTimeEmployee(id, name, employeeStatus, department, starDate, jobTitle, basicSalary);
                break;
            case PART_TIME:
                emp1 = new PartTimeEmployee(id, name, employeeStatus, department, starDate, jobTitle, basicSalary);
                break;
            default:
                throw new IllegalArgumentException("Invalid employee type");
        }

        





 }
}
