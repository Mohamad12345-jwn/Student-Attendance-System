package com.attendance;

public class Main {
    public static void main(String[] args) {
        // Initialize the database
        Database db = new Database();

        // Start the login screen
        new LoginGUI(db);
    }
}
