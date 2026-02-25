package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BussinessRule.CannotCreateSalaryRecord;
import BussinessRule.InactiveEmployee;
import Model.Employee;
import Model.EmployeeStatus;
import Model.SalaryRecord;

public class SalaryManager {
    private Map<String, List<SalaryRecord>> salaryManager;
    private AttendanceManager attendanceManager;
    //constructor
    public SalaryManager( AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
        this.salaryManager = new HashMap<>();
    }

    public SalaryManager(AttendanceManager attendanceManager, List<SalaryRecord> salaryRecords) {
        this.attendanceManager = attendanceManager;
        this.salaryManager = new HashMap<>();
        for (SalaryRecord record : salaryRecords) {
            if (record == null) continue; // bỏ qua null
            salaryManager.computeIfAbsent(record.getEmployeeID(), k -> new ArrayList<>()).add(record);
        }
    }
    //tính lương
    public boolean createMonthSalaryRecord(Employee emp, int month, int year) throws InactiveEmployee, CannotCreateSalaryRecord{
        if (emp.getStatus()!=EmployeeStatus.ACTIVE) {
            throw new InactiveEmployee("Employee with ID: "+emp.getEmployeeID()+" is not active!");
        }

        if(getSalaryByMonth(emp.getEmployeeID(), month, year)==null){
            List<SalaryRecord> salaryRecords = salaryManager.computeIfAbsent(emp.getEmployeeID(), k -> new ArrayList<>());
            int working = attendanceManager.getWorkingDay(emp.getEmployeeID(), month, year);
            int absent = attendanceManager.getAbsentDay(emp.getEmployeeID(), month, year);
            if (working==0&&absent==0) {
                throw new CannotCreateSalaryRecord("ID: "+emp.getEmployeeID()+" have no attendance record for this month. Salary record can't be created.");
            }
            int ot = attendanceManager.getOTByMonth(emp.getEmployeeID(), month, year);
            SalaryRecord record = new SalaryRecord(emp,month,year,working,ot,absent);
            salaryRecords.add(record);
            return true;
        };
        return false;

    }

    //lương cao nhất.
    public SalaryRecord getHighestEmployee (int month, int year){
        if (salaryManager==null) {
            System.out.println("Salary Manager is null!");
        }
        
        SalaryRecord highest = null;
        for (List<SalaryRecord> list : salaryManager.values()) {
            for (SalaryRecord salaryRecord : list) {
                if(salaryRecord.getMonth()==month && salaryRecord.getYear()==year){
                    if (highest==null||salaryRecord.getTotalSalary()>highest.getTotalSalary()) {
                        highest=salaryRecord;
                    }
                    break;
                }
            }
        }
        return highest;
    }
    //lấy bảng lương;
    public SalaryRecord getSalaryByMonth(String id, int month, int year){
        List<SalaryRecord> list = salaryManager.get(id);
        if (list==null){
            return null;
        };

        for (SalaryRecord salaryRecord : list) {
            if (salaryRecord.getMonth()==month&&salaryRecord.getYear()==year) {
                return salaryRecord;
            }
        }
        return null;

    }

    public List<SalaryRecord> getAllSalaries() {
        List<SalaryRecord> allSalaries = new ArrayList<>();
        for (List<SalaryRecord> records : salaryManager.values()) {
            allSalaries.addAll(records);
        }
        return allSalaries;
    }
}
