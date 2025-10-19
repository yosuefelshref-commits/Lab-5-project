import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class EmployeeRole {
    private ProductDatabase productsDatabase;
    private CustomerProductDatabase customerProductDatabase;

    public EmployeeRole() {
        this.productsDatabase = new ProductDatabase("products.txt");
        this.customerProductDatabase = new CustomerProductDatabase("CustomerProducts.txt");
        productsDatabase.readFromFile();
        customerProductDatabase.readFromFile();
    }


    public void addProduct(String productID, String productName, String manufacturerName, String supplierName, int quantity, float price) {
        Product newProduct = new Product(productID, productName, manufacturerName, supplierName, quantity, price);
        productsDatabase.insertRecord(newProduct);
        productsDatabase.saveToFile();
    }

    public Product[] getListOfProducts() {  
        ArrayList<Product> list = productsDatabase.returnAllRecords();
    return list.toArray(new Product[0]);
    }

    public CustomerProduct[] getListOfPurchasingOperations() {
        ArrayList<CustomerProduct> list = customerProductDatabase.returnAllRecords();
        return list.toArray(new CustomerProduct[0]);
    }

    public boolean purchaseProduct(String customerSSN, String productID, LocalDate purchaseDate) {
        Product product = productsDatabase.getRecord(productID);
        if (product == null) {
            System.out.println("product not found.");
            return false;
        }

        if (product.getQuantity() == 0) {
            System.out.println("There is no available quantity for this product.");
            return false;
        }
        product.setQuantity(product.getQuantity() - 1);
        CustomerProduct purchase = new CustomerProduct(customerSSN, productID, purchaseDate);
        purchase.setPaid(false);
        customerProductDatabase.insertRecord(purchase);
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();

        System.out.println("Your purchase was completed successfully.");
        return true;
    }

    public double returnProduct(String customerSSN, String productID, LocalDate purchaseDate, LocalDate returnDate) {
        if (returnDate.isBefore(purchaseDate)) {
            return -1;
        }
        long daysBetween = ChronoUnit.DAYS.between(purchaseDate, returnDate);
        if (daysBetween > 14) {
            return -1;
        }
        Product product = productsDatabase.getRecord(productID);
        if (product == null) {
            return -1;
        }
        String key = customerSSN + "," + productID + "," + purchaseDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        CustomerProduct purchase = customerProductDatabase.getRecord(key);
        if (purchase == null) {
            return -1;
        }
        product.setQuantity(product.getQuantity() + 1);
        customerProductDatabase.deleteRecord(key);
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        return product.getPrice();
    }

    public boolean applyPayment(String customerSSN, LocalDate purchaseDate) {
        ArrayList<CustomerProduct> allPurchases = customerProductDatabase.returnAllRecords();


        for (CustomerProduct cp : allPurchases) {
            if (cp.getCustomerSSN().equals(customerSSN) && cp.getPurchaseDate().equals(purchaseDate)) {

                if (!cp.isPaid()) {
                    cp.setPaid(true);
                    customerProductDatabase.saveToFile();
                    System.out.println("Payment has been successfully registered.");
                    return true;
                } else {
                    System.out.println("The process is already paid");
                    return false;
                }
            }
        }

        System.out.println("This process is not found");
        return false;
    }

    public void logout() {
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        System.out.println("all data has been saved successfully. Logging out....");
    }
}
