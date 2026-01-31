package CommonUtility;

import java.util.Random;

import Model.Department;
import Model.EmployeeType;

public final class IdGenerator {


    private IdGenerator() {
    }

    public static String generateEmployeeId(EmployeeType employeeType, Department department) {
        Random rd = new Random();

        return new StringBuilder("EMP")
                .append(employeeType.getCode())
                .append(department.getCode())
                .append(rd.nextInt(9000) + 1000)
                .toString();
    }
}
