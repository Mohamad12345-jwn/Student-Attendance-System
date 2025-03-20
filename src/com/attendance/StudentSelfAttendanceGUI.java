package com.attendance;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class StudentSelfAttendanceGUI extends JFrame {
    private Database db;

    public StudentSelfAttendanceGUI(Database database) {
        this.db = database;

        setTitle("Self Attendance");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Enter Your Student ID:"));
        JTextField studentIDField = new JTextField();
        add(studentIDField);

        JButton markPresentButton = new JButton("Mark Present");
        add(markPresentButton);

        markPresentButton.addActionListener(e -> {
            try {
                int studentID = Integer.parseInt(studentIDField.getText());
                String todayDate = LocalDate.now().toString();
                db.markAttendance(studentID, todayDate, "Present");
                JOptionPane.showMessageDialog(null, "✅ Attendance Marked Successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "❌ Invalid Student ID! Enter a valid number.");
            }
        });

        setVisible(true);
    }
}
