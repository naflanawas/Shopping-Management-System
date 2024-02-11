package OOP.design.classImplementation;

public class Clothing extends Product {
    // instance variables
    private String size;
    private String color;

    // constructor
    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    // getters and setters
    public String getSize() {

        return size;
    }

    public void setSize(String size) {

        this.size = size;
    }

    public String getColor() {

        return color;
    }

    public void setColor(String color) {

        this.color = color;
    }

    @Override
    public String toString() {
        return
                size + " " + color;
    }

    @Override
    public void displayInfo() {
        System.out.println("Product ID: " + getProductID());
        System.out.println("Product Name: " + getProductName());
        System.out.println("Available Items: " + getAvailableItems());
        System.out.println("Price: " + getPrice());
        System.out.println("Size: " + size);
        System.out.println("Color: " + color);
        System.out.println();


    }
}
