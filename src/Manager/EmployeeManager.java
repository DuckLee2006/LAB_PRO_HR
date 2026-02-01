package Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.Employee;

public class EmployeeManager {
    Map<String, Employee> comp;

    //constructor
    public EmployeeManager() {
        this.comp = new HashMap<>();;
    }
    //getter and setter

    public Map<String, Employee> getComp() {
        return comp;
    }

    public void setComp(Map<String, Employee> comp) {
        this.comp = comp;
    }
    public boolean deleteEmployee(String ID){
        return comp.remove(ID)!=null;
    }
    //cần truyền 1 employee tham chiếu vào để thêm
    public boolean addEmployee(Employee e){
        if(comp.containsKey(e.getEmployeeID())){
            return false;
        }
        comp.put(e.getEmployeeID(), e);
        return true;
    }
    //cần truyền 1 employee tham chiếu vào để update. Nếu tồn tại thì update, ko thì trả về false
    public boolean updateEmployee(Employee e){
        Employee old = comp.get(e.getEmployeeID());
        if(old == null) return false;

        old.setEmployeeName(e.getEmployeeName());
        old.setStatus(e.getStatus());
        old.setDepartment(e.getDepartment());
        old.setJobTitle(e.getJobTitle());
        old.setType(e.getType());
        old.setBasicSalary(e.getBasicSalary());

        return true;
    }

    //tìm theo employeeID
    public Employee findEmployeeByID(String employeeID){
        return comp.get(employeeID);
    }

    //lấy toàn bộ employee
    public ArrayList<Employee> getAllEmployee(){
        return new ArrayList<Employee>(comp.values());
    }
    //lấy toàn bộ ID
    public ArrayList<String> getAllEmployeeID(){
        return new ArrayList<String>(comp.keySet());
    }







}
