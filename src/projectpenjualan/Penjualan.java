/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectpenjualan;
import java.io.File;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.StringValueExp;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import projectpenjualan.UserSession;
/**
 *
 * @author Lenovo G-40
 */
public class Penjualan extends javax.swing.JFrame {
public String idkonsumen,namakonsumen,kdbarang,barang,namabarang,harga,KDJ;
 
  

    /**
     * Creates new form Penjualan
     */
    public Penjualan() {
        initComponents();
        tampildetaildata();
        nofaktur();
        waktu();
        sum();
        TxtKembali.setText("0");
    }
   
    
     private void kosongdetailjual(){
        TxtKodeBarang.setText(null);
        TxtNamaBarang.setText(null);
        TxtHarga.setText(null);
        TxtQty.setText(null);  
        TxtSubtotal.setText(null);
    }   
    
   public void nofaktur(){
    try{
        java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
        String sql = "SELECT MAX(RIGHT(nofaktur,3)) AS No FROM penjualan";
         java.sql.PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery(sql);
        while (rs.next()) {
                if (rs.first() == false) {
                    TxtNoFaktur.setText("TRX001");
                } else {
                    rs.last();
                    int auto_id = rs.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    for (int j = 0; j < 3 - NomorJual; j++) {
                        no = "0" + no;
                    }
                    TxtNoFaktur.setText("TRX" + no);
                }
            }
        rs.close();
        }catch (Exception e){JOptionPane.showMessageDialog(null, e);}
    }
   
   
    public void waktu(){
    Thread clock=new Thread(){
        public void run(){
            for(;;){
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                 TxtTglTransaksi.setText(format.format(cal.getTime()));
                
            try { sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Penjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
      };
    clock.start();
    }
    
     public void tampildetaildata(){
        DefaultTableModel tabel=new DefaultTableModel();
        tabel.addColumn("No.");
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Harga");
        tabel.addColumn("Qty");
        tabel.addColumn("Subtotal");
        try{
            TableDetailPenjual.setModel(tabel);
        }
        catch (Exception e){
        }
    }
     
     
 public void datainputdetail(){
        DefaultTableModel tabel=new DefaultTableModel();
        tabel.addColumn("No.");
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Harga");
        tabel.addColumn("Qty");
        tabel.addColumn("Subtotal");
        try{
            java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
            String nofaktur1=TxtNoFaktur.getText();
            //String KDJ="D"+kddetailjual;
            String sql = "SELECT no,kdbarang1,namabarang,hargajual,qty,"
                    + "subtotal FROM barang JOIN detailjual ON kdbarang1=kdbarang "
                    + "WHERE nofaktur1='" + nofaktur1 + "' ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while(rs.next())
            {
                tabel.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)});
            }
            TableDetailPenjual.setModel(tabel);
        }
        catch (Exception e){
        }
    }            
     
      private boolean cekstock(String kodebarang,int qty) {
       boolean stockcukup = false;
       try {
           java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
           String sql = "SELECT stock FROM barang WHERE kdbarang = ?";
           java.sql.PreparedStatement pst = conn.prepareStatement(sql);
           pst.setString(1, kodebarang);              
           ResultSet rs = pst.executeQuery();
           //cek apakah data ditemukan 
          while (rs.next()) {
            int stock = rs.getInt("stock");
            if (stock >= qty ){
                stockcukup=true;
                }
               }
            }catch (Exception e) {
                e.printStackTrace();
       }
         return stockcukup; 
}
    
     public void KonsumenTerpilih() {
        ModalDataKonsumen kcm = new ModalDataKonsumen();
        kcm.ksm = this;
        TxtIdKonsumen.setText(idkonsumen);
        TxtNamaKonsumen.setText(namakonsumen);
    }
     
     public void BarangTerpilih() {
        ModalDataBarang bcm = new ModalDataBarang();
        bcm.brg = this;
        TxtKodeBarang.setText(kdbarang);
        TxtNamaBarang.setText(namabarang);
        TxtHarga.setText(harga);
    }
     
     public void sum(){
        int totalseluruh = 0;
        int subtotal;
        DefaultTableModel dataModel = (DefaultTableModel) TableDetailPenjual.getModel();
        int jumlah = TableDetailPenjual.getRowCount();
        for (int i=0; i<jumlah; i++){
        subtotal = Integer.parseInt(dataModel.getValueAt(i, 5).toString());
        totalseluruh += subtotal;
        }
        TxtTotal.setText(String.valueOf(totalseluruh));
    }
    
     
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        TxtKembali = new javax.swing.JTextField();
        TxtQty = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        BtnSimpan = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BtnTambah = new javax.swing.JButton();
        TxtNoFaktur = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableDetailPenjual = new javax.swing.JTable();
        TxtIdKonsumen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TxtTotal = new javax.swing.JTextField();
        TxtNamaBarang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TxtHarga = new javax.swing.JTextField();
        TxtBayar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        BtnCariKonsumen = new javax.swing.JButton();
        TxtKodeBarang = new javax.swing.JTextField();
        BtnKodeBarang = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        TxtNamaKonsumen = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TxtTglTransaksi = new javax.swing.JTextField();
        BtnHapusItem = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        TxtSubtotal = new javax.swing.JTextField();
        BtnMenu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("HARGA");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, 36));

        TxtKembali.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtKembali.setEnabled(false);
        TxtKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtKembaliActionPerformed(evt);
            }
        });
        getContentPane().add(TxtKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 500, 132, 30));

        TxtQty.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TxtQtyCaretUpdate(evt);
            }
        });
        TxtQty.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                TxtQtyHierarchyChanged(evt);
            }
        });
        TxtQty.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                TxtQtyInputMethodTextChanged(evt);
            }
        });
        TxtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtQtyActionPerformed(evt);
            }
        });
        TxtQty.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TxtQtyPropertyChange(evt);
            }
        });
        TxtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtQtyKeyReleased(evt);
            }
        });
        getContentPane().add(TxtQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 49, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("DETAIL  BARANG :");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 122, 36));

        BtnSimpan.setText("Check Out");
        BtnSimpan.setActionCommand("SimpanTransaksi");
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 450, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Qty");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, -1, 36));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("INPUT DATA PENJUALAN");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, 48));

        BtnTambah.setText("Tambah");
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        getContentPane().add(BtnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 80, -1));

        TxtNoFaktur.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtNoFaktur.setEnabled(false);
        TxtNoFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNoFakturActionPerformed(evt);
            }
        });
        getContentPane().add(TxtNoFaktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 79, 150, -1));

        TableDetailPenjual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableDetailPenjual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableDetailPenjualMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableDetailPenjual);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 294, 718, 123));

        TxtIdKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdKonsumenActionPerformed(evt);
            }
        });
        getContentPane().add(TxtIdKonsumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 70, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("NO FAKTUR ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 72, -1, 36));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("TOTAL SELURUH");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("ID KONSUMEN ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(388, 72, -1, 36));

        TxtTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtTotal.setEnabled(false);
        TxtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTotalActionPerformed(evt);
            }
        });
        getContentPane().add(TxtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 140, 30));

        TxtNamaBarang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtNamaBarang.setEnabled(false);
        TxtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNamaBarangActionPerformed(evt);
            }
        });
        getContentPane().add(TxtNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 143, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("BAYAR");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, -1, 30));

        TxtHarga.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtHarga.setEnabled(false);
        TxtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtHargaActionPerformed(evt);
            }
        });
        getContentPane().add(TxtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 210, 116, -1));

        TxtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtBayarActionPerformed(evt);
            }
        });
        getContentPane().add(TxtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, 132, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("NAMA BARANG");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, -1, 36));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("KEMBALI ");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 500, -1, 30));

        BtnCariKonsumen.setText("Cari");
        BtnCariKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariKonsumenActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCariKonsumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 63, -1));

        TxtKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtKodeBarangActionPerformed(evt);
            }
        });
        getContentPane().add(TxtKodeBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 85, -1));

        BtnKodeBarang.setText("Cari");
        BtnKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKodeBarangActionPerformed(evt);
            }
        });
        getContentPane().add(BtnKodeBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 54, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("KODE BARANG");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 91, 36));

        TxtNamaKonsumen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtNamaKonsumen.setEnabled(false);
        TxtNamaKonsumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNamaKonsumenActionPerformed(evt);
            }
        });
        getContentPane().add(TxtNamaKonsumen, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, 153, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("NAMA KONSUMEN");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(388, 114, -1, 36));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("TGL TRANSAKSI");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 114, -1, 36));

        TxtTglTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtTglTransaksi.setEnabled(false);
        TxtTglTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTglTransaksiActionPerformed(evt);
            }
        });
        getContentPane().add(TxtTglTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 121, 150, -1));

        BtnHapusItem.setText("Hapus");
        BtnHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusItemActionPerformed(evt);
            }
        });
        getContentPane().add(BtnHapusItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 80, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("SUBTOTAL");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, -1, 36));

        TxtSubtotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtSubtotal.setEnabled(false);
        TxtSubtotal.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                TxtSubtotalCaretUpdate(evt);
            }
        });
        TxtSubtotal.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                TxtSubtotalInputMethodTextChanged(evt);
            }
        });
        TxtSubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtSubtotalActionPerformed(evt);
            }
        });
        getContentPane().add(TxtSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, 109, -1));

        BtnMenu.setText("Menu");
        BtnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenuActionPerformed(evt);
            }
        });
        getContentPane().add(BtnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 480, 85, -1));

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 76, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 650, 80));

        jLabel15.setIcon(new javax.swing.ImageIcon("D:\\Tugas Java\\ProjectPBO\\ProjectPenjualan\\images\\screenshot-1705742430633.png")); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtKembaliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtKembaliActionPerformed

    private void TxtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtQtyActionPerformed
        // TODO add your handling code here:    
        int harga,qty,subtotal;
        harga =Integer.parseInt(TxtHarga.getText());
        qty =Integer.parseInt(TxtQty.getText());
        subtotal = harga*qty;
        TxtSubtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_TxtQtyActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        // TODO add your handling code here:
         try{
            String sql = "INSERT INTO penjualan VALUES('"
            +TxtNoFaktur.getText()+"','"
            +TxtTglTransaksi.getText()+"','"
            +TxtIdKonsumen.getText()+"','"
            +TxtTotal.getText()+"','"
            +TxtBayar.getText()+"','"
            +TxtKembali.getText()+"')";
            java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null , "Data Penjualan Berhasil Di Simpan !");
            int ok = JOptionPane.showConfirmDialog(null,"Apakah Faktur Penjualan Akan Di Cetak ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
             if (ok==0){
                HashMap hash = new HashMap();
                hash.put("vnofaktur", TxtNoFaktur.getText());
                hash.put("username", UserSession.getUserPengguna());
                JasperPrint jp = JasperFillManager.fillReport(getClass().
                        getResourceAsStream("FakturPenjualan.jasper"),hash,Koneksi.koneksiDB());    
                JasperViewer.viewReport(jp, false);
            }    
            TxtIdKonsumen.setText("");
            TxtNamaKonsumen.setText("");
            TxtTotal.setText("");
            TxtBayar.setText("");
            TxtKembali.setText("0");
            tampildetaildata();
            nofaktur();
            sum();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Penjualan Gagal Di Simpan!");
            System.out.print(e.getMessage());
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        // TODO add your handling code here:
  try {  
        String kodebarang = TxtKodeBarang.getText();
        int qty = Integer.parseInt(TxtQty.getText());
       if(cekstock(kodebarang,qty)){         
            String nofaktur1=TxtNoFaktur.getText();
            int no=0;
            //KDJ="D"+kddetailjual;
            String sql = "INSERT INTO detailjual VALUES('"
            +no++ +"','"
            +nofaktur1+"','"
            +TxtKodeBarang.getText()+"','"
            +TxtHarga.getText()+"','"
            +TxtQty.getText()+"','"
            +TxtSubtotal.getText()+"')";
            java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null , "Data Barang Berhasil Di Tambah !");
            datainputdetail();
            kosongdetailjual();
            sum();
             }
            else{
               JOptionPane.showMessageDialog(null , "Maaf,Barang Tidak Bisa Di Tambahkan,Karena Stock Barang Melewati Batas Maximun !");
               kosongdetailjual();
                }
             }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Barang Gagal Di Tambah !");
            System.out.print(e.getMessage());
      }
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void TxtNoFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNoFakturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNoFakturActionPerformed

    private void TableDetailPenjualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableDetailPenjualMouseClicked
        // TODO add your handling code here:
        // menampilkan data kedalam form pengisian:
        int baris = TableDetailPenjual.rowAtPoint(evt.getPoint());
        String kdbarang =TableDetailPenjual.getValueAt(baris, 1).toString();
        TxtKodeBarang.setText(kdbarang);
        String namabarang = TableDetailPenjual.getValueAt(baris,2).toString();
        TxtNamaBarang.setText(namabarang);
        String qty = TableDetailPenjual.getValueAt(baris, 3).toString();
        TxtHarga.setText(qty);
        String harga = TableDetailPenjual.getValueAt(baris, 4).toString();
        TxtQty.setText(harga);
        String subtotal= TableDetailPenjual.getValueAt(baris, 5).toString();
        TxtSubtotal.setText(subtotal);
    }//GEN-LAST:event_TableDetailPenjualMouseClicked

    private void TxtIdKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdKonsumenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIdKonsumenActionPerformed

    private void TxtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNamaBarangActionPerformed

    private void TxtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtHargaActionPerformed

    private void TxtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtBayarActionPerformed
        // TODO add your handling code here:
      if(Integer.valueOf(TxtBayar.getText()) >= Integer.valueOf(TxtTotal.getText())) {
        int total,bayar,kembali;
        total =Integer.parseInt(TxtTotal.getText());
        bayar =Integer.parseInt(TxtBayar.getText());
        kembali = bayar-total;
        TxtKembali.setText(String.valueOf(kembali));
         } else {
            JOptionPane.showMessageDialog(null, "Uang Tidak Cukup Untuk Melakukan Pembayaran !");
            TxtBayar.setText("");
      }
    }//GEN-LAST:event_TxtBayarActionPerformed

    private void BtnCariKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariKonsumenActionPerformed
        // TODO add your handling code here:
        ModalDataKonsumen mck = new ModalDataKonsumen();
        mck.ksm = this;
        mck.setVisible(true);
        mck.setResizable(false);
    }//GEN-LAST:event_BtnCariKonsumenActionPerformed

    private void TxtKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtKodeBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtKodeBarangActionPerformed

    private void BtnKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKodeBarangActionPerformed
        // TODO add your handling code here:
        ModalDataBarang mcb = new ModalDataBarang();
        mcb.brg = this;
        mcb.setVisible(true);
        mcb.setResizable(false);
    }//GEN-LAST:event_BtnKodeBarangActionPerformed

    private void TxtNamaKonsumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNamaKonsumenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNamaKonsumenActionPerformed

    private void TxtTglTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTglTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTglTransaksiActionPerformed

    private void BtnHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusItemActionPerformed
        // TODO add your handling code here:
        // fungsi hapus data
        int row = TableDetailPenjual.getSelectedRow();
        try {
            String sql ="DELETE FROM detailjual WHERE no='"+TableDetailPenjual.getModel().getValueAt(row, 0)+"'";
            java.sql.Connection conn=(Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Barang Berhasil Di Hapus !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        datainputdetail();
        kosongdetailjual();
        sum();
    }//GEN-LAST:event_BtnHapusItemActionPerformed

    private void TxtSubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtSubtotalActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TxtSubtotalActionPerformed

    private void TxtSubtotalInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_TxtSubtotalInputMethodTextChanged
        // TODO add your handling code here:
       
    }//GEN-LAST:event_TxtSubtotalInputMethodTextChanged

    private void TxtSubtotalCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TxtSubtotalCaretUpdate
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TxtSubtotalCaretUpdate

    private void TxtQtyHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_TxtQtyHierarchyChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TxtQtyHierarchyChanged

    private void TxtQtyCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_TxtQtyCaretUpdate
        // TODO add your handling code here:
       
    }//GEN-LAST:event_TxtQtyCaretUpdate

    private void TxtQtyPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TxtQtyPropertyChange
        // TODO add your handling code here:
       
    }//GEN-LAST:event_TxtQtyPropertyChange

    private void TxtQtyInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_TxtQtyInputMethodTextChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TxtQtyInputMethodTextChanged

    private void BtnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenuActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_BtnMenuActionPerformed

    private void TxtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtQtyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtQtyKeyReleased

    private void TxtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTotalActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCariKonsumen;
    private javax.swing.JButton BtnHapusItem;
    private javax.swing.JButton BtnKodeBarang;
    private javax.swing.JButton BtnMenu;
    private javax.swing.JButton BtnSimpan;
    private javax.swing.JButton BtnTambah;
    private javax.swing.JTable TableDetailPenjual;
    private javax.swing.JTextField TxtBayar;
    private javax.swing.JTextField TxtHarga;
    private javax.swing.JTextField TxtIdKonsumen;
    private javax.swing.JTextField TxtKembali;
    private javax.swing.JTextField TxtKodeBarang;
    private javax.swing.JTextField TxtNamaBarang;
    private javax.swing.JTextField TxtNamaKonsumen;
    private javax.swing.JTextField TxtNoFaktur;
    private javax.swing.JTextField TxtQty;
    private javax.swing.JTextField TxtSubtotal;
    private javax.swing.JTextField TxtTglTransaksi;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
