package UserInterface;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import CommonUtility.IdGenerator;
import CommonUtility.InputChecker;
import Manager.EmployeeManager;
import Model.Department;
import Model.Employee;
import Model.EmployeeStatus;
import Model.EmployeeType;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;

public class EmployeeMenu {
    private EmployeeManager employeeManager;
    private Map<Integer, Runnable> menu;
    private Scanner sc;
    //constructor
    public EmployeeMenu(EmployeeManager employeeManager, Scanner scanner) {
        this.employeeManager = employeeManager;
        this.menu = new HashMap<>();
        this.sc = scanner;
        menu.put(1,() -> add());
        menu.put(2, () -> update());
        menu.put(3, () -> delete());
        menu.put(4, () -> viewAll());
        menu.put(5,() -> findEmployeeByID());


    }
    //setter
    public void run() {
        while (true) {
            Display.showEmployeeMenu();
            
            int choose;
            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (choose == 0) {
                break; 
            }

            Runnable r = menu.get(choose);
            if (r == null) {
                System.out.println("Invalid input (1-4 only).");
            } else {
                r.run();
            }
        }
    }

    

    public void add(){
        System.out.println("Full Name: ");
        String name = InputChecker.inputName(sc);
        Department department = InputChecker.inputDepartment(sc);
        System.out.println("Job title: ");
        String job = sc.nextLine();
        System.out.println("Employee Type: ");
        EmployeeType type= InputChecker.inputEmployeeType(sc);
        System.out.println("Start Date: ");
        LocalDate starDate= InputChecker.inputDate(sc);
        System.out.println("Status.");
        EmployeeStatus status= InputChecker.inputEmployeeStatus(sc);
        System.out.println("basic Salary: ");
        double basicSalary = InputChecker.inputSalary(sc);
        String id = IdGenerator.generateEmployeeId(type, department);
        Employee emp;
            switch (type) {
                case FULL_TIME:
                    emp = new FullTimeEmployee(id, name, status, department, starDate, job, basicSalary);
                    break;
                case PART_TIME:
                    emp = new PartTimeEmployee(id, name, status, department, starDate, job, basicSalary);
                    break;
                default:
                throw new IllegalArgumentException("Invalid employee type");
            }
        System.out.println("-----ADD EMPLOYEE-----");
        System.out.println(emp.toString());
        System.out.println("[1] Save     [2] Cancel");
        int confirm =InputChecker.confirm(sc);

        if (confirm==1){
            employeeManager.addEmployee(emp);
            System.out.println("Employee added successfully.");
        }else {
            return;
        }

    }

    public void update(){
        System.out.print("Enter Employee ID to update: ");
        String input = sc.nextLine();
        Employee oldEmp = employeeManager.findEmployeeByID(input);
        if (oldEmp==null) {
            System.out.println("Employee dose not exist.");
            return;
        }else{
            System.out.println("");
            System.out.println(oldEmp.toString());
        
        }
        
        String id = oldEmp.getEmployeeID();
        LocalDate starDate = oldEmp.getStartDate();
        String blank = "^\\s*$";
        System.out.println("Leave blank to skip any field don't need to update");
        System.out.println("Full Name: ");
        String name;
        while (true) {
            name = sc.nextLine();
            if (name.matches(blank)) {
                name= oldEmp.getEmployeeName();
                break;
            }
            if (InputChecker.nameCheck(name)) {
                break;
            }else{
                System.out.println("Invalid name, enter again pls!");
            }
        }
        Department department;
        while (true) {
            System.out.println("Department (HR,IT, ACCOUNTING, SALES, MARKETING): ");
            try {
                String deptStr= sc.nextLine();
                if (deptStr.matches(blank)) {
                department = oldEmp.getDepartment();
                break;
                }
                department = InputChecker.departmentCheck(deptStr);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid department.");
            }catch (Exception e){
                System.out.println("uhhh... anything wrong here.");
            }
        }
        String job;
        while (true) {
            try {
                System.out.println("Job title: ");
                String jobStr= sc.nextLine();
                if (jobStr.matches(blank)){
                    job = oldEmp.getJobTitle();
                    break;
                }else{
                    job = jobStr;
                    break;
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("yo bro.. enter again pls!");
            }
         } 

        EmployeeType type;
        while (true) {
            try{
                System.out.println("Type(Full-time, Part-time):");
                String typeStr= sc.nextLine();
                if (typeStr.matches(blank)) {
                type = oldEmp.getType();
                break;
                }
                type = InputChecker.employeeTypeCheck(typeStr);
                break;
            }catch(IllegalArgumentException ie){
                System.out.println("enter again pls");
            }
        }
        EmployeeStatus status;
        while (true) {
            try {
                System.out.println("Status.");
                String statusStr= sc.nextLine();
                if (statusStr.matches(blank)) {
                    status = oldEmp.getStatus();
                    break;
                }
                status = InputChecker.employeeStatusCheck(statusStr);
                break;
            } catch (IllegalArgumentException ie ) {
                System.out.println("ACTIVE OR RETIRED ONLY!!");
            }catch(Exception e){
                System.out.println("Again pls!");
            }
        }
        double basicSalary;
        while (true) {
            try {
                System.out.println("basicSalary: ");
                String basicString= sc.nextLine();
                if (basicString.matches(blank)) {
                basicSalary = oldEmp.getBasicSalary();
                break;
                }
                basicSalary= Double.parseDouble(basicString);
                break;
            } catch (Exception e) {
                System.out.println("enter again, pls!");
            }
        }   Employee newEmp;
            switch (type) {
                case FULL_TIME:
                    newEmp = new FullTimeEmployee(id, name, status, department, starDate, job, basicSalary);
                    break;
                case PART_TIME:
                    newEmp = new PartTimeEmployee(id, name, status, department, starDate, job, basicSalary);
                    break;
                default:
                throw new IllegalArgumentException("Invalid employee type");
            }
        
        System.out.println("-----Update EMPLOYEE-----");
        System.out.println(newEmp.toString());
        System.out.println("[1] Update     [2] Cancel");    

        int confirm =InputChecker.confirm(sc);
        if (confirm==1){
            employeeManager.updateEmployee(newEmp);
            System.out.println("Employee update successfully.");
        }else return;

    }

    public void delete(){
        System.out.println("Enter id to delete.");
        String id = sc.nextLine().trim();
        if (employeeManager.findEmployeeByID(id)==null){
            System.out.println("ID does not exist.");
            return;
        }
        System.out.println("The employee with ID: "+id+" will be deleted.");
        System.out.println("[1]YES    [2]NO");

        int confirm =InputChecker.confirm(sc);

        if (confirm==1){
            employeeManager.deleteEmployee(id);
            System.out.println("Employee was deleted.");
        }else if (confirm==2) {
            return;
        }

    }
    
    public void viewAll(){
        DecimalFormat df = new DecimalFormat("#,###");
        List<Employee> comp = employeeManager.getAllEmployee();
        if (comp.isEmpty()) {
            System.out.println("Don't have any employee here.");
            return;
        }
        System.out.println("-----------------------------------------Employee List--------------------------------------------");
        System.out.println("     ID     |               NAME               | DEPARTMENT |     JOB TITLE     |      SALARY      |");
        // System.out.printf("%-8s|%-34s|%-12s|%-19s|%-18s");
        //8/ 34 /12/19/18
        for (Employee employee : comp) {
            System.out.printf("%-12s|%-34s|%-12s|%-19s|%-18s|\n",employee.getEmployeeID(),employee.getEmployeeName(),employee.getDepartment(),employee.getJobTitle(),df.format(employee.getBasicSalary()));
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("Press Enter to return");
        sc.nextLine();
    }

    public void findEmployeeByID(){
        System.out.println("Enter ID: ");
        String id = sc.nextLine();
        Employee emp = employeeManager.findEmployeeByID(id);
        if (emp==null) {
            System.out.println("ID does not exist.");
            return;
        }
        System.out.println(emp.toString());

    }
}
