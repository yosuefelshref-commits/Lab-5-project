package Data;

import Models.Product;

import java.io.*;
import java.util.ArrayList;

public class ProductDatabase extends Database<Product> {

    public ProductDatabase(String filename) {
        super(filename);
    }

    @Override
    public Product createRecordFrom(String line) {
        String[] parts = line.split(",");
        String id = parts[0];
        String name = parts[1];
        String manufacturer = parts[2];
        String supplier = parts[3];
        int quantity = Integer.parseInt(parts[4]);
        float price = Float.parseFloat(parts[5]);
        return new Product(id, name, manufacturer, supplier, quantity, price);
    }
    @Override
    protected boolean recordMatchesKey(Product record, String key) {
        return record.getSearchKey().equals(key);
    }

    @Override
    protected String toLine(Product record) {
        return record.lineRepresentation();
    }

}
