package OOP.design.classImplementation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class ShoppingGUI extends JFrame {
    private JComboBox<String> productComboBox;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextArea selectedProductDetails;
    private WestminsterShoppingManager manager;
    private ShoppingCart shoppingCart;
    private JFrame shoppingCartFrame;
    private JTable shoppingCartTable;
    private DefaultTableModel shoppingCartTableModel;
    private JLabel totalLabel;
    private JLabel categoryDiscountLabel;
    private JLabel finalTotalLabel;





    public ShoppingGUI() {
        manager = new WestminsterShoppingManager();
        List<Product> productList = manager.loadProductsFile();

        setTitle("Westminster Shopping centre");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top panel with category selection and shopping cart button
        //combo box
        JPanel topPanel = new JPanel(new BorderLayout());
        String[] categories = {"All", "Clothing", "Electronics"};
        productComboBox = new JComboBox<>(categories);
        productComboBox.addActionListener(e -> {
            String selectedCategory = (String) productComboBox.getSelectedItem();
            updateProductTable(selectedCategory);
        });
        //shopping cart button
        JButton shoppingCartButton = new JButton("Shopping Cart");
        topPanel.add(productComboBox, BorderLayout.CENTER);
        topPanel.add(shoppingCartButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);


        // Product table in the center
        tableModel = new DefaultTableModel(new Object[]{"Product ID", "Name", "Category", "Price", "Info"}, 0);
        //reference
        //https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
        productTable = new JTable(tableModel);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = (Product) tableModel.getValueAt(selectedRow, tableModel.getColumnCount() - 1);
                displayProductDetails(selectedProduct);
            }
        });

        int rowHeight = 25;
        int columnWidth = 70;

        productTable.setRowHeight(rowHeight);
        for (int column = 0; column < productTable.getColumnCount(); column++) {
            productTable.getColumnModel().getColumn(column).setMinWidth(columnWidth);
        }

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        //product details and add to cart button
        //product details
        JPanel displayPanel = new JPanel(new BorderLayout());
        selectedProductDetails = new JTextArea();
        selectedProductDetails.setEditable(false);
        selectedProductDetails.setPreferredSize(new Dimension(200, 250));
        //add to cart button
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.setPreferredSize(new Dimension(100, 30)); // Set your desired size
        addToCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = (Product) tableModel.getValueAt(selectedRow, tableModel.getColumnCount() - 1);
                if (selectedProduct.getAvailableItems() <= 0) {
                    JOptionPane.showMessageDialog(this, "This product is out of stock.", "Out of Stock", JOptionPane.ERROR_MESSAGE);
                } else {
                    shoppingCart.addProduct(selectedProduct);
                   // selectedProduct.setAvailableItems(selectedProduct.getAvailableItems() - 1);

                    for (Product product : productList) {
                        if (product.getProductID().equals(selectedProduct.getProductID())) {
                            product.setAvailableItems(product.getAvailableItems() - 1); // Decrease the available items
                            break;
                        }
                    }
                    manager.saveProductsFile(productList);
                    manager.loadProductsFile();
                    JOptionPane.showMessageDialog(this, "Product added to the shopping cart.");

                }
            } else {
                JOptionPane.showMessageDialog(this, "No product selected.");
            }
        });


        displayPanel.add(selectedProductDetails, BorderLayout.CENTER);
        displayPanel.add(addToCartButton, BorderLayout.SOUTH);
        add(displayPanel, BorderLayout.SOUTH);



        //SHOPPING CART GUI
        setLocationRelativeTo(null); //to locate it in center
        setVisible(true);

        shoppingCart = new ShoppingCart();
        shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartTableModel = new DefaultTableModel(new Object[]{"Product", "Quantity", "Price"}, 0);
        shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane shoppingCartScrollPane = new JScrollPane(shoppingCartTable);
        totalLabel = new JLabel();
        categoryDiscountLabel = new JLabel();
        finalTotalLabel = new JLabel();

        // Create the shopping cart GUI
        JPanel shoppingCartPanel = new JPanel(new BorderLayout());
        shoppingCartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        shoppingCartPanel.setPreferredSize(new Dimension(450, 350));
        shoppingCartPanel.add(shoppingCartScrollPane, BorderLayout.CENTER);
        JPanel totalPanel = new JPanel(new GridLayout(0, 1));
        totalPanel.add(totalLabel);
        totalPanel.add(categoryDiscountLabel);
        totalPanel.add(finalTotalLabel);
        shoppingCartPanel.add(totalPanel, BorderLayout.SOUTH);
        shoppingCartFrame.add(shoppingCartPanel);
        shoppingCartFrame.pack();
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add action listener to shoppingCartButton
        shoppingCartButton.addActionListener(e -> showShoppingCart());
        //Reference
        ////https://stackoverflow.com/questions/6644922/jtable-cell-renderer

        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Assuming the product object is in the last column
                Product product = (Product) table.getValueAt(row, table.getColumnCount() - 1);

                if (product.getAvailableItems() <= 3) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }

                return c;
            }
        });

        // Display the GUI
        setLocationRelativeTo(null);
        setVisible(true);


    }
    private void showShoppingCart() {
        // Clear the shopping cart table
        shoppingCartTableModel.setRowCount(0);

        // Add products to the shopping cart table
        List<Product> products = shoppingCart.getProducts();
        for (Product product : products) {
            String productId = product.getProductID();
            String productName = product.getProductName();
            int quantity = 1; // Assuming quantity is always 1 for now
            double price = product.getPrice();



            // Concatenating the details to one in order to display
            String ProductDetail = "";

            if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                ProductDetail = String.format("%s, %s,%s, %s",productId, productName, clothing.getSize(), clothing.getColor());
            } else if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                ProductDetail = String.format("%s, %s, %s, %d",productId, productName, electronics.getBrand(), electronics.getWarrantyPeriod());
            }

            shoppingCartTableModel.addRow(new Object[]{ProductDetail, quantity, price});
        }


        // Calculate total cost
        double totalCost = shoppingCart.CalculateTotalCost();
        totalLabel.setText("Total: $" + totalCost);


        //20% DISCOUNT
        int clothingCount = 0;
        int electronicsCount = 0;
        // Iterate through the productList to count category occurrences
        for (Product product : products) {
            if (product instanceof Clothing) {
                clothingCount++;
            } else if (product instanceof Electronics) {
                electronicsCount++;
            }
        }
        // Calculate category discount
        double categoryDiscount = 0.0;
        // Assuming category discount is 20% for now
        if (clothingCount >= 3 || electronicsCount >= 3) {
            categoryDiscount = (totalCost * 0.2);

            categoryDiscountLabel.setText("Category Discount (20%): -$" + categoryDiscount);
            double finalTotal = totalCost - (categoryDiscount);
            finalTotalLabel.setText("Final Total: $" + finalTotal);

        } else {
            categoryDiscountLabel.setText("");
        }


        // Show the shopping cart frame
        shoppingCartFrame.setVisible(true);


    }



    private void displayProductDetails(Product product) {
        selectedProductDetails.setText("\n     Selected Product - Details\n\n");
        selectedProductDetails.append("     Product ID: " + product.getProductID() + "\n\n");
        selectedProductDetails.append("     Product Name: " + product.getProductName() + "\n\n");
        selectedProductDetails.append("     Price: " + product.getPrice() + "\n\n");

        if (product instanceof Clothing clothing) {
            selectedProductDetails.append("     Size: " + clothing.getSize() + "\n\n");
            selectedProductDetails.append("     Color: " + clothing.getColor() + "\n\n");
        } else if (product instanceof Electronics electronics) {
            selectedProductDetails.append("     Brand: " + electronics.getBrand() + "\n\n");
            selectedProductDetails.append("     Warranty Period: " + electronics.getWarrantyPeriod() + "\n\n");
        }
    }



    private void updateProductTable(String category) {
        tableModel.setRowCount(0); // Clear existing rows

        List<Product> productList = manager.loadProductsFile();

        for (Product product : productList) {
            if ("All".equals(category) || matchesCategory(product, category)) {
                String categoryLabel = getCategoryLabel(product);
                tableModel.addRow(new Object[]{
                        product.getProductID(),
                        product.getProductName(),
                        categoryLabel,
                        product.getPrice(),
                        product // Store the actual product object in the last column for later retrieval
                });
            }
        }
    }

    private boolean matchesCategory(Product product, String category) {
        return ("Clothing".equals(category) && product instanceof Clothing) ||
                ("Electronics".equals(category) && product instanceof Electronics);
    }

    private String getCategoryLabel(Product product) {
        if (product instanceof Clothing) {
            return "Clothing";
        } else if (product instanceof Electronics) {
            return "Electronics";
        }
        return " ";
    }


    public static void main(String[] args) {
        //https://stackoverflow.com/questions/38399466/simple-example-swingutilities

        SwingUtilities.invokeLater(() -> new ShoppingGUI());
    }
}
