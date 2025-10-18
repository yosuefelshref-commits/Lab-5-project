import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerProductDatabase {

    private ArrayList<CustomerProduct> records;
    private String filename;

    public CustomerProductDatabase(String filename) {
        this.filename = filename;
        this.records = new ArrayList<>();
    }

    public void readFromFile() {

        records.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                records.add(creatRecordFrom(line));
            }
        } catch (IOException e) {
            System.out.println("Error Reading file: " + e.getMessage());
        }
    }

    public CustomerProduct creatRecordFrom(String line)
    {
        String [] parts = line.split(",");
        String customerSSN = parts[0];
        String productID = parts[1];
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate purchaseDate = LocalDate.parse(parts[2], formatter);
        boolean paid = Boolean.parseBoolean(parts[3]);

        CustomerProduct record = new CustomerProduct(customerSSN, productID, purchaseDate);
        record.setPaid(paid);
        return record;
    }

    public ArrayList<CustomerProduct> returnAllRecords()
    {
        return records;
    }

    public boolean contains(String key )
    {
        for(CustomerProduct record : records)
        {
            if(record.getSearchKey().equals(key))
            {
                return true;
            }
        }
        return false;
    }

    public CustomerProduct getRecord(String key)
    {
        for(CustomerProduct record : records)
        {
            if(record.getSearchKey().equals(key))
            {
                return record;
            }
        }
        return null;
    }

    public void insertRecord(CustomerProduct record)
    {
        records.add(record);
    }

    public void deleteRecord(String key)
    
    {
        records.removeIf(cp -> cp.getSearchKey().equals(key));
    }

    public void saveToFile()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            for(CustomerProduct record : returnAllRecords()){
                writer.write(record.lineRepresentation());
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Error Writing to file: " + e.getMessage());
        }
    }
}
