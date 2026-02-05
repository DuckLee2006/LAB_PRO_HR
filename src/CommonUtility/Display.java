package CommonUtility;

public final class Display {


    private Display() {
    }
    //show menu
    public static void showMainMenu(){
        System.out.println("=========================================");
        System.out.println("       HUMAN RESOURCE MANAGEMENT         ");
        System.out.println("=========================================");
        System.out.println("1. Manage Employees");
        System.out.println("2. Attendance Management");
        System.out.println("3. Salary Management");
        System.out.println("4. Report");
        System.out.println("0. Exit");
        System.out.println("-----------------------------------------");
        System.out.println("Choose an option: ");
    }
    public static void showEmployeeMenu(){
        System.out.println("-------------Manage Employee-------------");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. View All Employee");
        System.out.println("5. Find Employee By ID");
        System.out.println("0. Exit");
        System.out.println("-----------------------------------------");
        System.out.println("Choose an option: ");
    }
    public static void showAttendanceMenu(){
        System.out.println("----------Attendance Management----------");
        System.out.println("1. Record Attendance");
        System.out.println("2. View Attendance History");
        System.out.println("3. Update record");
        System.out.println("0.Exit");
        System.out.println("-----------------------------------------");
        System.out.println("Choose an option: ");
    }

    public static void showSalaryMenu(){
        System.out.println("---------Salary Management---------------");
        System.out.println("1. Calculate Salary");
        System.out.println("2. View Salary.");
        System.out.println("0. Exit");
        System.out.println("-----------------------------------------");
        System.out.println("Choose an option: ");
    }

    public static void showReportMenu() {
        System.out.println("----------------Report--------------");
        System.out.println("1. Employees with Low Attendance");
        System.out.println("2. Highest Paid Employee");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------");
        System.out.println("Choose an option: ");

        //hàm chạy
    }
}
