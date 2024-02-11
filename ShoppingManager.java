package OOP.design.classImplementation;

import java.util.List;

public interface ShoppingManager {
    //initializing the methods
    void addProduct(Product product);
    void deleteProduct(String productID);
    void printList();
    void saveProductsFile(List<Product> productList);

}
