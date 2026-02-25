package UserInterface;

public class main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Employee Management System");
        MenuConsole console = new MenuConsole(
            "C:\\Users\\ACER\\Desktop\\lab_pro\\Lab_Hr\\data\\employeeData.txt",
            "C:\\Users\\ACER\\Desktop\\lab_pro\\Lab_Hr\\data\\attendanceData.txt",
            "C:\\Users\\ACER\\Desktop\\lab_pro\\Lab_Hr\\data\\salaryData.txt");
        console.run();
    }
}