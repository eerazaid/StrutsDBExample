package com.jwt.struts.dao;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.jwt.struts.form.UserRegisterForm;

public class UserRegisterDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/struts";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest123";

    private static final String INSERT_USER_SQL = "INSERT INTO USER (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM USER";
    private static final String SELECT_USER_SQL = "SELECT * FROM USER WHERE ID = ?";
    private static final String UPDATE_USER_SQL = "UPDATE USER SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PASSWORD = ? WHERE ID = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM USER WHERE ID = ?";

    // Ensure the MySQL driver is loaded
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    // Method to hash passwords using SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Get all users from the database
    public List<UserRegisterForm> getAllUsers() throws Exception {
        List<UserRegisterForm> users = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(SELECT_ALL_USERS_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UserRegisterForm user = new UserRegisterForm();
                user.setId(rs.getInt("ID"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPassword(rs.getString("PASSWORD")); // Include hashed password
                users.add(user);
            }
        }
        return users;
    }

    // Insert user with hashed password
    public void insertData(String firstName, String lastName, String email, String password) throws Exception {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            String hashedPassword = hashPassword(password);
            pstmt.setString(4, hashedPassword);

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                System.out.println("Inserted User ID: " + id);
            }
        }
    }

    // Get user by ID
    public UserRegisterForm getUserById(int id) throws Exception {
        UserRegisterForm user = null;
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(SELECT_USER_SQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new UserRegisterForm();
                user.setId(rs.getInt("ID"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPassword(rs.getString("PASSWORD")); // Include hashed password
            }
        }
        return user;
    }

    // Update user with hashed password
    public void updateUser(UserRegisterForm user, int id) throws Exception {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(UPDATE_USER_SQL)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());

            // Hash the password before updating
            String hashedPassword = hashPassword(user.getPassword());
            pstmt.setString(4, hashedPassword); // Update hashed password

            pstmt.setInt(5, id);  
            pstmt.executeUpdate();
        }
    }

    // Delete user by ID
    public void deleteUser(int id) throws Exception {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(DELETE_USER_SQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
