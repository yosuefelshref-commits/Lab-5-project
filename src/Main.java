import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create the AdminRole (this automatically loads the Employees.txt file)
            AdminRole admin = new AdminRole();

            // 1️⃣ Print all employees currently in the file
            System.out.println("📋 Current Employees:");
            for (EmployeeUser e : admin.getListOfEmployees()) {
                System.out.println(e.lineRepresentation());
            }

            // 2️⃣ Add a new employee
            System.out.println("\n➕ Adding new employee...");
            admin.addEmployee("E1005", "Rozan", "Rozan@gmail.com", "5th Settlement", "01040040040");

            // 3️⃣ Print all employees again
            System.out.println("\n📋 Employees after adding:");
            for (EmployeeUser e : admin.getListOfEmployees()) {
                System.out.println(e.lineRepresentation());
            }

            // 4️⃣ Remove an employee
            System.out.println("\n❌ Removing employee:");
            admin.removeEmployee("E1004");

            // 5️⃣ Show employees again
            System.out.println("\n📋 Employees after removal:");
            for (EmployeeUser e : admin.getListOfEmployees()) {
                System.out.println(e.lineRepresentation());
            }

            // 6️⃣ Logout (save all changes)
            admin.logout();
            System.out.println("\n✅ All changes saved to Employees.txt");

        } catch (IOException e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }
}
