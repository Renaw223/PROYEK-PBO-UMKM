/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Renadi Wilantara
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String nama;
    private String role;
    
    // Constructor kosong
    public User() {
    }
    
    // Constructor lengkap
    public User(int id, String username, String password, String nama, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.role = role;
    }
    
    // Getter & Setter
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
