package OOP.design.classImplementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UserGUI extends JFrame {
    private JTextField usernamelabel;
    private JPasswordField passwordlabel;
    private ArrayList<User> userList;

    public UserGUI() {
        // Initialize user list
        userList = loadUserListFromFile(); // Load user list from file

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernamelabel = new JTextField();
        passwordlabel = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });

        // Set layout to BorderLayout
        setLayout(new BorderLayout());

        // Create panels for login form and buttons
        JPanel loginPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel();

        // Add components to the login panel
        loginPanel.add(usernameLabel);
        loginPanel.add(usernamelabel);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordlabel);

        // Add components to the button panel
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        // Add panels to the frame
        add(loginPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setTitle("Login Form");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private ArrayList<User> loadUserListFromFile() {
        ArrayList<User> loadedUserList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("userList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    User loadedUser = new User(username, password);
                    loadedUserList.add(loadedUser);
                }
            }
            System.out.println("User list loaded from file successfully");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading user list from file");
        }

        return loadedUserList;
    }

    private void login() {
        String username = usernamelabel.getText();
        String password = new String(passwordlabel.getPassword());

        // Check if the user exists in the user list
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                openShopGUI();  // Call a method to open the shop GUI
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
    }

    private void openShopGUI() {
        // Replace this with the code to open your shopping GUI
        // For example, you can create a new instance of your shopping GUI class and show it.
        ShoppingGUI shopGUI = new ShoppingGUI();
        shopGUI.setVisible(true);

        // Close the login frame
        dispose();
    }

    private void signUp() {
        String username = usernamelabel.getText();
        String password = new String(passwordlabel.getPassword());

        // Check if the username already exists
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different one.");
                return;
            }
        }

        // Create a new user and add it to the list
        User newUser = new User(username, password);
        userList.add(newUser);

        // Save the updated userList to a text file
        saveUserListToFile();

        JOptionPane.showMessageDialog(this, "Account created successfully!");
    }

    private void saveUserListToFile() {
        try (PrintWriter writer = new PrintWriter("userList.txt")) {
            for (User user : userList) {
                writer.println(user.getUsername() + "," + user.getPassword());
            }
            System.out.println("User list saved to file successfully");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving user list to file");
        }
    }

    public void showUserGUI(){
        UserGUI userGUI=new UserGUI();
        userGUI.setSize(300,150);
        userGUI.setLocationRelativeTo(null);
        userGUI.setVisible(true);
        userGUI.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


}
