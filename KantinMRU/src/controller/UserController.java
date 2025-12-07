/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.Koneksi;
import model.User;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Renadi Wilantara
 */
public class UserController {
    private Connection conn;
    
    // Constructor: ambil koneksi dari class Koneksi
    public UserController() {
        conn = Koneksi.getConnection();
    }
    
    /*
     * Proses login user
     * Mencocokkan username dan password dengan data di database
     */
    public User login(String username, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try {
            // PreparedStatement untuk query dengan parameter, aman dari SQL injection
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);  // Set parameter pertama (username)
            ps.setString(2, password);  // Set parameter kedua (password)
            ResultSet rs = ps.executeQuery();
            
            // Jika ada hasil, berarti login berhasil
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNama(rs.getString("nama"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("Error login: " + e.getMessage());
        }
        
        return user;
    }
    
    /*
     * Mengambil semua data user dari database
     * Digunakan untuk menampilkan daftar user di tabel (khusus admin)
     * Return ArrayList berisi semua object User
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY id";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            // Loop semua hasil dan masukkan ke ArrayList
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNama(rs.getString("nama"));
                user.setRole(rs.getString("role"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error get all users: " + e.getMessage());
        }
        
        return list;
    }
    
    
     // Mengambil satu user berdasarkan ID
    
    public User getUserById(int id) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNama(rs.getString("nama"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("Error get user by id: " + e.getMessage());
        }
        
        return user;
    }
    
    //ID otomatis di-generate oleh database (auto increment)
    
    public boolean insertUser(User user) {
        String query = "INSERT INTO users (username, password, nama, role) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNama());
            ps.setString(4, user.getRole());
            ps.executeUpdate();  // executeUpdate untuk INSERT/UPDATE/DELETE
            return true;
        } catch (SQLException e) {
            System.out.println("Error insert user: " + e.getMessage());
            return false;
        }
    }
    
    
     // Mencari berdasarkan ID, lalu update semua field
     
    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, nama = ?, role = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNama());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getId());  // WHERE id = ?
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error update user: " + e.getMessage());
            return false;
        }
    }
    
  
    //Menghapus user dari database berdasarkan ID

    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error delete user: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Mengecek apakah username sudah dipakai user lain
     * Parameter excludeId untuk mengabaikan user yang sedang diedit
     * Contoh: saat edit user ID 5, username "admin" boleh jika memang milik ID 5
     * Return true jika username sudah ada (duplikat), false jika belum
     */
    public boolean isUsernameExists(String username, int excludeId) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND id != ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Jika count > 0, berarti ada duplikat
            }
        } catch (SQLException e) {
            System.out.println("Error cek username: " + e.getMessage());
        }
        
        return false;
    }
}
