package OOP.design.classImplementation;

public  abstract class Product {
    protected String productID;
    protected String productName;
    protected int availableItems;
    protected double price;


    //constructor
    public Product(String productID, String productName, int availableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    //getters and setters
    public String getProductID() {

        return productID;
    }

    public void setProductID(String productID) {

        this.productID = productID;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(String productName) {

        this.productName = productName;
    }

    public int getAvailableItems() {

        return availableItems;
    }

    public void setAvailableItems(int availableItems) {

        this.availableItems = availableItems;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    @Override
    public String toString() {
        return "Product ID: " + productID +
                ", Product Name: " + productName +
                ", Available Items: " + availableItems +
                ", Price: " + price;
    }

    public abstract void displayInfo();

}