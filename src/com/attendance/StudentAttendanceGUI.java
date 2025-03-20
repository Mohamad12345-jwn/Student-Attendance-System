package com.attendance;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAttendanceGUI extends JFrame {
    public StudentAttendanceGUI(Database db, int studentID) {
        setTitle("Student Attendance Report");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        try {
            ResultSet rs = db.getAttendanceReport(studentID);
            reportArea.setText("Date\tStatus\n");
            while (rs.next()) {
                reportArea.append(rs.getString("date") + "\t" + rs.getString("status") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
}
