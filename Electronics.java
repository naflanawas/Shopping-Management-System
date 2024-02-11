package OOP.design.classImplementation;

public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;

    //constructor
    public Electronics(String productID, String productName, int availableItems, double price,String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    @Override
    public String toString() {
        return
                 brand + " " + warrantyPeriod;
    }

    @Override
    public void displayInfo() {
        System.out.println("Product ID: " + getProductID());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Available Items: " + getAvailableItems());
        System.out.println("Price: " + getPrice());
        System.out.println("Brand: " + brand);
        System.out.println("Warranty Period: " + warrantyPeriod);
        System.out.println();
    }
}
