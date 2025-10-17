import java.util.*;
import java.io.*;

public class EmployeeUserDatabase
{
    private ArrayList<EmployeeUser> records;
    private String fileName;

    public EmployeeUserDatabase(String fileName)
    {
        this.fileName = fileName;
        records = new ArrayList<>();
    }

    public void setFileName(String fileName) {this.fileName = fileName;}
    public String getFileName(){return this.fileName;}

    public EmployeeUser createRecordFrom(String line)
    {
        String [] parts = line.split(",");
        String id = parts[0];
        String name = parts[1];
        String email = parts[2];
        String address = parts[3];
        String phoneNumber = parts[4];
        EmployeeUser x = new EmployeeUser(id , name , email , address , phoneNumber);
        return x;
    }

    public void readFromFile()  throws IOException
    {
        File employeeFile = new File(this.fileName);
        Scanner s = new Scanner(employeeFile);
        while(s.hasNextLine())
        {
            String line = s.nextLine().trim();
            if(line.isEmpty()){continue;}
            EmployeeUser y = createRecordFrom(line);
            records.add(y);
        }
        s.close();
    }

    public ArrayList<EmployeeUser> returnAllRecords()
    {
    return records;
    }

    public boolean contains(String key)
    {
        for(EmployeeUser emp : records)
        {
            if(emp.getSearchKey().equals(key)){return true;}
        }
        return false;
    }

    public EmployeeUser getRecord(String key)
    {
        for(EmployeeUser emp : records)
        {
            if(emp.getSearchKey().equals(key))
                return emp;
        }
        return null;
    }

    public void insertRecord(EmployeeUser record)
    {
        records.add(record);
    }

    public void deleteRecord(String key)
    {
        for(EmployeeUser emp : records)
        {
            if(emp.getSearchKey().equals(key))
            {
                records.remove(emp);
                return;
            }
        }
        System.out.println("Employee not found");
    }

    public void saveToFile() throws IOException
    {
        FileWriter x = new FileWriter(fileName);
        for(EmployeeUser emp : records)
        {
            x.write(emp.lineRepresentation());
            x.write("\n");
        }
        x.close();
    }

}
