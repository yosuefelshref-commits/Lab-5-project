import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public class EmployeeRole {
    private ProductDatabase productsDatabase;
    private CustomerProductDatabase customerProductDatabase;
    public EmployeeRole() {
        this.productsDatabase = new ProductDatabase();
        this.customerProductDatabase = new CustomerProductDatabase();
    }
    public void addProduct(String productID, String productName, String manufacturerName,
                           String supplierName, int quantity) {
        Product newProduct = new Product(productID, productName, manufacturerName, supplierName, quantity);
        productsDatabase.addProduct(newProduct);
        productsDatabase.saveToFile();
    }

    public Product[] getListOfProducts() {
        return productsDatabase.getAllProducts();
    }

    public CustomerProduct[] getListOfPurchasingOperations() {
        return customerProductDatabase.getAllCustomerProducts();
    }

    public boolean purchaseProduct(String customerSSN, String productID, LocalDate purchaseDate) {
        Product product = productsDatabase.findProductByID(productID);
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
        customerProductDatabase.addCustomerProduct(purchase);
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();

        System.out.println("Your purchase was completed successfully.");
        return true;
    }
    public double returnProduct(String customerSSN, String productID,LocalDate purchaseDate, LocalDate returnDate) {
        if (returnDate.isBefore(purchaseDate)) {
            return -1;
        }
        long daysBetween = ChronoUnit.DAYS.between(purchaseDate, returnDate);
        if (daysBetween > 14) {
            return -1;
        }
        Product product = productsDatabase.findProductByID(productID);
        if (product == null) {
            return -1;
        }
        String searchKey = customerSSN + "," + productID + "," + purchaseDate.format(CustomerProduct.DATE_FORMAT);
        CustomerProduct purchase = customerProductDatabase.findCustomerProduct(searchKey);
        if (purchase == null) {
            return -1;
        }
        product.setQuantity(product.getQuantity() + 1);
        customerProductDatabase.removeCustomerProduct(searchKey);
        productsDatabase.saveToFile();
        customerProductDatabase.saveToFile();
        return product.getPrice();
    }
    public boolean applyPayment(String customerSSN, LocalDate purchaseDate) {
        CustomerProduct[] allPurchases = customerProductDatabase.getAllCustomerProducts();

        for (CustomerProduct cp : allPurchases) {
            if (cp.getCustomerSSN().equals(customerSSN) &&
                    cp.getPurchaseDate().equals(purchaseDate)) {

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
