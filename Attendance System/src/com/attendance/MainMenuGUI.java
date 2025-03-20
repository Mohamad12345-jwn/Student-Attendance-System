package com.attendance;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    private final Integer studentID;

    public MainMenuGUI(Database db, String role, Integer studentID) {
        this.studentID = studentID;

        setTitle(role + " - Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + role + "!");
        add(welcomeLabel);

        if (role.equals("Admin")) {
            JButton addStudentButton = new JButton("Add Student");
            JButton removeStudentButton = new JButton("Remove Student");
            add(addStudentButton);
            add(removeStudentButton);

            addStudentButton.addActionListener(e -> {
                try {
                    int enteredStudentID = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID:"));
                    String name = JOptionPane.showInputDialog("Enter Student Name:");
                    String className = JOptionPane.showInputDialog("Enter Class:");

                    if (name != null && className != null && !name.isEmpty() && !className.isEmpty()) {
                        db.addStudent(enteredStudentID, name, className);
                        JOptionPane.showMessageDialog(null, " Student Added Successfully!");
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
        }

        if (role.equals("Teacher")) {
            JButton markAttendanceButton = new JButton("Mark Student Attendance");
            JButton viewReportsButton = new JButton("View Attendance Reports");
            add(markAttendanceButton);
            add(viewReportsButton);

            markAttendanceButton.addActionListener(e -> {
                try {
                    int enteredStudentID = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID:"));
                    String todayDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
                    db.markAttendance(enteredStudentID, todayDate, "Present");
                    JOptionPane.showMessageDialog(null, " Attendance marked successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, " Invalid Student ID!");
                }
            });

            viewReportsButton.addActionListener(e -> {
                int enteredStudentID = Integer.parseInt(JOptionPane.showInputDialog("Enter Student ID to View Report:"));
                new StudentAttendanceGUI(db, enteredStudentID);
            });
        }

        if (role.equals("Student")) {
            JButton selfAttendanceButton = new JButton("View My Attendance");
            add(selfAttendanceButton);
            selfAttendanceButton.addActionListener(e -> new StudentAttendanceGUI(db, this.studentID));
        }

        setVisible(true);
    }
}
