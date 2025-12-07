/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Renadi Wilantara
 */
public class Menu {
    private int id;
    private String namaMenu;
    private String kategori;
    private int harga;
    private String status;
    
    // Constructor kosong
    public Menu() {
    }
    
    // Constructor lengkap
    public Menu(int id, String namaMenu, String kategori, int harga, String status) {
        this.id = id;
        this.namaMenu = namaMenu;
        this.kategori = kategori;
        this.harga = harga;
        this.status = status;
    }
    
    // Getter & Setter
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNamaMenu() {
        return namaMenu;
    }
    
    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    public int getHarga() {
        return harga;
    }
    
    public void setHarga(int harga) {
        this.harga = harga;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
