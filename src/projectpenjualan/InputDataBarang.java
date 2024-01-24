/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectpenjualan;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.*;
//import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import projectpenjualan.UserSession;

/**
 *
 * @author Lenovo G-40
 */
public class InputDataBarang extends javax.swing.JFrame {
DefaultTableModel tabel;
ResultSet rs=null;




    /**
     * Creates new form InputDataBarang
     */
    public InputDataBarang() {
        initComponents();
        TxtNamaBarang.requestFocus();
        tampil_data();
        kosong();
        kodebarang();
    }
    
    
 private void kosong(){
        //TxtKodeBarang.setText(null);
        TxtNamaBarang.setText(null);
        TxtStock.setText(null);
        TxtHarga.setText(null);  
    }   
 
  private void kodebarang()
    {
       try {
            java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
            String sql ="SELECT * FROM barang ORDER BY kdbarang DESC";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                String kdbarang = rs.getString("kdbarang").substring(1);
                String AN = "" + (Integer.parseInt(kdbarang) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "00";}
                else if(AN.length()==2)
                {Nol = "0";}
                else if(AN.length()==3)
                {Nol = "";}

               TxtKodeBarang.setText("B" + Nol + AN);
            } else {
               TxtKodeBarang.setText("B001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
     }
 
  
  
 
    public void tampil_data(){
        DefaultTableModel tabel=new DefaultTableModel();
        tabel.addColumn("Kode Barang");
        tabel.addColumn("Nama Barang");
        tabel.addColumn("Stock");
        tabel.addColumn("Harga");
        try{
            java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
            String sql = "SELECT * FROM barang";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while(rs.next())
            {
                tabel.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)});
            }
            TableDataBarang.setModel(tabel);
        }
        catch (Exception e){
        }
    }
    
    private void cariData(String key){
        try{
            Object[] judul_kolom = {"Kode Barang", 
                "Nama Barang", 
                "Stock", 
                "Harga"};
            tabel=new DefaultTableModel(null,judul_kolom);
            TableDataBarang.setModel(tabel);
            
            Connection conn=(Connection)Koneksi.koneksiDB();
            Statement stt=conn.createStatement();
            tabel.getDataVector().removeAllElements();
            
            rs=stt.executeQuery("SELECT * FROM barang WHERE kdbarang "
                    + "LIKE '%"+key+"%' OR namabarang LIKE '%"+key+"%'");  
            while(rs.next()){
                Object[] data={
                    rs.getString("kdbarang"),
                    rs.getString("namabarang"),
                    rs.getString("stock"),
                    rs.getString("harga")         
                };
               tabel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TxtKodeBarang = new javax.swing.JTextField();
        TxtNamaBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TxtStock = new javax.swing.JTextField();
        TxtCariDataBarang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        BtnTambah = new javax.swing.JButton();
        BtnSimpan = new javax.swing.JButton();
        BtnHapus = new javax.swing.JButton();
        BtnUpdate = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableDataBarang = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        TxtHarga = new javax.swing.JTextField();
        BtnMenu = new javax.swing.JButton();
        BtnCetak = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText(" INPUT DATA BARANG");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 8, -1, 48));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("KODE BARANG");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 74, -1, 36));

        TxtKodeBarang.setEditable(false);
        TxtKodeBarang.setBackground(new java.awt.Color(255, 255, 255));
        TxtKodeBarang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TxtKodeBarang.setEnabled(false);
        TxtKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtKodeBarangActionPerformed(evt);
            }
        });
        getContentPane().add(TxtKodeBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 81, 147, -1));

        TxtNamaBarang.setText(" ");
        TxtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNamaBarangActionPerformed(evt);
            }
        });
        getContentPane().add(TxtNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 123, 147, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("NAMA BARANG");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 116, -1, 36));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("STOCK");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 74, -1, 36));
        getContentPane().add(TxtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 81, 47, -1));

        TxtCariDataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCariDataBarangActionPerformed(evt);
            }
        });
        TxtCariDataBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtCariDataBarangKeyReleased(evt);
            }
        });
        getContentPane().add(TxtCariDataBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 146, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("CARI DATA BARANG");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, 36));

        BtnTambah.setText("Tambah");
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        getContentPane().add(BtnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        BtnSimpan.setText("Simpan");
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        getContentPane().add(BtnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, -1, -1));

        BtnHapus.setText("Hapus");
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        getContentPane().add(BtnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, -1, -1));

        BtnUpdate.setText("Update");
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(BtnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, -1, -1));

        TableDataBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        TableDataBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableDataBarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableDataBarang);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 521, 123));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("HARGA");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 116, -1, 36));
        getContentPane().add(TxtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 123, 138, -1));

        BtnMenu.setText("Menu");
        BtnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenuActionPerformed(evt);
            }
        });
        getContentPane().add(BtnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, -1, -1));

        BtnCetak.setText("Cetak");
        BtnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCetakActionPerformed(evt);
            }
        });
        getContentPane().add(BtnCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon("D:\\Tugas Java\\ProjectPBO\\ProjectPenjualan\\images\\screenshot-1705742430633.png")); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 420));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtKodeBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtKodeBarangActionPerformed

    private void TxtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNamaBarangActionPerformed

    private void TxtCariDataBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtCariDataBarangKeyReleased
        // TODO add your handling code here:/
        //cari data
        String key=TxtCariDataBarang.getText();
        System.out.println(key);

        if(key!=""){
            cariData(key);
        }else{
            tampil_data();
        }
    }//GEN-LAST:event_TxtCariDataBarangKeyReleased

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        // TODO add your handling code here:
        TxtNamaBarang.requestFocus();
        kosong();
        kodebarang();
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        // TODO add your handling code here:
        // fungsi simpan data
        try{
            String sql = "INSERT INTO barang VALUES('"
            +TxtKodeBarang.getText()+"','"
            +TxtNamaBarang.getText()+"','"
            +TxtStock.getText()+"','"
            +TxtHarga.getText()+"')";
            java.sql.Connection conn =(java.sql.Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null , "Data Barang Berhasil Di Simpan !");
            tampil_data();
            kosong();
            kodebarang();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Barang Gagal Di Simpan !");
            System.out.print(e.getMessage());
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        // TODO add your handling code here:
        // fungsi hapus data
        try {
            String sql ="DELETE FROM barang WHERE kdbarang='"+TxtKodeBarang.getText()+"'";
            java.sql.Connection conn=(Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Barang Berhasil Di Hapus !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        tampil_data();
        kosong();
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        // TODO add your handling code here:
        // Edit Data
        try {
            String sql ="UPDATE barang SET "
            + "namabarang = '"+TxtNamaBarang.getText()+"', "
            + "stock = '"+TxtStock.getText()+"', "
            + "harga= '"+TxtHarga.getText()+"' "
            + "WHERE kdbarang= '"+TxtKodeBarang.getText()+"'";
            java.sql.Connection conn=(Connection)Koneksi.koneksiDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Barang Berhasil Di Update !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update Data Barang Gagal"+e.getMessage());
        }
        tampil_data();
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void TableDataBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableDataBarangMouseClicked
        // TODO add your handling code here:
        // menampilkan data kedalam form pengisian:
        int baris = TableDataBarang.rowAtPoint(evt.getPoint());
        String kdbarang =TableDataBarang.getValueAt(baris, 0).toString();
        TxtKodeBarang.setText(kdbarang);
        String namabarang = TableDataBarang.getValueAt(baris,1).toString();
        TxtNamaBarang.setText(namabarang);
        String stock = TableDataBarang.getValueAt(baris, 2).toString();
        TxtStock.setText(stock);
        String harga = TableDataBarang.getValueAt(baris, 3).toString();
        TxtHarga.setText(harga);
    }//GEN-LAST:event_TableDataBarangMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
       
    }//GEN-LAST:event_formComponentShown

    private void TxtCariDataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCariDataBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCariDataBarangActionPerformed

    private void BtnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenuActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_BtnMenuActionPerformed

    private void BtnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCetakActionPerformed
        // TODO add your handling code here:
          try {
          HashMap hash = new HashMap();
          hash.put("username", UserSession.getUserPengguna());
          JasperPrint jp = JasperFillManager.fillReport(getClass().
                  getResourceAsStream("ReportDataBarang.jasper"),hash,Koneksi.koneksiDB());
          
            JasperViewer.viewReport(jp, false);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_BtnCetakActionPerformed

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
            java.util.logging.Logger.getLogger(InputDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InputDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InputDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InputDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InputDataBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCetak;
    private javax.swing.JButton BtnHapus;
    private javax.swing.JButton BtnMenu;
    private javax.swing.JButton BtnSimpan;
    private javax.swing.JButton BtnTambah;
    private javax.swing.JButton BtnUpdate;
    private javax.swing.JTable TableDataBarang;
    private javax.swing.JTextField TxtCariDataBarang;
    private javax.swing.JTextField TxtHarga;
    private javax.swing.JTextField TxtKodeBarang;
    private javax.swing.JTextField TxtNamaBarang;
    private javax.swing.JTextField TxtStock;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
