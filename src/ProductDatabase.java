import java.io.*;
import java.util.ArrayList;

public class ProductDatabase {

    // ---------- Fields ----------
    private ArrayList<Product> records;  // holds all the product objects in memory
    private String filename;             // name of the file where data is stored

    // ---------- Constructor ----------
    public ProductDatabase(String filename) {
        this.filename = filename;
        this.records = new ArrayList<>();
    }

    // ---------- 1. Read all products from file ----------
    public void readFromFile() {
        records.clear(); // make sure list is empty before reading
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product p = createRecordFrom(line);
                records.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // ---------- 2. Convert one line from file into a Product object ----------
    public Product createRecordFrom(String line) {
        String[] parts = line.split(",");
        // Convert text data to correct types
        String id = parts[0];
        String name = parts[1];
        String manufacturer = parts[2];
        String supplier = parts[3];
        int quantity = Integer.parseInt(parts[4]);
        float price = Float.parseFloat(parts[5]);
        return new Product(id, name, manufacturer, supplier, quantity, price);
    }

    // ---------- 3. Return all products ----------
    public ArrayList<Product> returnAllRecords() {
        return records;
    }

    // ---------- 4. Check if product exists ----------
    public boolean contains(String key) {
        for (Product p : records) {
            if (p.getSearchKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    // ---------- 5. Get a product by ID ----------
    public Product getRecord(String key) {
        for (Product p : records) {
            if (p.getSearchKey().equals(key)) {
                return p;
            }
        }
        return null; // not found
    }

    // ---------- 6. Add a new product ----------
    public void insertRecord(Product record) {
        if (!contains(record.getSearchKey())) {
            records.add(record);
        } else {
            System.out.println("Product already exists!");
        }
    }

    // ---------- 7. Delete a product ----------
    public void deleteRecord(String key) {
        Product toRemove = null;
        for (Product p : records) {
            if (p.getSearchKey().equals(key)) {
                toRemove = p;
                break;
            }
        }
        if (toRemove != null) {
            records.remove(toRemove);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // ---------- 8. Save all products to file ----------
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Product p : records) {
                writer.println(p.lineRepresentation());
            }
            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
