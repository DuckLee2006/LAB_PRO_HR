package UserInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import CommonUtility.Display;
import Manager.EmployeeManager;
import Manager.SalaryManager;

public class SalaryMenu {
    private EmployeeManager employeeManager;
    private SalaryManager salaryManager;
    private Scanner sc;
    private Map<Integer, Runnable> menu;

      //constructor
    public SalaryMenu(EmployeeManager employeeManager,SalaryManager salaryManager, Scanner sc) {
        this.employeeManager = employeeManager;
        this.salaryManager = salaryManager;
        this.sc = sc;
        this.menu = new HashMap<>();
        menu.put(1,() -> calculateSalary());
        
    }
    //run
    public void run(){
        while (true) {
            Display.showSalaryMenu();
            
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
                System.out.println("Invalid input (0-1 only).");
            } else {
                r.run();
            }
        }
    }
    //tính lương
    public void calculateSalary(){
        System.out.println("Enter employee ID: ");
        String id;
        while (true) {
          id = sc.nextLine();
          if(employeeManager.findEmployeeByID(id)==null){
            System.out.println("ID does not exist!");
            continue;
          }
          break;
        }
        int month, year;
        while (true) {
            try {
                System.out.println("Month: ");
                month = Integer.parseInt(sc.nextLine());
                if (month<1||month>12) {
                    System.out.println("MONTH 1-12 PLS");
                    continue;
                }
                System.out.println("Year: ");
                year = Integer.parseInt(sc.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }

        boolean createMonthSalaryRecord = salaryManager.createMonthSalaryRecord(employeeManager.findEmployeeByID(id), month, year);
        if(createMonthSalaryRecord==false){
             System.out.println("Salary Record can't be created.");
             return;
        }
        
        System.out.println("------------CALCULATE SALARY-------------");
        System.out.println("Employee ID: "+id);
        System.out.println("Month / Year: "+ month+"/"+year);
        System.out.println(salaryManager.getSalaryByMonth(id, month, year).toString());
    }



  
    
}
