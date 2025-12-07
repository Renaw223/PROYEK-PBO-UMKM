/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.Koneksi;
import model.Order;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Renadi Wilantara
 */
public class OrderController {
    private Connection conn;
    
    // Constructor: langsung ambil koneksi dari class Koneksi
    public OrderController() {
        conn = Koneksi.getConnection();
    }
    
    /*
     * Generate nomor order otomatis dengan format ORD-XXX
     * Mengambil nomor terakhir dari database, lalu ditambah 1
     * Contoh: jika terakhir ORD-005, maka return ORD-006
     * Jika belum ada order sama sekali, return ORD-001
     */
    public String generateNomorOrder() {
        String nomorOrder = "ORD-001";
        String query = "SELECT nomor_order FROM orders ORDER BY id DESC LIMIT 1";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                String lastNomor = rs.getString("nomor_order");
                // Split "ORD-005" jadi ["ORD", "005"], ambil index 1
                int angka = Integer.parseInt(lastNomor.split("-")[1]);
                angka++;
                // Format dengan padding 3 digit: 6 jadi "006"
                nomorOrder = String.format("ORD-%03d", angka);
            }
        } catch (SQLException e) {
            System.out.println("Error generate nomor order: " + e.getMessage());
        }
        
        return nomorOrder;
    }
    
    /*
     * Mengambil semua order dari database (untuk admin)
     * Menggunakan JOIN untuk mengambil nama pembeli dari tabel users
     * Diurutkan dari yang terbaru (DESC)
     */
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT o.*, u.nama as nama_pembeli FROM orders o " +
                       "JOIN users u ON o.id_user = u.id " +
                       "ORDER BY o.created_at DESC";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setNomorOrder(rs.getString("nomor_order"));
                order.setLokasiPelanggan(rs.getString("lokasi_pelanggan"));
                order.setTotalHarga(rs.getInt("total_harga"));
                order.setStatusBayar(rs.getString("status_bayar"));
                order.setStatusOrder(rs.getString("status_order"));
                order.setIdUser(rs.getInt("id_user"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setNamaPembeli(rs.getString("nama_pembeli"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error get all orders: " + e.getMessage());
        }
        
        return list;
    }
    
    /*
     * Mengambil order berdasarkan ID user tertentu (untuk pembeli)
     * Pembeli hanya bisa melihat order miliknya sendiri
     * Diurutkan dari yang terbaru
     */
    public ArrayList<Order> getOrdersByUser(int idUser) {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT o.*, u.nama as nama_pembeli FROM orders o " +
                       "JOIN users u ON o.id_user = u.id " +
                       "WHERE o.id_user = ? " +
                       "ORDER BY o.created_at DESC";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setNomorOrder(rs.getString("nomor_order"));
                order.setLokasiPelanggan(rs.getString("lokasi_pelanggan"));
                order.setTotalHarga(rs.getInt("total_harga"));
                order.setStatusBayar(rs.getString("status_bayar"));
                order.setStatusOrder(rs.getString("status_order"));
                order.setIdUser(rs.getInt("id_user"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setNamaPembeli(rs.getString("nama_pembeli"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error get orders by user: " + e.getMessage());
        }
        
        return list;
    }
    
    /*
     * Mengambil order yang statusnya masih 'diproses'
     * Diurutkan dari yang paling lama (ASC) supaya yang pertama order dilayani duluan
     * Digunakan untuk menampilkan antrian pesanan di dashboard admin
     */
    public ArrayList<Order> getOrdersDiproses() {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT o.*, u.nama as nama_pembeli FROM orders o " +
                       "JOIN users u ON o.id_user = u.id " +
                       "WHERE o.status_order = 'diproses' " +
                       "ORDER BY o.created_at ASC";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setNomorOrder(rs.getString("nomor_order"));
                order.setLokasiPelanggan(rs.getString("lokasi_pelanggan"));
                order.setTotalHarga(rs.getInt("total_harga"));
                order.setStatusBayar(rs.getString("status_bayar"));
                order.setStatusOrder(rs.getString("status_order"));
                order.setIdUser(rs.getInt("id_user"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setNamaPembeli(rs.getString("nama_pembeli"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error get orders diproses: " + e.getMessage());
        }
        
        return list;
    }
    
    /*
     * Mengambil satu order berdasarkan ID
     * Digunakan untuk melihat detail order atau edit order
     */
    public Order getOrderById(int id) {
        Order order = null;
        String query = "SELECT o.*, u.nama as nama_pembeli FROM orders o " +
                       "JOIN users u ON o.id_user = u.id " +
                       "WHERE o.id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setNomorOrder(rs.getString("nomor_order"));
                order.setLokasiPelanggan(rs.getString("lokasi_pelanggan"));
                order.setTotalHarga(rs.getInt("total_harga"));
                order.setStatusBayar(rs.getString("status_bayar"));
                order.setStatusOrder(rs.getString("status_order"));
                order.setIdUser(rs.getInt("id_user"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setNamaPembeli(rs.getString("nama_pembeli"));
            }
        } catch (SQLException e) {
            System.out.println("Error get order by id: " + e.getMessage());
        }
        
        return order;
    }
    
    /*
     * Menyimpan order baru ke database
     * Menggunakan RETURN_GENERATED_KEYS untuk mendapatkan ID yang baru dibuat
     * ID ini diperlukan untuk menyimpan order_detail
     * Return ID order jika berhasil, -1 jika gagal
     */
    public int insertOrder(Order order) {
        String query = "INSERT INTO orders (nomor_order, lokasi_pelanggan, total_harga, " +
                       "status_bayar, status_order, id_user) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            // RETURN_GENERATED_KEYS supaya bisa ambil ID yang auto-generated
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getNomorOrder());
            ps.setString(2, order.getLokasiPelanggan());
            ps.setInt(3, order.getTotalHarga());
            ps.setString(4, order.getStatusBayar());
            ps.setString(5, order.getStatusOrder());
            ps.setInt(6, order.getIdUser());
            ps.executeUpdate();
            
            // Ambil ID yang baru saja di-generate database
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error insert order: " + e.getMessage());
        }
        
        return -1;
    }
    
    /*
     * Mengupdate status pembayaran order (belum/lunas)
     * Digunakan admin untuk menandai pesanan yang sudah dibayar
     */
    public boolean updateStatusBayar(int id, String statusBayar) {
        String query = "UPDATE orders SET status_bayar = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, statusBayar);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error update status bayar: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Mengupdate status order (diproses/selesai/dibatalkan)
     * Digunakan admin untuk menandai pesanan yang sudah selesai dimasak/diantar
     */
    public boolean updateStatusOrder(int id, String statusOrder) {
        String query = "UPDATE orders SET status_order = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, statusOrder);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error update status order: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Menghapus order dari database
     * Order detail akan ikut terhapus otomatis karena ON DELETE CASCADE di database
     */
    public boolean deleteOrder(int id) {
        String query = "DELETE FROM orders WHERE id = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error delete order: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * Menghitung total pendapatan hari ini
     * Hanya menghitung order yang sudah lunas
     * Digunakan untuk statistik di dashboard admin
     */
    public int getTotalPendapatanHariIni() {
        int total = 0;
        String query = "SELECT SUM(total_harga) as total FROM orders " +
                       "WHERE DATE(created_at) = CURDATE() AND status_bayar = 'lunas'";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error get total pendapatan: " + e.getMessage());
        }
        
        return total;
    }
    
    /*
     * Menghitung jumlah order yang masuk hari ini
     * Termasuk semua status (diproses, selesai, dibatalkan)
     * Digunakan untuk statistik di dashboard admin
     */
    public int getJumlahOrderHariIni() {
        int jumlah = 0;
        String query = "SELECT COUNT(*) as jumlah FROM orders WHERE DATE(created_at) = CURDATE()";
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                jumlah = rs.getInt("jumlah");
            }
        } catch (SQLException e) {
            System.out.println("Error get jumlah order: " + e.getMessage());
        }
        
        return jumlah;
    }
}
