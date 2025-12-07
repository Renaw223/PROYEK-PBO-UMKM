/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.Koneksi;
import model.OrderDetail;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Renadi Wilantara
 */
public class OrderDetailController {
    private Connection conn;
    
    // Constructor: ambil koneksi dari class Koneksi
    public OrderDetailController() {
        try {
            conn = Koneksi.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Gagal koneksi di OrderController: " + e.getMessage());
        }
    }
    
    /*
     * Mengambil semua detail item dari satu order
     * Menggunakan JOIN untuk mengambil nama menu dan harga dari tabel menu
     * Digunakan untuk menampilkan struk atau detail pesanan
     */
    public ArrayList<OrderDetail> getDetailByOrderId(int idOrder) {
        ArrayList<OrderDetail> list = new ArrayList<>();
        String query = "SELECT od.*, m.nama_menu, m.harga as harga_menu " +
                       "FROM order_detail od " +
                       "JOIN menu m ON od.id_menu = m.id " +
                       "WHERE od.id_order = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idOrder);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setId(rs.getInt("id"));
                detail.setIdOrder(rs.getInt("id_order"));
                detail.setIdMenu(rs.getInt("id_menu"));
                detail.setJumlah(rs.getInt("jumlah"));
                detail.setSubtotal(rs.getInt("subtotal"));
                // Data tambahan dari JOIN
                detail.setNamaMenu(rs.getString("nama_menu"));
                detail.setHargaMenu(rs.getInt("harga_menu"));
                list.add(detail);
            }
        } catch (SQLException e) {
            System.out.println("Error get detail by order: " + e.getMessage());
        }
        
        return list;
    }
    
    /*
     * Menyimpan satu item detail order ke database
     * Digunakan jika hanya perlu simpan 1 item saja
     */
    public boolean insertDetail(OrderDetail detail) {
        String query = "INSERT INTO order_detail (id_order, id_menu, jumlah, subtotal) " +
                       "VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, detail.getIdOrder());
            ps.setInt(2, detail.getIdMenu());
            ps.setInt(3, detail.getJumlah());
            ps.setInt(4, detail.getSubtotal());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insert detail: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Menyimpan banyak item detail sekaligus menggunakan batch
     * Lebih efisien daripada insert satu-satu karena hanya 1x eksekusi ke database
     * Digunakan saat submit order yang berisi banyak menu
     */
    public boolean insertMultipleDetails(ArrayList<OrderDetail> details) {
        String query = "INSERT INTO order_detail (id_order, id_menu, jumlah, subtotal) " +
                       "VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            
            // Loop semua detail, tambahkan ke batch
            for (OrderDetail detail : details) {
                ps.setInt(1, detail.getIdOrder());
                ps.setInt(2, detail.getIdMenu());
                ps.setInt(3, detail.getJumlah());
                ps.setInt(4, detail.getSubtotal());
                ps.addBatch();  // Tambah ke antrian batch
            }
            
            ps.executeBatch();  // Eksekusi semua batch sekaligus
            return true;
        } catch (SQLException e) {
            System.out.println("Error insert multiple details: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Menghapus semua detail dari satu order
     * Biasanya dipanggil sebelum mengupdate order (hapus lama, insert baru)
     * Atau ketika order dibatalkan
     */
    
    public boolean deleteDetailsByOrderId(int idOrder) {
        String query = "DELETE FROM order_detail WHERE id_order = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idOrder);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error delete details: " + e.getMessage());
            return false;
        }
    }
}
