/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Renadi Wilantara
 */
public class Order {
    private int id;
    private String nomorOrder;
    private String lokasiPelanggan;
    private int totalHarga;
    private String statusBayar;
    private String statusOrder;
    private int idUser;
    private Timestamp createdAt;
    
    // Tambahan: untuk menampilkan nama pembeli (dari JOIN)
    private String namaPembeli;
    
    // Constructor kosong
    public Order() {
    }
    
    // Constructor lengkap
    public Order(int id, String nomorOrder, String lokasiPelanggan, int totalHarga,
                 String statusBayar, String statusOrder, int idUser, Timestamp createdAt) {
        this.id = id;
        this.nomorOrder = nomorOrder;
        this.lokasiPelanggan = lokasiPelanggan;
        this.totalHarga = totalHarga;
        this.statusBayar = statusBayar;
        this.statusOrder = statusOrder;
        this.idUser = idUser;
        this.createdAt = createdAt;
    }
    
    // Getter & Setter
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNomorOrder() {
        return nomorOrder;
    }
    
    public void setNomorOrder(String nomorOrder) {
        this.nomorOrder = nomorOrder;
    }
    
    public String getLokasiPelanggan() {
        return lokasiPelanggan;
    }
    
    public void setLokasiPelanggan(String lokasiPelanggan) {
        this.lokasiPelanggan = lokasiPelanggan;
    }
    
    public int getTotalHarga() {
        return totalHarga;
    }
    
    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }
    
    public String getStatusBayar() {
        return statusBayar;
    }
    
    public void setStatusBayar(String statusBayar) {
        this.statusBayar = statusBayar;
    }
    
    public String getStatusOrder() {
        return statusOrder;
    }
    
    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }
    
    public int getIdUser() {
        return idUser;
    }
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getNamaPembeli() {
        return namaPembeli;
    }
    
    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }
}
