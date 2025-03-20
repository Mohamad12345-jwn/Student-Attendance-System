package com.attendance;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportGUI extends JFrame {
    private Database db;

    public ReportGUI(Database database) {
        this.db = database;

        setTitle("Student Attendance Report");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        panel.add(new JLabel("Enter Student ID:"));
        JTextField studentIDField = new JTextField();
        panel.add(studentIDField);

        JButton viewButton = new JButton("View Report");
        panel.add(viewButton);
        add(panel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        viewButton.addActionListener(e -> {
            int studentID = Integer.parseInt(studentIDField.getText());
            ResultSet rs = db.getAttendanceReport(studentID);

            try {
                reportArea.setText("Date\tStatus\n");
                while (rs.next()) {
                    reportArea.append(rs.getString("date") + "\t" + rs.getString("status") + "\n");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}
