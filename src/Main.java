import Models.CustomerProduct;
import Models.EmployeeUser;
import Models.Product;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("     Welcome to the Store System   ");
        System.out.println("===================================");

        while (true) {
            System.out.println("\nChoose your role:");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = inputInt();

            try {
                switch (choice) {
                    case 1 -> {
                        AdminRole admin = new AdminRole();
                        adminMenu(admin);
                    }
                    case 2 -> {
                        EmployeeRole employee = new EmployeeRole();
                        employeeMenu(employee);
                    }
                    case 0 -> {
                        System.out.println("Exiting program. Goodbye!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------------- ADMIN MENU ----------------
    public static void adminMenu(AdminRole admin) throws IOException {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. View Employees");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int adminChoice = inputInt();

            switch (adminChoice) {
                case 1 -> {
                    String id = inputString("Enter ID: ");
                    String name = inputString("Enter name: ");
                    String email = inputString("Enter email: ");
                    String address = inputString("Enter address: ");
                    String phone = inputString("Enter phone: ");
                    admin.addEmployee(id, name, email, address, phone);
                }
                case 2 -> {
                    String id = inputString("Enter employee ID to remove: ");
                    admin.removeEmployee(id);
                }
                case 3 -> {
                    EmployeeUser[] list = admin.getListOfEmployees();
                    System.out.println("\n--- Employee List ---");
                    for (EmployeeUser emp : list) {
                        System.out.println(emp); // يعتمد على toString() أو lineRepresentation()
                    }
                }
                case 4 -> {
                    admin.logout();
                    System.out.println("Admin logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ---------------- EMPLOYEE MENU ----------------
    public static void employeeMenu(EmployeeRole employee) throws IOException {
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
            int empChoice = inputInt();

            switch (empChoice) {
                case 1 -> {
                    String pid = inputString("Enter Product ID: ");
                    String pname = inputString("Enter Product Name: ");
                    String manuf = inputString("Enter Manufacturer: ");
                    String supp = inputString("Enter Supplier: ");
                    int q = inputInt("Enter Quantity: ");
                    float price = inputFloat("Enter Price: ");
                    employee.addProduct(pid, pname, manuf, supp, q, price);
                }
                case 2 -> {
                    Product[] products = employee.getListOfProducts();
                    System.out.println("\n--- Products ---");
                    for (Product p : products) {
                        System.out.println(p);
                    }
                }
                case 3 -> {
                    String ssn = inputString("Enter Customer SSN: ");
                    String pid = inputString("Enter Product ID: ");
                    LocalDate date = inputDate("Enter Purchase Date (dd-MM-yyyy): ");
                    employee.purchaseProduct(ssn, pid, date);
                }
                case 4 -> {
                    String ssn = inputString("Enter Customer SSN: ");
                    String pid = inputString("Enter Product ID: ");
                    LocalDate pdate = inputDate("Enter Purchase Date (dd-MM-yyyy): ");
                    LocalDate rdate = inputDate("Enter Return Date (dd-MM-yyyy): ");
                    double refund = employee.returnProduct(ssn, pid, pdate, rdate);
                    if (refund == -1) System.out.println("Return not allowed.");
                    else System.out.println("Return accepted. Refund: " + refund);
                }
                case 5 -> {
                    String ssn = inputString("Enter Customer SSN: ");
                    LocalDate date = inputDate("Enter Purchase Date (dd-MM-yyyy): ");
                    employee.applyPayment(ssn, date);
                }
                case 6 -> {
                    CustomerProduct[] ops = employee.getListOfPurchasingOperations();
                    System.out.println("\n--- All Purchases ---");
                    for (CustomerProduct cp : ops) {
                        System.out.println(cp);
                    }
                }
                case 7 -> {
                    employee.logout();
                    System.out.println("Employee logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ---------------- HELPER METHODS ----------------
    private static String inputString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static int inputInt() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Invalid input. Enter a number: ");
        }
        int value = sc.nextInt();
        sc.nextLine(); // consume newline
        return value;
    }

    private static int inputInt(String prompt) {
        System.out.print(prompt);
        return inputInt();
    }

    private static float inputFloat(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextFloat()) {
            sc.next();
            System.out.print("Invalid input. Enter a number: ");
        }
        float value = sc.nextFloat();
        sc.nextLine(); // consume newline
        return value;
    }

    private static LocalDate inputDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(sc.nextLine(), DATE_FORMAT);
            } catch (Exception e) {
                System.out.println("Invalid date format. Try again.");
            }
        }
    }
}
