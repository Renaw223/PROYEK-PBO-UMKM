/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.Koneksi;
import model.Menu;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Renadi Wilantara
 */
public class MenuController {
    private Connection conn;
    
    // Constructor: ambil koneksi dari class Koneksi
    public MenuController() {
        try {
            conn = Koneksi.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal koneksi di OrderController: " + e.getMessage());
        }
    }
    
    /*
     * Mengambil semua menu dari database
     * Diurutkan berdasarkan kategori lalu nama menu
     * Digunakan untuk halaman kelola menu (admin)
     */
    public ArrayList<Menu> getAllMenu() {
        ArrayList<Menu> list = new ArrayList<>();
        String query = "SELECT * FROM menu ORDER BY status ASC, kategori ASC, harga ASC";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNamaMenu(rs.getString("nama_menu"));
                menu.setKategori(rs.getString("kategori"));
                menu.setHarga(rs.getInt("harga"));
                menu.setStatus(rs.getString("status"));
                list.add(menu);
            }
        } catch (SQLException e) {
            System.out.println("Error get all menu: " + e.getMessage());
        }
        
        return list;
    }
    
    /*
     * Mengambil menu berdasarkan kategori tertentu
     * Hanya yang statusnya 'tersedia'
     * Digunakan untuk filter menu saat pembeli memilih kategori
     */
    public ArrayList<Menu> getMenuByKategori(String kategori) {
        ArrayList<Menu> list = new ArrayList<>();
        String query = "SELECT * FROM menu WHERE kategori = ? AND status = 'tersedia' ORDER BY nama_menu";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, kategori);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNamaMenu(rs.getString("nama_menu"));
                menu.setKategori(rs.getString("kategori"));
                menu.setHarga(rs.getInt("harga"));
                menu.setStatus(rs.getString("status"));
                list.add(menu);
            }
        } catch (SQLException e) {
            System.out.println("Error get menu by kategori: " + e.getMessage());
        }
        
        return list;
    }
    
    /*
     * Mengambil semua menu yang statusnya 'tersedia'
     * Digunakan untuk dropdown/list menu di form pemesanan
     * Menu yang habis tidak akan muncul
     */
    public ArrayList<Menu> getMenuTersedia() {
        ArrayList<Menu> list = new ArrayList<>();
        String query = "SELECT * FROM menu WHERE status = 'tersedia' ORDER BY kategori, nama_menu";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNamaMenu(rs.getString("nama_menu"));
                menu.setKategori(rs.getString("kategori"));
                menu.setHarga(rs.getInt("harga"));
                menu.setStatus(rs.getString("status"));
                list.add(menu);
            }
        } catch (SQLException e) {
            System.out.println("Error get menu tersedia: " + e.getMessage());
        }
        
        return list;
    }
    
    /*
     * Mengambil satu menu berdasarkan ID
     * Digunakan saat mau edit atau lihat detail menu tertentu
     */
    public Menu getMenuById(int id) {
        Menu menu = null;
        String query = "SELECT * FROM menu WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNamaMenu(rs.getString("nama_menu"));
                menu.setKategori(rs.getString("kategori"));
                menu.setHarga(rs.getInt("harga"));
                menu.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error get menu by id: " + e.getMessage());
        }
        
        return menu;
    }
    
    /*
     * Menambah menu baru ke database
     * ID otomatis di-generate oleh database
     * Return true jika berhasil, false jika gagal
     */
    public boolean insertMenu(Menu menu) {
        String query = "INSERT INTO menu (nama_menu, kategori, harga, status) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, menu.getNamaMenu());
            ps.setString(2, menu.getKategori());
            ps.setInt(3, menu.getHarga());
            ps.setString(4, menu.getStatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insert menu: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Mengupdate data menu yang sudah ada
     * Semua field diupdate berdasarkan ID
     */
    public boolean updateMenu(Menu menu) {
        String query = "UPDATE menu SET nama_menu = ?, kategori = ?, harga = ?, status = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, menu.getNamaMenu());
            ps.setString(2, menu.getKategori());
            ps.setInt(3, menu.getHarga());
            ps.setString(4, menu.getStatus());
            ps.setInt(5, menu.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error update menu: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Menghapus menu dari database berdasarkan ID
     * Hati-hati: menu yang sudah pernah diorder sebaiknya jangan dihapus
     */
    public boolean deleteMenu(int id) {
        String query = "DELETE FROM menu WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error delete menu: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Mengupdate status menu saja (tersedia/habis)
     * Lebih praktis daripada update semua field
     * Digunakan untuk tombol cepat "Tandai Habis" atau "Tandai Tersedia"
     */
    public boolean updateStatusMenu(int id, String status) {
        String query = "UPDATE menu SET status = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error update status menu: " + e.getMessage());
            return false;
        }
    }
}
