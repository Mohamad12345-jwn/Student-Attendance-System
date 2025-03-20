package com.attendance;

import javax.swing.JOptionPane;
import java.sql.*;

public class Database {
    private Connection connection;

    public Database() {
        this("jdbc:sqlite:attendance.db");
    }

    public Database(String dbURL) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbURL);
            System.out.println(" Connected to SQLite database.");
            createTables();
        } catch (Exception e) {
            System.out.println(" Database Connection Failed!");
            e.printStackTrace();
        }
    }

    public void createTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, role TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, class TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS attendance (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, date TEXT, status TEXT, FOREIGN KEY(student_id) REFERENCES students(id), UNIQUE(student_id, date))");

            stmt.execute("INSERT INTO users (username, password, role) SELECT 'admin', 'admin123', 'Admin' WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='admin')");
            stmt.execute("INSERT INTO users (username, password, role) SELECT 'teacher1', 'teachpass', 'Teacher' WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='teacher1')");

            System.out.println("✅ Tables created & Default users inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isStudentRegistered(int studentID) {
        try {
            String query = "SELECT id FROM students WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); //
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password, String role) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addStudent(int studentID, String name, String className) {
        try {
            if (studentID <= 0) {
                JOptionPane.showMessageDialog(null, " Student ID must be a positive number!");
                return;
            }
            if (isStudentRegistered(studentID)) {
                JOptionPane.showMessageDialog(null, " Student ID already exists! Choose another.");
                return;
            }

            String query = "INSERT INTO students (id, name, class) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, studentID);
            pstmt.setString(2, name);
            pstmt.setString(3, className);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, " Student Added Successfully!");
            System.out.println("✅ Student Added: " + name + " (" + className + ") with ID: " + studentID);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error adding student!");
            e.printStackTrace();
        }
    }

    public void removeStudent(int studentID) {
        try {
            if (!isStudentRegistered(studentID)) {
                JOptionPane.showMessageDialog(null, " Error: Student ID not found.");
                return;
            }
            String query = "DELETE FROM students WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, studentID);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, " Student Removed Successfully!");
            System.out.println("✅ Student Removed: " + studentID);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error removing student!");
            e.printStackTrace();
        }
    }

    public void markAttendance(int studentID, String date, String status) {
        try {
            if (!isStudentRegistered(studentID)) {
                JOptionPane.showMessageDialog(null, " Error: Student ID not found.");
                return;
            }

            String query = "INSERT OR IGNORE INTO attendance (student_id, date, status) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, studentID);
            pstmt.setString(2, date);
            pstmt.setString(3, status);
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, " Attendance marked successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "️ Attendance for this student on this date already exists!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error marking attendance!");
            e.printStackTrace();
        }
    }

    public ResultSet getAttendanceReport(int studentID) {
        try {
            String query = "SELECT date, status FROM attendance WHERE student_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, studentID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
