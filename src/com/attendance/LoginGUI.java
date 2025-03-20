package com.attendance;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    private Database db;

    public LoginGUI(Database database) {
        this.db = database;
        setTitle("Login - Attendance System");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Role:"));
        String[] roles = {"Admin", "Teacher", "Student"};
        JComboBox<String> roleDropdown = new JComboBox<>(roles);
        add(roleDropdown);

        JTextField userInputField = new JTextField();
        add(new JLabel("Enter ID (Student) / Username (Admin/Teacher):"));
        add(userInputField);

        JPasswordField passwordField = new JPasswordField();
        add(new JLabel("Password (Leave blank for students):"));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        loginButton.addActionListener(e -> {
            String role = roleDropdown.getSelectedItem().toString();
            String input = userInputField.getText();
            String password = new String(passwordField.getPassword());

            if (role.equals("Student")) {
                //
                try {
                    int studentID = Integer.parseInt(input);
                    if (db.isStudentRegistered(studentID)) {
                        JOptionPane.showMessageDialog(null, "✅ Login successful!");
                        dispose();
                        new MainMenuGUI(db, "Student", studentID);
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ Error: Student ID not found! Please register first.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "❌ Invalid Student ID! Please enter a number.");
                }
            } else {
                if (db.loginUser(input, password, role)) {
                    JOptionPane.showMessageDialog(null, "✅ Login successful!");
                    dispose();
                    new MainMenuGUI(db, role, null);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Invalid credentials.");
                }
            }
        });

        setVisible(true);
    }
}
