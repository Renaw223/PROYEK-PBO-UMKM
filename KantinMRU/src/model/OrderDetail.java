/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Renadi Wilantara
 */
public class OrderDetail {
    private int id;
    private int idOrder;
    private int idMenu;
    private int jumlah;
    private int subtotal;
    
    // Tambahan: untuk menampilkan nama menu (dari JOIN)
    private String namaMenu;
    private int hargaMenu;
    
    // Constructor kosong
    public OrderDetail() {
    }
    
    // Constructor lengkap
    public OrderDetail(int id, int idOrder, int idMenu, int jumlah, int subtotal) {
        this.id = id;
        this.idOrder = idOrder;
        this.idMenu = idMenu;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }
    
    // Getter & Setter
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdOrder() {
        return idOrder;
    }
    
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
    
    public int getIdMenu() {
        return idMenu;
    }
    
    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }
    
    public int getJumlah() {
        return jumlah;
    }
    
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    
    public int getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
    
    public String getNamaMenu() {
        return namaMenu;
    }
    
    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }
    
    public int getHargaMenu() {
        return hargaMenu;
    }
    
    public void setHargaMenu(int hargaMenu) {
        this.hargaMenu = hargaMenu;
    }
}
