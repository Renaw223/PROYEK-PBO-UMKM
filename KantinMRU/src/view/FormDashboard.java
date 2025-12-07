/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import model.User;
import javax.swing.JOptionPane;

import controller.OrderController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Order;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import java.util.Locale;
import java.text.SimpleDateFormat;
import config.Koneksi;
import model.Menu;
import controller.MenuController;
import model.User;
import controller.UserController;
/**
 *
 * @author Renadi Wilantara
 */
public class FormDashboard extends javax.swing.JFrame {
    private User currentUser;
    private OrderController orderController;
    private MenuController menuController;
    private UserController userController;
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormDashboard.class.getName());

    /**
     * Creates new form FormDashboard
     */
    public FormDashboard() {
        initComponents();
        
        // Ambil data user yang login
        currentUser = FormLogin.loggedInUser;
        
        // Inisialisasi controller
        orderController = new OrderController();
        menuController = new MenuController();
        userController = new UserController();
        
        // Set form di tengah layar
        setLocationRelativeTo(null);
        setTitle("Dashboard - Kantin MRU");
        
        // Tampilkan nama user
        lblWelcome.setText("Selamat datang, " + currentUser.getNama() + "!");
        
        // Cek role, sembunyikan menu admin jika pembeli
        if (currentUser.getRole().equals("pembeli")) {
            btnMenuKelolaMenu.setVisible(false);
            btnMenuKelolaUser.setVisible(false);
            btnMenuLaporan.setVisible(false);
            
            // Sembunyikan card pendapatan untuk pembeli
            panelStatPendapatan.setVisible(false);
        }
        
        // Load data dashboard
        loadDashboardData();
        
        // Tampilkan card dashboard sebagai default
        showCard("dashboard");
    }
    
    /*
     * Load semua data untuk dashboard (statistik + tabel)
     */
    private void loadDashboardData() {
        // Format angka ke Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        // Load statistik
        int jumlahOrder = orderController.getJumlahOrderHariIni();
        int pendapatan = orderController.getTotalPendapatanHariIni();
        int orderDiproses = orderController.getOrdersDiproses().size();
        
        // Tampilkan ke label
        lblStatOrderValue.setText(String.valueOf(jumlahOrder));
        lblStatPendapatanValue.setText(formatRupiah.format(pendapatan));
        lblStatDiprosesValue.setText(String.valueOf(orderDiproses));
        
        // Load tabel order terbaru
        loadTabelOrderTerbaru();
        loadTabelMenu();
        loadTabelUser();
    }
    
    /*
     * Load data order ke tabel dashboard
     */
    private void loadTabelOrderTerbaru() {
        DefaultTableModel model = (DefaultTableModel) tblDashboardOrder.getModel();
        model.setRowCount(0);  // Kosongkan tabel dulu
        
        ArrayList<Order> orders;
        
        // Admin lihat semua order, pembeli hanya lihat order sendiri
        if (currentUser.getRole().equals("admin")) {
            orders = orderController.getAllOrders();
        } else {
            orders = orderController.getOrdersByUser(currentUser.getId());
        }
        
        // Format tanggal
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        // Format rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        // Tambahkan data ke tabel (maksimal 10 terbaru)
        int count = 0;
        for (Order order : orders) {
            if (count >= 10) break;
            
            model.addRow(new Object[]{
                order.getNomorOrder(),
                order.getNamaPembeli(),
                formatRupiah.format(order.getTotalHarga()),
                order.getStatusBayar(),
                order.getStatusOrder(),
                sdf.format(order.getCreatedAt())
            });
            count++;
        }
    }
    
    private void loadTabelMenu() {
        DefaultTableModel model = (DefaultTableModel) tabelMenu.getModel();
        model.setRowCount(0);
        
        ArrayList<Menu> Menus = menuController.getAllMenu();
        
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        int count = 0;
        for (Menu menu : Menus) {
            model.addRow(new Object[]{
                menu.getId(),
                menu.getNamaMenu(), 
                menu.getKategori(),
                formatRupiah.format(menu.getHarga()), 
                menu.getStatus()
            });
            count++;
        }
        
        tabelMenu.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelMenu.getColumnModel().getColumn(0).setMinWidth(0);
        tabelMenu.getColumnModel().getColumn(0).setPreferredWidth(0);
        tabelMenu.getColumnModel().getColumn(0).setWidth(0);
    }
    
    private void loadTabelUser() {
        DefaultTableModel model = (DefaultTableModel) tabelUser.getModel();
        model.setRowCount(0);
        
        ArrayList<User> Users = userController.getAllUsers();
        
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        int count = 0;
        for (User user : Users) {
            model.addRow(new Object[]{
                user.getId(),
                user.getUsername(), 
                user.getPassword(),
                user.getNama(), 
                user.getRole()
            });
            count++;
        }
        
        tabelUser.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelUser.getColumnModel().getColumn(0).setMinWidth(0);
        tabelUser.getColumnModel().getColumn(0).setPreferredWidth(0);
        tabelUser.getColumnModel().getColumn(0).setWidth(0);
    }
    
    // Method untuk pindah antar card
    private void showCard(String cardName) {
        java.awt.CardLayout cl = (java.awt.CardLayout) panelContent.getLayout();
        cl.show(panelContent, cardName);
    }
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        panelHeader = new javax.swing.JPanel();
        lblTitleHeader = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        panelSidebar = new javax.swing.JPanel();
        btnMenuDashboard = new javax.swing.JButton();
        btnMenuDaftarOrder = new javax.swing.JButton();
        btnMenuOrder = new javax.swing.JButton();
        btnMenuKelolaMenu = new javax.swing.JButton();
        btnMenuKelolaUser = new javax.swing.JButton();
        btnMenuLaporan = new javax.swing.JButton();
        panelContent = new javax.swing.JPanel();
        cardDashboard = new javax.swing.JPanel();
        lblDashTitle = new javax.swing.JLabel();
        lblDashSubtitle = new javax.swing.JLabel();
        panelStatOrder = new javax.swing.JPanel();
        lblStatOrderTitle = new javax.swing.JLabel();
        lblStatOrderValue = new javax.swing.JLabel();
        panelStatPendapatan = new javax.swing.JPanel();
        lblStatPendapatanTitle = new javax.swing.JLabel();
        lblStatPendapatanValue = new javax.swing.JLabel();
        panelStatDiproses = new javax.swing.JPanel();
        lblStatDiprosesTitle = new javax.swing.JLabel();
        lblStatDiprosesValue = new javax.swing.JLabel();
        lblRecentTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDashboardOrder = new javax.swing.JTable();
        cardOrder = new javax.swing.JPanel();
        cardDaftarOrder = new javax.swing.JPanel();
        cardKelolaMenu = new javax.swing.JPanel();
        btnAddMenu = new javax.swing.JButton();
        scrollMenu = new javax.swing.JScrollPane();
        tabelMenu = new javax.swing.JTable();
        btnDeleteMenu = new javax.swing.JButton();
        btnModifyMenu = new javax.swing.JButton();
        cardKelolaUser = new javax.swing.JPanel();
        btnAddUser = new javax.swing.JButton();
        scrollUser = new javax.swing.JScrollPane();
        tabelUser = new javax.swing.JTable();
        btnDeleteUser = new javax.swing.JButton();
        btnModifyUser = new javax.swing.JButton();
        cardLaporan = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelHeader.setBackground(new java.awt.Color(91, 143, 123));

        lblTitleHeader.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lblTitleHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleHeader.setText("KANTIN MRU");

        lblWelcome.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("Selamat datang, [nama]!");

        btnLogout.setBackground(new java.awt.Color(204, 204, 0));
        btnLogout.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblTitleHeader)
                .addGap(206, 206, 206)
                .addComponent(lblWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(25, 25, 25))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWelcome)
                    .addComponent(btnLogout)
                    .addComponent(lblTitleHeader))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, -1));

        panelSidebar.setBackground(new java.awt.Color(125, 189, 154));

        btnMenuDashboard.setBackground(new java.awt.Color(91, 143, 123));
        btnMenuDashboard.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMenuDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnMenuDashboard.setText("Dashboard");
        btnMenuDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuDashboardActionPerformed(evt);
            }
        });

        btnMenuDaftarOrder.setBackground(new java.awt.Color(91, 143, 123));
        btnMenuDaftarOrder.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMenuDaftarOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnMenuDaftarOrder.setText("Daftar Order");
        btnMenuDaftarOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuDaftarOrderActionPerformed(evt);
            }
        });

        btnMenuOrder.setBackground(new java.awt.Color(91, 143, 123));
        btnMenuOrder.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMenuOrder.setForeground(new java.awt.Color(255, 255, 255));
        btnMenuOrder.setText("Buat Order");
        btnMenuOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuOrderActionPerformed(evt);
            }
        });

        btnMenuKelolaMenu.setBackground(new java.awt.Color(91, 143, 123));
        btnMenuKelolaMenu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMenuKelolaMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnMenuKelolaMenu.setText("Kelola Menu");
        btnMenuKelolaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuKelolaMenuActionPerformed(evt);
            }
        });

        btnMenuKelolaUser.setBackground(new java.awt.Color(91, 143, 123));
        btnMenuKelolaUser.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMenuKelolaUser.setForeground(new java.awt.Color(255, 255, 255));
        btnMenuKelolaUser.setText("Kelola User");
        btnMenuKelolaUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuKelolaUserActionPerformed(evt);
            }
        });

        btnMenuLaporan.setBackground(new java.awt.Color(91, 143, 123));
        btnMenuLaporan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMenuLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnMenuLaporan.setText("Laporan");
        btnMenuLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuLaporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSidebarLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(panelSidebarLayout);
        panelSidebarLayout.setHorizontalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMenuDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMenuDaftarOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
            .addComponent(btnMenuOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMenuKelolaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMenuKelolaUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMenuLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelSidebarLayout.setVerticalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addComponent(btnMenuDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuDaftarOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuKelolaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenuKelolaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMenuLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(panelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 160, 440));

        panelContent.setBackground(new java.awt.Color(240, 237, 229));
        panelContent.setLayout(new java.awt.CardLayout());

        cardDashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDashTitle.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lblDashTitle.setForeground(new java.awt.Color(91, 143, 123));
        lblDashTitle.setText("DASHBOARD");
        cardDashboard.add(lblDashTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblDashSubtitle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblDashSubtitle.setForeground(new java.awt.Color(102, 102, 102));
        lblDashSubtitle.setText("Ringkasan aktivitas Kantin MRU");
        cardDashboard.add(lblDashSubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        panelStatOrder.setBackground(new java.awt.Color(125, 189, 154));
        panelStatOrder.setPreferredSize(new java.awt.Dimension(120, 100));

        lblStatOrderTitle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStatOrderTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblStatOrderTitle.setText("Order Hari Ini");

        lblStatOrderValue.setFont(new java.awt.Font("Arial", 1, 32)); // NOI18N
        lblStatOrderValue.setForeground(new java.awt.Color(255, 255, 255));
        lblStatOrderValue.setText("0");

        javax.swing.GroupLayout panelStatOrderLayout = new javax.swing.GroupLayout(panelStatOrder);
        panelStatOrder.setLayout(panelStatOrderLayout);
        panelStatOrderLayout.setHorizontalGroup(
            panelStatOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStatOrderLayout.createSequentialGroup()
                .addGroup(panelStatOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStatOrderLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblStatOrderTitle))
                    .addGroup(panelStatOrderLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(lblStatOrderValue)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelStatOrderLayout.setVerticalGroup(
            panelStatOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStatOrderLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblStatOrderTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatOrderValue)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        cardDashboard.add(panelStatOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        panelStatPendapatan.setBackground(new java.awt.Color(91, 143, 123));
        panelStatPendapatan.setPreferredSize(new java.awt.Dimension(120, 100));

        lblStatPendapatanTitle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStatPendapatanTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblStatPendapatanTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatPendapatanTitle.setText("Pendapatan");

        lblStatPendapatanValue.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblStatPendapatanValue.setForeground(new java.awt.Color(255, 255, 255));
        lblStatPendapatanValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatPendapatanValue.setText("Rp0");

        javax.swing.GroupLayout panelStatPendapatanLayout = new javax.swing.GroupLayout(panelStatPendapatan);
        panelStatPendapatan.setLayout(panelStatPendapatanLayout);
        panelStatPendapatanLayout.setHorizontalGroup(
            panelStatPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStatPendapatanLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(panelStatPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatPendapatanValue)
                    .addComponent(lblStatPendapatanTitle))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        panelStatPendapatanLayout.setVerticalGroup(
            panelStatPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStatPendapatanLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblStatPendapatanTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatPendapatanValue)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        cardDashboard.add(panelStatPendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 170, -1));

        panelStatDiproses.setBackground(new java.awt.Color(212, 175, 55));
        panelStatDiproses.setPreferredSize(new java.awt.Dimension(120, 100));

        lblStatDiprosesTitle.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStatDiprosesTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblStatDiprosesTitle.setText("Order Diproses");

        lblStatDiprosesValue.setFont(new java.awt.Font("Arial", 1, 32)); // NOI18N
        lblStatDiprosesValue.setForeground(new java.awt.Color(255, 255, 255));
        lblStatDiprosesValue.setText("0");

        javax.swing.GroupLayout panelStatDiprosesLayout = new javax.swing.GroupLayout(panelStatDiproses);
        panelStatDiproses.setLayout(panelStatDiprosesLayout);
        panelStatDiprosesLayout.setHorizontalGroup(
            panelStatDiprosesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStatDiprosesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStatDiprosesValue)
                .addGap(49, 49, 49))
            .addGroup(panelStatDiprosesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblStatDiprosesTitle)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelStatDiprosesLayout.setVerticalGroup(
            panelStatDiprosesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStatDiprosesLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblStatDiprosesTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatDiprosesValue)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        cardDashboard.add(panelStatDiproses, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, -1, -1));

        lblRecentTitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblRecentTitle.setText("Order Terbaru:");
        cardDashboard.add(lblRecentTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        tblDashboardOrder.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        tblDashboardOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No Order", "Pembeli", "Total", "Status Bayar", "Status Order", "Waktu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDashboardOrder);

        cardDashboard.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 650, 210));

        panelContent.add(cardDashboard, "dashboard");

        cardOrder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelContent.add(cardOrder, "order");

        cardDaftarOrder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelContent.add(cardDaftarOrder, "daftarOrder");

        cardKelolaMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAddMenu.setText("Tambah menu");
        btnAddMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMenuActionPerformed(evt);
            }
        });
        cardKelolaMenu.add(btnAddMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        scrollMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollMenuMouseClicked(evt);
            }
        });

        tabelMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Kategori", "Harga", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollMenu.setViewportView(tabelMenu);

        cardKelolaMenu.add(scrollMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 670, 340));

        btnDeleteMenu.setText("Delete");
        btnDeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMenuActionPerformed(evt);
            }
        });
        cardKelolaMenu.add(btnDeleteMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, -1));

        btnModifyMenu.setText("Modify");
        btnModifyMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyMenuActionPerformed(evt);
            }
        });
        cardKelolaMenu.add(btnModifyMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, -1, -1));

        panelContent.add(cardKelolaMenu, "kelolaMenu");

        cardKelolaUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAddUser.setText("Tambah User");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });
        cardKelolaUser.add(btnAddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        tabelUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Username", "Password", "Nama", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollUser.setViewportView(tabelUser);

        cardKelolaUser.add(scrollUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 50, 670, 340));

        btnDeleteUser.setText("Delete");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });
        cardKelolaUser.add(btnDeleteUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, -1));

        btnModifyUser.setText("Modify");
        btnModifyUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyUserActionPerformed(evt);
            }
        });
        cardKelolaUser.add(btnModifyUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, -1, -1));

        panelContent.add(cardKelolaUser, "kelolaUser");

        cardLaporan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelContent.add(cardLaporan, "laporan");

        getContentPane().add(panelContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 670, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuDaftarOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuDaftarOrderActionPerformed
        // TODO add your handling code here:
        showCard("daftarOrder");
    }//GEN-LAST:event_btnMenuDaftarOrderActionPerformed

    private void btnMenuOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuOrderActionPerformed
        // TODO add your handling code here:
        showCard("order");
    }//GEN-LAST:event_btnMenuOrderActionPerformed

    private void btnMenuKelolaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuKelolaMenuActionPerformed
        // TODO add your handling code here:
        showCard("kelolaMenu");
    }//GEN-LAST:event_btnMenuKelolaMenuActionPerformed

    private void btnMenuKelolaUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuKelolaUserActionPerformed
        // TODO add your handling code here:
        showCard("kelolaUser");
    }//GEN-LAST:event_btnMenuKelolaUserActionPerformed

    private void btnMenuLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuLaporanActionPerformed
        // TODO add your handling code here:
        showCard("laporan");
    }//GEN-LAST:event_btnMenuLaporanActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin ingin logout?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            // Reset user yang login
            FormLogin.loggedInUser = null;
            
            // Kembali ke form login
            new FormLogin().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnMenuDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuDashboardActionPerformed
        // TODO add your handling code here:
        loadDashboardData();  // Refresh data setiap klik
        showCard("dashboard");
    }//GEN-LAST:event_btnMenuDashboardActionPerformed

    private void scrollMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollMenuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_scrollMenuMouseClicked

    private void btnDeleteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMenuActionPerformed
        // TODO add your handling code here:
        int row = tabelMenu.getSelectedRow();
        
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris dulu");
            return;
        }
       
        tabelMenu.setRowSelectionInterval(row, row);
                
        int id = (Integer) tabelMenu.getValueAt(row, 0);
        String nama = (String) tabelMenu.getValueAt(row, 1);
        String kategori = (String) tabelMenu.getValueAt(row, 2);
                
        hapusMenu(id, nama, kategori);
    }//GEN-LAST:event_btnDeleteMenuActionPerformed

    private void btnModifyMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyMenuActionPerformed
        // TODO add your handling code here:
        int row = tabelMenu.getSelectedRow();
        
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris dulu");
            return;
        }
       
        tabelMenu.setRowSelectionInterval(row, row);
                
        int id = (Integer) tabelMenu.getValueAt(row, 0);
        String nama = (String) tabelMenu.getValueAt(row, 1);
        String kategori = (String) tabelMenu.getValueAt(row, 2);
        String hargaStr = (String) tabelMenu.getValueAt(row, 3);
        int harga = 0;
        try {
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            Number parsed = formatRupiah.parse(hargaStr);
            harga = parsed.intValue();  // hasil aman, tidak akan jadi + "00"
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal membaca harga: " + hargaStr);
            return;
        }
        String status = (String) tabelMenu.getValueAt(row, 4);
                
        modifyMenu(id, nama, kategori, harga, status);
    }//GEN-LAST:event_btnModifyMenuActionPerformed

    private void btnAddMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMenuActionPerformed
        // TODO add your handling code here:
        addMenu();
    }//GEN-LAST:event_btnAddMenuActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        // TODO add your handling code here:
        addUser();
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        // TODO add your handling code here:
        int row = tabelUser.getSelectedRow();
        
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris dulu");
            return;
        }
       
        tabelUser.setRowSelectionInterval(row, row);
                
        int id = (Integer) tabelUser.getValueAt(row, 0);
        String un = (String) tabelUser.getValueAt(row, 1);
        String pass = (String) tabelUser.getValueAt(row, 2);
        String nama = (String) tabelUser.getValueAt(row, 3);
                
        hapusUser(id, un, pass, nama);
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnModifyUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyUserActionPerformed
        // TODO add your handling code here:
        int row = tabelUser.getSelectedRow();
        
        if(row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih baris dulu");
            return;
        }
       
        tabelUser.setRowSelectionInterval(row, row);
                
        int id = (int) tabelUser.getValueAt(row, 0);
        String un = (String) tabelUser.getValueAt(row, 1);
        String pass = (String) tabelUser.getValueAt(row, 2);
        String nama = (String) tabelUser.getValueAt(row, 3);
        String role = (String) tabelUser.getValueAt(row, 4);
                
        modifyUser(id, un, pass, nama, role);
    }//GEN-LAST:event_btnModifyUserActionPerformed

    private void hapusMenu(int id, String nama, String kategori) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Hapus menu berikut?\n\n" +
            "Nama: " + nama + "\n" +
            "Kategori: " + kategori + "\n\n" +
            "Tindakan ini tidak dapat dibatalkan!",
            "Konfirmasi Hapus Mata Kuliah",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM menu " +
                     "WHERE id = ? " +
                     "AND nama_menu = ? " +
                     "AND kategori = ? ";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Koneksi.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, nama);
            stmt.setString(3, kategori);

            int result = stmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "Menu berhasil dihapus!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Gagal menghapus menu!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            showError("Gagal hapus menu", e);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
            loadTabelMenu();
        }
    }
    
    private void modifyMenu(int id, String nama, String kategori, int harga, String status) {
        javax.swing.JTextField txtNama = new javax.swing.JTextField(nama);
        javax.swing.JComboBox<String> txtKategori = new javax.swing.JComboBox<>(new String[]{"makanan", "snack", "minumam", "box"});
        txtKategori.setSelectedItem(kategori);
        javax.swing.JTextField txtHarga = new javax.swing.JTextField(String.valueOf(harga));
        javax.swing.JComboBox<String> cmbStatus = new javax.swing.JComboBox<>(new String[]{"tersedia", "habis"});
        cmbStatus.setSelectedItem(status);

        Object[] form = {
            "Nama:", txtNama,
            "Kategori:", txtKategori,
            "Harga:", txtHarga,
            "Status:", cmbStatus
        };

        int result = JOptionPane.showConfirmDialog(
                this, 
                form, 
                "Modify Menu", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String newNama = txtNama.getText();
        String newKategori = txtKategori.getSelectedItem().toString();
        int newHarga = Integer.parseInt(txtHarga.getText());
        String newStatus = cmbStatus.getSelectedItem().toString();

        String sql = "UPDATE menu SET nama_menu=?, kategori=?, harga=?, status=? WHERE id=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newNama);
            stmt.setString(2, newKategori);
            stmt.setInt(3, newHarga);
            stmt.setString(4, newStatus);
            stmt.setInt(5, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Menu updated!");

        } catch (Exception e) {
            showError("Gagal update menu", e);
        }
        
        loadTabelMenu();
    }
    
    private void addMenu() {
        javax.swing.JTextField txtNama = new javax.swing.JTextField();
        javax.swing.JComboBox<String> txtKategori = new javax.swing.JComboBox<>(new String[]{"makanan", "snack", "minuman", "box"});
        javax.swing.JTextField txtHarga = new javax.swing.JTextField();
        javax.swing.JComboBox<String> cmbStatus = new javax.swing.JComboBox<>(new String[]{"tersedia", "habis"});

        Object[] form = {
            "Nama:", txtNama,
            "Kategori:", txtKategori,
            "Harga:", txtHarga,
            "Status:", cmbStatus
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                "Tambah Menu",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String nama = txtNama.getText().trim();
        String kategori = txtKategori.getSelectedItem().toString();
        String hargaText = txtHarga.getText().trim();
        String status = cmbStatus.getSelectedItem().toString();

        if (nama.isEmpty() || kategori.isEmpty() || hargaText.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harus isi semua data!");
            return;
        }

        int harga;
        try {
            harga = Integer.parseInt(hargaText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!");
            return;
        }

        String sql = "INSERT INTO menu (nama_menu, kategori, harga, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nama);
            stmt.setString(2, kategori);
            stmt.setInt(3, harga);
            stmt.setString(4, status);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Menu berhasil ditambahkan!");

        } catch (Exception e) {
            showError("Gagal tambah menu", e);
        }

        loadTabelMenu();
    }
    
    private void hapusUser(int id, String un, String pass, String nama) {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Hapus user berikut?\n\n" +
            "Username: " + un + "\n" +
            "Password: " + pass + "\n" +
            "Nama: " + nama + "\n\n" +
            "Tindakan ini tidak dapat dibatalkan!",
            "Konfirmasi Hapus User",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM users " +
                     "WHERE id = ? " +
                     "AND username = ? " +
                     "AND password = ? " +
                     "AND nama = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Koneksi.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, un);
            stmt.setString(3, pass);
            stmt.setString(4, nama);

            int result = stmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "User berhasil dihapus!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Gagal menghapus user!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            showError("Gagal hapus user", e);
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
            loadTabelUser();
        }
    }
    
    private void modifyUser(int id, String un, String pass, String nama, String role) {
        javax.swing.JTextField txtUn = new javax.swing.JTextField(un);
        javax.swing.JTextField txtPass = new javax.swing.JTextField(pass);
        javax.swing.JTextField txtNama = new javax.swing.JTextField(nama);
        javax.swing.JComboBox<String> cmbRole = new javax.swing.JComboBox<>(new String[]{"admin", "pembeli"});
        cmbRole.setSelectedItem(role);

        Object[] form = {
            "Username:", txtUn, 
            "Password:", txtPass, 
            "Nama:", txtNama, 
            "Role: ", cmbRole
        };

        int result = JOptionPane.showConfirmDialog(
                this, 
                form, 
                "Modify User", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String newUn = txtUn.getText();
        String newPass = txtPass.getText();
        String newNama = txtNama.getText();
        String newRole = cmbRole.getSelectedItem().toString();

        String sql = "UPDATE users SET username=?, password=?, nama=?, role=? WHERE id=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newUn);
            stmt.setString(2, newPass);
            stmt.setString(3, newNama);
            stmt.setString(4, newRole);
            stmt.setInt(5, id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User updated!");
        } catch (Exception e) {
            showError("Gagal update user", e);
        }
        
        loadTabelUser();
    }
    private void addUser() {
        javax.swing.JTextField txtUn = new javax.swing.JTextField();
        javax.swing.JTextField txtPass = new javax.swing.JTextField();
        javax.swing.JTextField txtNama = new javax.swing.JTextField();
        javax.swing.JComboBox<String> cmbRole = new javax.swing.JComboBox<>(new String[]{"admin", "pembeli"});
        cmbRole.setSelectedItem("pembeli");

        Object[] form = {
            "Username:", txtUn, 
            "Password:", txtPass, 
            "Nama:", txtNama, 
            "Role: ", cmbRole
        };

        int result = JOptionPane.showConfirmDialog(
                this, 
                form, 
                "Modify User", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String un = txtUn.getText().trim();
        String pass = txtPass.getText().trim();
        String nama = txtNama.getText().trim();
        String role = cmbRole.getSelectedItem().toString();

        if (un.isEmpty() || pass.isEmpty() || nama.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harus isi semua data!");
            return;
        }

        String sql = "INSERT INTO users (username, password, nama, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, un);
            stmt.setString(2, pass);
            stmt.setString(3, nama);
            stmt.setString(4, role);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User berhasil ditambahkan!");

        } catch (Exception e) {
            showError("Gagal tambah user", e);
        }

        loadTabelUser();
    }
    
    private void showError(String message, Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            message + "\n\n" + e.getMessage(),
            "Database Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormDashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMenu;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnDeleteMenu;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMenuDaftarOrder;
    private javax.swing.JButton btnMenuDashboard;
    private javax.swing.JButton btnMenuKelolaMenu;
    private javax.swing.JButton btnMenuKelolaUser;
    private javax.swing.JButton btnMenuLaporan;
    private javax.swing.JButton btnMenuOrder;
    private javax.swing.JButton btnModifyMenu;
    private javax.swing.JButton btnModifyUser;
    private javax.swing.JPanel cardDaftarOrder;
    private javax.swing.JPanel cardDashboard;
    private javax.swing.JPanel cardKelolaMenu;
    private javax.swing.JPanel cardKelolaUser;
    private javax.swing.JPanel cardLaporan;
    private javax.swing.JPanel cardOrder;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDashSubtitle;
    private javax.swing.JLabel lblDashTitle;
    private javax.swing.JLabel lblRecentTitle;
    private javax.swing.JLabel lblStatDiprosesTitle;
    private javax.swing.JLabel lblStatDiprosesValue;
    private javax.swing.JLabel lblStatOrderTitle;
    private javax.swing.JLabel lblStatOrderValue;
    private javax.swing.JLabel lblStatPendapatanTitle;
    private javax.swing.JLabel lblStatPendapatanValue;
    private javax.swing.JLabel lblTitleHeader;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelSidebar;
    private javax.swing.JPanel panelStatDiproses;
    private javax.swing.JPanel panelStatOrder;
    private javax.swing.JPanel panelStatPendapatan;
    private javax.swing.JScrollPane scrollMenu;
    private javax.swing.JScrollPane scrollUser;
    private javax.swing.JTable tabelMenu;
    private javax.swing.JTable tabelUser;
    private javax.swing.JTable tblDashboardOrder;
    // End of variables declaration//GEN-END:variables
}
