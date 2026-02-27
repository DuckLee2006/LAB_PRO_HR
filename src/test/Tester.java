 package test;

import Model.Department;

public class Tester {

     public static void main(String[] args) {
        System.out.println("Hello world");
        String id = "01";
        Department department = null;
        for (Department d : Department.values()) {
            if (d.getCode().equals(id)) {
                department = d;
                break;
            }
        }
        System.out.println("Department code: " + department.getCode());
        System.out.println("Department name: " + department.name());
    }
}