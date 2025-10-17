import java.io.*;

public class AdminRole
{
    private EmployeeUserDatabase database;

    public AdminRole() throws IOException
    {
        this.database = new EmployeeUserDatabase("Employees.txt");
        this.database.readFromFile();
    }

    public void addEmployee(String employeeId, String name, String email, String address, String phoneNumber) throws IOException
    {
        if(database.contains(employeeId))
        {
            System.out.println("This employee ID already exists");
            return;
        }
        EmployeeUser x = new EmployeeUser(employeeId, name, email, address, phoneNumber);
        database.insertRecord(x);
        database.saveToFile();
    }

    public EmployeeUser [] getListOfEmployees()
    {
        return database.returnAllRecords().toArray(new EmployeeUser[0]);
    }

    public void removeEmployee (String key) throws IOException
    {
        if(!database.contains(key))
        {
            System.out.println("This id does not exist");
            return;
        }
        database.deleteRecord(key);
        database.saveToFile();
    }

    public void logout() throws IOException
    {
        database.saveToFile();
    }
}
