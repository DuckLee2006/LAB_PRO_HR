package UserInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import BussinessRule.CannotCreateSalaryRecord;
import BussinessRule.InactiveEmployee;
import CommonUtility.Display;
import Manager.EmployeeManager;
import Manager.SalaryManager;
import Model.Employee;
import Model.SalaryRecord;
import StorageData.SalaryStorage;

public class SalaryMenu {
    private EmployeeManager employeeManager;
    private SalaryManager salaryManager;
    private Scanner sc;
    private Map<Integer, Runnable> menu;
    private SalaryStorage salaryStorage;

      //constructor
    public SalaryMenu(EmployeeManager employeeManager,SalaryManager salaryManager, Scanner sc, String salaryFileName) {
        this.employeeManager = employeeManager;
        this.salaryManager = salaryManager;
        this.sc = sc;
        this.salaryStorage = new SalaryStorage(salaryFileName);
        this.menu = new HashMap<>();
        menu.put(1,() -> calculateSalary());
        menu.put(2,() -> viewSalary());
        
    }
    //run
    public void run(){
        boolean haveChange = false;
        while (true) {
            Display.showSalaryMenu();
            
            int choose;
            try {
                choose = Integer.parseInt(sc.nextLine());
                if (choose ==1) {
                    haveChange = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (choose == 0) {
                if (haveChange) {
                    salaryStorage.saveSalariesToFile(salaryManager.getAllSalaries());
                    System.out.println("data saved. Returning to main menu...");
                } else {
                    System.out.println("No changes to save. Returning to main menu...");
                }
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
                if (year < 1000 || year > 9999) {
                    System.out.println("Invalid Year!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
         for (Employee employee : employeeManager.getAllEmployee()) {
            try {
                salaryManager.createMonthSalaryRecord(employee, month, year);
            } catch (InactiveEmployee ie) {
                System.out.println("Error: " + ie.getMessage());
            }catch (CannotCreateSalaryRecord ccsr) {
                System.out.println("Error: " + ccsr.getMessage());
            }
        }

        for (SalaryRecord salaryRecord : salaryManager.getAllSalaries()) {
            if (salaryRecord.getMonth() == month && salaryRecord.getYear() == year) {
                System.out.println("------------CALCULATE SALARY-------------");
                System.out.println("Employee ID: "+salaryRecord.getEmployeeID());
                System.out.println("Month / Year: "+ month+"/"+year);
        System.out.println(salaryManager.getSalaryByMonth(salaryRecord.getEmployeeID(), month, year).toString());
            }
        }
    }

    public void viewSalary(){
        System.out.println("Enter ID to find: ");
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
        SalaryRecord salaryRecord = salaryManager.getSalaryByMonth(id, month, year);
        if (salaryRecord==null) {
            System.out.println("Salary Record does not exist.");
            return;
        }
        System.out.println("----------------------------------------");
        System.out.println(salaryManager.getSalaryByMonth(id, month, year).toString());
    }


  
    
}
