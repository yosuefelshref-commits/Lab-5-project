import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("===================================");
        System.out.println("     Welcome to the Store System   ");
        System.out.println("===================================");
        System.out.println("Choose your role:");
        System.out.println("1. Admin");
        System.out.println("2. Employee");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            AdminRole admin = new AdminRole();
            adminMenu(admin, sc);
        } else if (choice == 2) {
            EmployeeRole employee = new EmployeeRole();
            employeeMenu(employee, sc);
        } else {
            System.out.println("Invalid role.");
        }

        sc.close();
    }

    // ---------------- ADMIN MENU ----------------
    public static void adminMenu(AdminRole admin, Scanner sc) throws IOException {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. View Employees");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int adminChoice = sc.nextInt();
            sc.nextLine();

            switch (adminChoice) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();
                    admin.addEmployee(id, name, email, address, phone);
                }
                case 2 -> {
                    System.out.print("Enter employee ID to remove: ");
                    String id = sc.nextLine();
                    admin.removeEmployee(id);
                }
                case 3 -> {
                    EmployeeUser[] list = admin.getListOfEmployees();
                    System.out.println("\n--- Employee List ---");
                    for (EmployeeUser emp : list) {
                        System.out.println(emp.lineRepresentation());
                    }
                }
                case 4 -> {
                    admin.logout();
                    System.out.println("Admin logged out.");
                    return; // exit the admin menu
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ---------------- EMPLOYEE MENU ----------------
    public static void employeeMenu(EmployeeRole employee, Scanner sc) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Purchase Product");
            System.out.println("4. Return Product");
            System.out.println("5. Apply Payment");
            System.out.println("6. View Purchases");
            System.out.println("7. Logout");
            System.out.print("Choose: ");
            int empChoice = sc.nextInt();
            sc.nextLine();

            switch (empChoice) {
                case 1 -> {
                    System.out.print("Enter Product ID: ");
                    String pid = sc.nextLine();
                    System.out.print("Enter Product Name: ");
                    String pname = sc.nextLine();
                    System.out.print("Enter Manufacturer: ");
                    String manuf = sc.nextLine();
                    System.out.print("Enter Supplier: ");
                    String supp = sc.nextLine();
                    System.out.print("Enter Quantity: ");
                    int q = sc.nextInt();
                    System.out.print("Enter Price: ");
                    float price = sc.nextFloat();
                    sc.nextLine();
                    employee.addProduct(pid, pname, manuf, supp, q, price);
                }
                case 2 -> {
                    Product[] products = employee.getListOfProducts();
                    System.out.println("\n--- Products ---");
                    for (Product p : products) {
                        System.out.println(p.lineRepresentation());
                    }
                }
                case 3 -> {
                    System.out.print("Enter Customer SSN: ");
                    String ssn = sc.nextLine();
                    System.out.print("Enter Product ID: ");
                    String pid = sc.nextLine();
                    System.out.print("Enter Purchase Date (dd-MM-yyyy): ");
                    LocalDate date = LocalDate.parse(sc.nextLine(), formatter);
                    employee.purchaseProduct(ssn, pid, date);
                }
                case 4 -> {
                    System.out.print("Enter Customer SSN: ");
                    String ssn = sc.nextLine();
                    System.out.print("Enter Product ID: ");
                    String pid = sc.nextLine();
                    System.out.print("Enter Purchase Date (dd-MM-yyyy): ");
                    LocalDate pdate = LocalDate.parse(sc.nextLine(), formatter);
                    System.out.print("Enter Return Date (dd-MM-yyyy): ");
                    LocalDate rdate = LocalDate.parse(sc.nextLine(), formatter);
                    double refund = employee.returnProduct(ssn, pid, pdate, rdate);
                    if (refund == -1)
                        System.out.println("Return not allowed.");
                    else
                        System.out.println("Return accepted. Refund amount: " + refund);
                }
                case 5 -> {
                    System.out.print("Enter Customer SSN: ");
                    String ssn = sc.nextLine();
                    System.out.print("Enter Purchase Date (dd-MM-yyyy): ");
                    LocalDate date = LocalDate.parse(sc.nextLine(), formatter);
                    employee.applyPayment(ssn, date);
                }
                case 6 -> {
                    CustomerProduct[] ops = employee.getListOfPurchasingOperations();
                    System.out.println("\n--- All Purchases ---");
                    for (CustomerProduct cp : ops) {
                        System.out.println(cp.lineRepresentation());
                    }
                }
                case 7 -> {
                    employee.logout();
                    System.out.println("Employee logged out.");
                    return; // exit employee menu
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
