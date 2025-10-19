package Data;

import Models.EmployeeUser;

public class EmployeeUserDatabase extends Database<EmployeeUser>
{
    public EmployeeUserDatabase(String fileName)
    {
        super(fileName);
    }

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
    @Override
    protected boolean recordMatchesKey(EmployeeUser record, String key) {
        return record.getSearchKey().equals(key);
    }

    @Override
    protected String toLine(EmployeeUser record) {
        return record.lineRepresentation();
    }
    public boolean contains(String key) {
        for(EmployeeUser emp : records) {
            if(emp.getSearchKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
