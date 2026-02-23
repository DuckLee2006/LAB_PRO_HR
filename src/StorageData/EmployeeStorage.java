package StorageData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.Department;
import Model.Employee;
import Model.EmployeeStatus;
import Model.EmployeeType;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;

public class EmployeeStorage {
    private final String employeeFileName;

    public EmployeeStorage(String employeeFileName) {
        this.employeeFileName = employeeFileName;
    }
    //save employee data to file
    public void saveEmployeesToFile(List<Employee> employees) {

        if (employees == null || employees.isEmpty()) {
            System.out.println("No employee data to save.");
            return;
        }

        File tempFile = new File(employeeFileName + ".tmp");
        File realFile = new File(employeeFileName);

        // write to a temp file first, then move it after writer is closed
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, false))) {

            for (Employee employee : employees) {

                if (employee == null) continue; // bỏ qua null

                String line = String.join("|",
                    String.valueOf(employee.getType()),
                    String.valueOf(employee.getEmployeeID()),
                    String.valueOf(employee.getEmployeeName()),
                    String.valueOf(employee.getStatus()),
                    String.valueOf(employee.getDepartment()),
                    String.valueOf(employee.getStartDate()),
                    String.valueOf(employee.getJobTitle()),
                    String.valueOf(employee.getBasicSalary())
                );

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
            return;
        }

        // now the writer is closed; attempt to replace the real file with the temp file
        try {
            Files.move(
                tempFile.toPath(),
                realFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            );
            System.out.println("Employees saved successfully.");
        } catch (IOException e) {
            System.out.println("Move failed: " + e.getMessage());
        }
    }
    //load employee data from file
    public List<Employee> loadEmployeesFromFile() {
       List<Employee> employees = new ArrayList<>();
       File file = new File(employeeFileName);
        if (!file.exists()) {
            System.out.println("Employee file not found: " + employeeFileName);
            return employees;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] parts = line.split("\\|");
                if (parts.length != 8) continue;

                EmployeeType type = EmployeeType.valueOf(parts[0]);
                String employeeID = parts[1];
                String employeeName = parts[2];
                EmployeeStatus status = EmployeeStatus.valueOf(parts[3]);
                Department department = Department.valueOf(parts[4]);
                LocalDate startDate = LocalDate.parse(parts[5]);
                String jobTitle = parts[6];
                double basicSalary = Double.parseDouble(parts[7]);
                Employee employee;
                switch (type) {
                    case FULL_TIME:
                        employee = new FullTimeEmployee(employeeID, employeeName, status, department, startDate, jobTitle, basicSalary);
                        break;
                    case PART_TIME:
                        employee = new PartTimeEmployee(employeeID, employeeName, status, department, startDate, jobTitle, basicSalary);
                        break;
                    default:
                        continue;
                }

                employees.add(employee);

            } catch (Exception e) {
                System.out.println("Skipped invalid line: " + line);
            }
        }
        } catch (IOException ioExceptione) {
            System.out.println("Load failed: " + ioExceptione.getMessage());
        }
        return employees;
    }
    
}