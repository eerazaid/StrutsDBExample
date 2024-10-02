package com.jwt.struts.dao;

import java.sql.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import com.jwt.struts.form.UserRegisterForm;

public class UserRegisterDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/struts";
    private static final String USER = "guest";
    private static final String PASSWORD = "guest123";

    private static final String INSERT_USER_SQL = "INSERT INTO USER (LOGINID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM USER";
    private static final String SELECT_USER_SQL = "SELECT * FROM USER WHERE ID = ?";
    private static final String SELECT_LOGINID_SQL = "SELECT * FROM USER WHERE LOGINID = ?";
    private static final String UPDATE_USER_SQL = "UPDATE USER SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PASSWORD = ? WHERE ID = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM USER WHERE ID = ?";
    private static final String SELECT_PASSWORD_SQL = "SELECT PASSWORD FROM USER WHERE LOGINID = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE USER SET PASSWORD = ? WHERE LOGINID = ?";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    // Method to hash passwords using SHA-256 with loginId as salt
    public static String hashPasswordWithSalt(String password, String loginId) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String passwordWithSalt = password + loginId;  // Use loginId as salt
        byte[] hashedBytes = md.digest(passwordWithSalt.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Check if loginId exists
    public boolean isLoginIdExists(String loginId) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(SELECT_LOGINID_SQL)) {
            pstmt.setString(1, loginId);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("login id : " + loginId);
            return rs.next();  // Returns true if loginId exists
            
            
        }
    }

    // Verify current password
    public boolean verifyCurrentPassword(String loginId, String currentPassword) throws Exception {
        String storedHash = getPasswordHashByLoginId(loginId); // Get the stored hashed password
        String hashedCurrentPassword = hashPasswordWithSalt(currentPassword, loginId); // Hash the input password with the same salt

        // Logging for debugging
        System.out.println("Current Password: " + currentPassword);

        System.out.println("Stored Hash: " + storedHash);
        System.out.println("Current Password Attempt: " + hashedCurrentPassword);
        System.out.println("Is Current Password Valid: " + storedHash.equals(hashedCurrentPassword));

        return storedHash.equals(hashedCurrentPassword);
    }

    // Method to get the hashed password by loginId
    private String getPasswordHashByLoginId(String loginId) throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(SELECT_PASSWORD_SQL)) {
            pstmt.setString(1, loginId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("PASSWORD");
            }
            return null; // If no password is found
        }
    }

 // Method to update password with validation that new password cannot be the same as the current password
    public void updatePassword(String loginId, String newPassword, String confirmPassword) throws Exception {
        // Step 1: Check if newPassword and confirmPassword match
        if (!newPassword.equals(confirmPassword)) {
            throw new Exception("New password and confirm password do not match.");
        }

        // Step 2: Get the current stored password hash
        String currentPasswordHash = getPasswordHashByLoginId(loginId);

        // Step 3: Hash the new password with loginId as salt
        String hashedNewPassword = hashPasswordWithSalt(newPassword, loginId);
        
        // Step 4: Check if the new password hash is the same as the current password hash
        if (currentPasswordHash.equals(hashedNewPassword)) {
            throw new Exception("New password cannot be the same as the current password.");
        }

        // Step 5: Update the password in the database
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(UPDATE_PASSWORD_SQL)) {
            pstmt.setString(1, hashedNewPassword);  // Store hashed password
            pstmt.setString(2, loginId);  // Identify user by loginId
            pstmt.executeUpdate();  // Update password in the database
        }

        // Logging for debugging
        System.out.println("Password updated for loginId: " + loginId);
        System.out.println("New password: " + newPassword);
        System.out.println("Confirm new password: " + confirmPassword);
        System.out.println("New Password Hash: " + hashedNewPassword);
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
                user.setPassword(rs.getString("PASSWORD"));  
                user.setLoginId(rs.getString("LOGINID"));  
                users.add(user);
            }
        }
        return users;
    }

    // Insert user with hashed password using loginId as salt
    public void insertData(String loginId, String firstName, String lastName, String email, String password) throws Exception {
        if (isLoginIdExists(loginId)) {
            throw new Exception("Login ID already exists!");  // Ensure loginId is unique
        }

        String hashedPassword = hashPasswordWithSalt(password, loginId);  // Use loginId as the salt

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, loginId);  
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, hashedPassword); 
            pstmt.executeUpdate();
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
                user.setPassword(rs.getString("PASSWORD"));
                user.setLoginId(rs.getString("LOGINID"));  
            }
        }
        return user;
    }

    // Update user with hashed password using loginId as salt
    public void updateUser(UserRegisterForm user, int id) throws Exception {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(UPDATE_USER_SQL)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());

            // Hash the password using the loginId as salt before updating
            String hashedPassword = hashPasswordWithSalt(user.getPassword(), user.getLoginId());
            pstmt.setString(4, hashedPassword);  // Update hashed password

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
