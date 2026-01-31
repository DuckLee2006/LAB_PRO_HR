package Manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import Model.AttendanceRecord;
import Model.Employee;

public class AttendanceManager {
    private Map<String, List<AttendanceRecord>> attendanceManager;
    private EmployeeManager employeeManager;
    //constructor
    public AttendanceManager(Map<String, List<AttendanceRecord>> attendanceManager,
                             EmployeeManager employeeManager) {
        this.attendanceManager = attendanceManager;
        this.employeeManager = employeeManager;
    }
    //getter and setters
    public Map<String, List<AttendanceRecord>> getAttendanceManager() {
        return attendanceManager;
    }

    public void setAttendanceManager(Map<String, List<AttendanceRecord>> attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }
    //add attendance record
    public boolean addRecord(AttendanceRecord record) {
        String empID = record.getEmployeeID();

        // 1. kiểm tra id tồn tại
        if (employeeManager.findEmployeeByID(empID)==null) {
            return false;
        }

        // 2. tạo danh sách nếu chưa có.
        List<AttendanceRecord> empRecords = attendanceManager.computeIfAbsent(empID, k -> new ArrayList<>());

        // 3. Check trùng ngày
        for (AttendanceRecord r : empRecords) {
            if (r.getDate().equals(record.getDate())) {
                return false;
            }
        }

        // 4. Add record
        empRecords.add(record);
        return true;
    }
    //get attendance by month
    public List<AttendanceRecord> getAttendanceByMonth (String ID,int month, int year){
        //lấy toàn bộ attendance của nhân viên có ID
        List<AttendanceRecord> all = attendanceManager.get(ID);
        //lọc theo tháng và năm
        List<AttendanceRecord> result = new ArrayList<>();
        if (all == null || all.isEmpty()) {
            return Collections.emptyList();
        }
        for (AttendanceRecord attendanceRecord : all) {
            LocalDate date = attendanceRecord.getDate();
            if (date.getMonthValue()==month && date.getYear()==year) {
                result.add(attendanceRecord);
            }
        }
        return result;
    }
    //get working day by month
    public int getWorkingDay(String id, int month, int year){
        //lấy toàn bộ attendance của nhân viên có ID trong tháng và năm
        List<AttendanceRecord> records = getAttendanceByMonth(id, month, year);
        int workingDays = 0;
        for (AttendanceRecord record : records) {
            //kiểm tra trạng thái
            if (record.getStatus() == Model.AttendanceStatus.PRESENT) {
                workingDays++;
            }
        }
        return workingDays;
    }
    //get OT by month
    public int getOTByMonth(String id, int month, int year){
        List<AttendanceRecord> records = getAttendanceByMonth(id, month, year);
        int totalOT = 0;
        for (AttendanceRecord record : records) {
            totalOT += record.getOT();
        }
        return totalOT;
    }
    //get absent day by month
    public int getAbsentDay(String id, int month, int year){
        List<AttendanceRecord> records = getAttendanceByMonth(id, month, year);
        int absentDays = 0;
        for (AttendanceRecord record : records) {
            if (record.getStatus() == Model.AttendanceStatus.ABSENT) {
                absentDays++;
            }
        }
        return absentDays;
    }

    //low attendance employees
    // public List<String> isLowAttendance(int month, int year){
    //     List<String> lowId = new ArrayList<>();
    //     for (String id : attendanceManager.keySet()) {
    //         if(getAbsentDay(id, month, year)>=3){
    //             lowId.add(id);
    //         }
    //     }

    //     return lowId;

    // }

    public List<Employee> isLowAttendance(int month, int year){
        List<Employee> lowEmp = new ArrayList<>();
        for (Employee emp : employeeManager.getAllEmployee()) {
            if(getAbsentDay(emp.getEmployeeID(), month, year)>=3){
                lowEmp.add(emp);
            }
        }

        return lowEmp;

    }







}
