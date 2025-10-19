package Data;

import Models.CustomerProduct;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerProductDatabase extends Database<CustomerProduct> {

    public CustomerProductDatabase(String filename) {
        super(filename);
    }

    @Override
    public CustomerProduct createRecordFrom(String line)
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

    @Override
    protected boolean recordMatchesKey(CustomerProduct record, String key) {
        return record.getSearchKey().equals(key);
    }

    @Override
    protected String toLine(CustomerProduct record) {
        return record.lineRepresentation();
    }
}
