package OOP.design.classImplementation;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCart {
    private List<Product> products; //declaration of the list of products

    //constructor-initializing and creating the arraylist by adding the list of products
    public ShoppingCart() {

        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {

        products.add(product);
    }

    public void removeProduct(Product product) {

        products.remove(product);
    }

    public double CalculateTotalCost() {
        double totalCost = 0.0;
        //for loop-to iterate over the list
        for (Product product : products) {
            //to add the total-cost of the products
            totalCost += product.getPrice();
        }
        return totalCost;
    }


    public List<Product> getProducts() {
        // Implement your logic to return the list of products in the shopping cart
        return new ArrayList<>(products);  // Replace this with your actual list of products
    }

}