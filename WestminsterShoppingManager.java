package OOP.design.classImplementation;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
public class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> productList;
    private static final int maxProducts = 50;

    String category;


    //constructor
    public WestminsterShoppingManager() {

        this.productList = new ArrayList<>();
        loadProductsFile();
    }

    @Override
    public void addProduct(Product product) {

        if (productList.size() < maxProducts) {
            productList.add(product);
            System.out.println("Product added successfully");
        } else {
            System.out.println("Maximum limit exceeded,SORRY!");
        }

    }

    @Override
    public void deleteProduct(String productID) {
        Product deletedProduct = null;

        // Loop through the productList to find the product with the specified productID
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                deletedProduct = product;

                // Check if the deleted product is electronics or clothing
                if (deletedProduct instanceof Electronics) {
                    System.out.println("Product deleted successfully. Deleted Product Category: Electronics");
                } else if (deletedProduct instanceof Clothing) {
                    System.out.println("Product deleted successfully. Deleted Product Category: Clothing");
                }

                // Remove the product from the list
                productList.remove(deletedProduct);

                // Display the total number of products left in the system
                System.out.println("Total number of products left: " + productList.size());

                // Exit the loop once the product is found and deleted
                break;
            }
        }

        // Check if the product was not found
        if (deletedProduct == null) {
            System.out.println("Product not found! Try again!");
        }
    }

    @Override
    public void printList() {
        if (productList.isEmpty()) {
            System.out.println("Product list is empty");
        } else {
            // Sort the product list alphabetically based on productID
            productList.sort(Comparator.comparing(Product::getProductID));

            // Loop through the list and print the details of each product.
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    System.out.println("Electronics:");
                    product.displayInfo();
                } else if (product instanceof Clothing) {
                    System.out.println("Clothing:");
                    product.displayInfo();
                }
            }
        }
    }


        // Save product list to a file using FileWriter
        public void saveProductsFile(List<Product> productList) {
            try (PrintWriter writer = new PrintWriter("products.txt")) {
                for (Product product : productList) {
                    if (product instanceof Electronics) {
                        writer.println("Electronics:");
                    } else if (product instanceof Clothing) {
                        writer.println("Clothing:");
                    }
                    writer.println("Product ID: " + product.getProductID());
                    writer.println("Product Name: " + product.getProductName());
                    writer.println("Available Items: " + product.getAvailableItems());
                    writer.println("Price: " + product.getPrice());
                    if (product instanceof Electronics electronics) {
                        writer.println("Brand: " + electronics.getBrand());
                        writer.println("Warranty Period: " + electronics.getWarrantyPeriod());
                    } else if (product instanceof Clothing clothing) {
                        writer.println("Size: " + clothing.getSize());
                        writer.println("Color: " + clothing.getColor());
                    }
                    writer.println(); // Add a blank line to separate products
                }
                System.out.println("Products saved to file successfully");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error saving products to file");
            }
        }
    // Load product list from a file using FileReader
    public List<Product>  loadProductsFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            productList.clear(); // Clear existing products before loading

            String word;
            while ((word = reader.readLine()) != null) {
                if (word.equals("Electronics:") || word.equals("Clothing:")) {
                    // Read category
                    String category = word.equals("Electronics:") ? "Electronics" : "Clothing";

                    // Read common attributes
                    String productID = reader.readLine().substring("Product ID: ".length());
                    String productName = reader.readLine().substring("Product Name: ".length());
                    int availableItems = Integer.parseInt(reader.readLine().substring("Available Items: ".length()));
                    double price = Double.parseDouble(reader.readLine().substring("Price: ".length()));

                    // Create and add product based on category
                    if (category.equals("Electronics")) {
                        String brand = reader.readLine().substring("Brand: ".length());
                        int warrantyPeriod = Integer.parseInt(reader.readLine().substring("Warranty Period: ".length()));
                        productList.add(new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod));
                    } else if (category.equals("Clothing")) {
                        String size = reader.readLine().substring("Size: ".length());
                        String color = reader.readLine().substring("Color: ".length());
                        productList.add(new Clothing(productID, productName, availableItems, price, size, color));
                    }

                    // Read the blank line separating products
                    reader.readLine();
                }
            }

            System.out.println("Products loaded from file successfully");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Error loading products from file");
        }
        return productList;
    }





    //display a list of options
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;

        // do-while loop to make sure that this program at least works once even when the user enters invalid options
        do {
            try {
                System.out.println("--Welcome to Westminster Shopping Menu--" +
                        "\n 1.Add a new product" +
                        "\n 2.Delete a product" +
                        "\n 3.Print the list of products" +
                        "\n 4.Save products to the file" +
                        "\n 5.Login to ShoppingGUI"+
                        "\n 6.Exit" +
                        "\n Enter your option:");
                option = scanner.nextInt();
                scanner.nextLine();

                // switch case to select the action that needs to be performed
                switch (option) {
                    case 1:
                        addNewProduct();
                        break;
                    case 2:
                        deleteNewProduct();
                        break;
                    case 3:
                        printList();
                        break;
                    case 4:
                        saveProductsFile(productList);
                        break;
                    case 5:
                        UserGUI userGUI=new UserGUI();
                        userGUI.showUserGUI();
                        break;
                    case 6:
                        System.out.println("Done shopping! EXIT!");
                        break;
                    default:
                        System.out.println("Invalid option. Try to enter a valid option:");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number:");
                scanner.nextLine(); // Consume the invalid input
                option = 0; // Set option to 0 to continue the loop
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                option = 0; // Set option to 0 to continue the loop
            }
        } while (option != 5);

        scanner.close();
    }


    public void addNewProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert the product:\n Enter 1 for Electronics or 2 for Clothing:");
        int newProductType = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.println("Enter product ID:");
        String productID = scanner.nextLine();

        System.out.println("Enter product name:");
        while (!scanner.hasNext("[a-zA-Z]+")) {
            System.out.println("Invalid input. Please enter a valid product name: ");
            scanner.next();
        }
        String productName = scanner.next();

        System.out.print("Enter available items: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number of available items: ");
            scanner.next();
        }
         int availableItems = scanner.nextInt();



        System.out.print("Enter product price: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid product price: ");
            scanner.next();
        }
        double productPrice = scanner.nextDouble();


        if (newProductType == 1) {
            category="Electronics";

            // Adding electronics
            System.out.print("Enter brand: ");
            String brand = scanner.next();
            System.out.print("Enter warranty period: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid warranty period in months: ");
                scanner.next();
            }
            int warrantyPeriod = scanner.nextInt();

            while (warrantyPeriod <= 0) {
                System.out.println("Invalid input. Warranty period cannot be negative. Please enter a valid number:");
                warrantyPeriod = scanner.nextInt();
            }
            Electronics electronics = new Electronics(productID, productName, availableItems, productPrice, brand, warrantyPeriod);
            addProduct(electronics);
        } else if (newProductType == 2) {
            category="Clothing";
            // Adding clothing
            System.out.print("Enter size [ XS , S , M , L , XL , XXL, ]: ");
            String size = scanner.next();
            String[] validSizes = {"XS", "S", "M", "L", "XL", "XXL"};
            boolean isValidSize = Arrays.asList(validSizes).contains(size.toUpperCase());
            while (!isValidSize) {
                System.out.println("Invalid size. Please enter a valid size (XS, S, M, L, XL, XXL): ");
                size = scanner.next();
                isValidSize = Arrays.asList(validSizes).contains(size.toUpperCase());
            }

            System.out.print("Enter color: ");
            while (!scanner.hasNext("[a-zA-Z]+")) {
                System.out.println("Invalid input. Please enter a valid clothing color: ");
                scanner.next();
            }
            String color = scanner.next();
            Clothing clothing = new Clothing(productID, productName, availableItems, productPrice, size, color);
            addProduct(clothing);
        } else {
            System.out.println("Invalid product type.");
        }
    }
    public void deleteNewProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product ID:");
        String productID = scanner.nextLine();
        deleteProduct(productID);

    }

}
































