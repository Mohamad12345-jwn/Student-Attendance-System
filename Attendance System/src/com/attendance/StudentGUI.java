package com.attendance;

import javax.swing.*;
import java.awt.*;

public class StudentGUI extends JFrame {
    public StudentGUI(Database db) {
        setTitle("Manage Students");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton addStudentButton = new JButton("Add Student");
        JButton removeStudentButton = new JButton("Remove Student");

        add(addStudentButton);
        add(removeStudentButton);

        addStudentButton.addActionListener(e -> {
            try {
                int studentID = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID:"));
                String name = JOptionPane.showInputDialog("Enter Student Name:");
                String className = JOptionPane.showInputDialog("Enter Class:");

                if (name != null && className != null && !name.isEmpty() && !className.isEmpty()) {
                    db.addStudent(studentID, name, className); //
                    JOptionPane.showMessageDialog(null, " Student Added Successfully with ID: " + studentID);
                } else {
                    JOptionPane.showMessageDialog(null, " Please enter valid details.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, " Invalid Student ID! Please enter a number.");
            }
        });

        removeStudentButton.addActionListener(e -> {
            try {
                int studentIDToRemove = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID to Remove:"));
                db.removeStudent(studentIDToRemove);
                JOptionPane.showMessageDialog(null, " Student Removed Successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, " Invalid Student ID!");
            }
        });

        setVisible(true);
    }
}
