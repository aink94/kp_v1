/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp_v1;

import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Faisal Abdul Hamid
 */
public class main extends javax.swing.JFrame {

    /**
     * Creates new form main_v2
     */
    public main() {
        initComponents();
        panel_barang_keluar.setVisible(false);
        panel_barang_masuk.setVisible(true);
        panel_tipe.setVisible(false);
        panel_jenis.setVisible(false);
        
        load_table_tipe();
        load_table_jenis();
        load_table_barangkeluar();
        load_table_barangmasuk();
        cbx_kopi_keluar();
        cbx_kopi_masuk();
        autokode_masuk();
        autokode_keluar();
    }
    
    private void cbx_kopi_keluar(){
             try {
                    cb_kopi2.removeAllItems();
                    String quary="SELECT kd_kopi,CONCAT(nama_jeniskopi,' ',nama_tipekopi) FROM tipe_kopi JOIN kopi USING (kd_tipekopi) JOIN jenis_kopi USING (kd_jeniskopi)";
                    Connection conn=(Connection)koneksi.Koneksi.konekDB();
                    java.sql.Statement stm=conn.createStatement();
                    java.sql.ResultSet res=stm.executeQuery(quary);
                    cb_kopi2.addItem("                                                       ------PILIH------");
                    while(res.next()){
                        cb_kopi2.addItem(res.getString(1)+" - "+res.getString(2));
            }
        } catch (Exception e) {}
          load_table_barangkeluar();  
    }
    
    private void cbx_kopi_masuk(){
        try {
            cb_kopi.removeAllItems();
            String quary="SELECT kd_kopi,CONCAT(nama_jeniskopi,' ',nama_tipekopi) FROM tipe_kopi JOIN kopi USING (kd_tipekopi) JOIN jenis_kopi USING (kd_jeniskopi)";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(quary);
            cb_kopi.addItem("                                                       ------PILIH------");
            while(res.next()){
                cb_kopi.addItem(res.getString(1)+" - "+res.getString(2));
            }
        } catch (Exception e) {}
        load_table_barangmasuk();  
    }
    
    private boolean load_table_jenis(){
        DefaultTableModel tm=new DefaultTableModel();
        tm.addColumn("Kode Jenis Kopi");
        tm.addColumn("Nama Jenis Kopi");
        try {
            String query="SELECT * FROM jenis_kopi";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(query);
            while(res.next()){
                tm.addRow(new Object[]{res.getString(1),res.getString(2)});
            }
            tblbarangmasuk3.setModel(tm);
            return false;
        } catch (Exception e) {
        }
        return false;
    }
    private boolean load_table_tipe(){
        DefaultTableModel tm=new DefaultTableModel();
        tm.addColumn("Kode Tipe Kopi");
        tm.addColumn("Nama Tipe Kopi");
        try {
            String query="SELECT * FROM tipe_kopi";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(query);
            while(res.next()){
                tm.addRow(new Object[]{res.getString(1),res.getString(2)});
            }
            tblbarangmasuk4.setModel(tm);
            return false;
        } catch (Exception e) {
        }
        return false;
    }
    private boolean load_table_barangmasuk(){
        DefaultTableModel tm=new DefaultTableModel();
        tm.addColumn("Kode");
        tm.addColumn("Kopi");
        tm.addColumn("Tanggal");
        tm.addColumn("Stok Keluar");
        try {
            String query="SELECT kd_barangmasuk,CONCAT(nama_jeniskopi,' ',nama_tipekopi),tgl_masuk,stok_masuk FROM (barang_masuk JOIN kopi USING (kd_kopi) JOIN jenis_kopi USING (kd_jeniskopi)) JOIN tipe_kopi USING (kd_tipekopi)";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(query);
            while(res.next()){
                tm.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),res.getString(4)});
            }
            tblbarangmasuk.setModel(tm);
            return false;
        } catch (Exception e) {}
        return false;
    }
    private boolean load_table_barangkeluar(){
        DefaultTableModel tm=new DefaultTableModel();
        tm.addColumn("Kode");
        tm.addColumn("Kopi");
        tm.addColumn("Tanggal");
        tm.addColumn("Stok Keluar");
        try {
            String query="SELECT kd_barangkeluar,CONCAT(nama_jeniskopi,' ',nama_tipekopi),tgl_keluar,stok_keluar FROM (barang_keluar JOIN kopi USING (kd_kopi) JOIN jenis_kopi USING (kd_jeniskopi)) JOIN tipe_kopi USING (kd_tipekopi)";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(query);
            while(res.next()){
                tm.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),res.getString(4)});
            }
            tblbarangmasuk2.setModel(tm);
            return false;
        } catch (Exception e) {}
        return false;
    }
    
    private void updatestok_masuk(){
        String cbx_kopi=(String)this.cb_kopi.getSelectedItem();
        String kopi=cbx_kopi.substring(0,5);
        String stok_masuk=txt_stock.getText();
        String sqlstok="select stok from kopi where kd_kopi='"+kopi+"'";
        try{
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sqlstok);
            while(res.next()){
               String stokawal=res.getString(1);
               int stok=(int) (Double.parseDouble(stok_masuk)+ Double.parseDouble(stokawal));
               String query="update kopi set stok='"+stok+"' where kd_kopi='"+kopi+"'";
               try{
                   java.sql.PreparedStatement pst=conn.prepareStatement(query);
                    pst.executeUpdate(query);
               } catch (Exception e){
                  JOptionPane.showMessageDialog(null,"Terjadi Kesalahan "+e.getMessage()); 
               }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Terjadi Kesalahan "+e.getMessage()); 
        }
    }
    
    private void autokode_masuk(){
        try{
            String query="select max(right((kd_barangmasuk),3)) AS kode from barang_masuk";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
                java.sql.Statement stm=conn.createStatement();
                java.sql.ResultSet res=stm.executeQuery(query);
                while(res.next()){
                    if(res.first()==false){
                        txt_kode.setText("BM0001");
                    }else{
                        res.last();
                        int autokode = res.getInt(1) + 1;
                        String nomor=String.valueOf(autokode);
                        int noLong = nomor.length();
                        for(int a=0;a<4-noLong;a++){
                            nomor = "0" + nomor;
                        }
                        txt_kode.setText("BM" + nomor);
                    }
                }
        }catch(Exception e){
             JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                                "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updatestok_keluar(){
        String cbx_kopi=(String)this.cb_kopi2.getSelectedItem();
        String kopi=cbx_kopi.substring(0,5);
        String jml_keluar=txt_stock2.getText();
        String sqlstok="select stok from kopi where kd_kopi='"+kopi+"'";
        try{
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sqlstok);
            while(res.next()){
               String stokawal=res.getString(1);
               int stok1=(int) Double.parseDouble(stokawal);
               int stok2=(int) Double.parseDouble(jml_keluar);
               int hasil=stok1-stok2;
               String query="update kopi set stok='"+hasil+"' where kd_kopi='"+kopi+"'";         
               try{
                   java.sql.PreparedStatement pst=conn.prepareStatement(query);
                    pst.executeUpdate(query);
               } catch (Exception e){
                  JOptionPane.showMessageDialog(null,"Terjadi Kesalahan "+e.getMessage()); 
               }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Terjadi Kesalahan "+e.getMessage()); 
        }
    }
    
    private void autokode_keluar(){
    try{
        String query="select max(right((kd_barangmasuk),3)) AS kode from barang_masuk";
        Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(query);
            while(res.next()){
                if(res.first()==false){
                    txt_kode2.setText("BK0001");
                }else{
                    res.last();
                    int autokode = res.getInt(1) + 1;
                    String nomor=String.valueOf(autokode);
                    int noLong = nomor.length();
                    for(int a=0;a<4-noLong;a++){
                        nomor = "0" + nomor;
                    }
                    txt_kode2.setText("BK" + nomor);
                }
            }
    }catch(Exception e){
         JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                            "Kesalahan", JOptionPane.WARNING_MESSAGE);
    }
}
    
    public void masuk(){
        panel_barang_keluar.setVisible(false);
        panel_barang_masuk.setVisible(true);
        panel_tipe.setVisible(false);
        panel_jenis.setVisible(false);
    }
    public void keluar(){
        panel_barang_keluar.setVisible(true);
        panel_barang_masuk.setVisible(false);
        panel_tipe.setVisible(false);
        panel_jenis.setVisible(false);
    }
    public void tipe(){
        panel_barang_keluar.setVisible(false);
        panel_barang_masuk.setVisible(false);
        panel_tipe.setVisible(true);
        panel_jenis.setVisible(false);
    }
    public void jenis(){
        panel_barang_keluar.setVisible(false);
        panel_barang_masuk.setVisible(false);
        panel_tipe.setVisible(false);
        panel_jenis.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_barang_masuk = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_kode = new javax.swing.JTextField();
        txt_tgl = new javax.swing.JTextField();
        cb_kopi = new javax.swing.JComboBox();
        txt_stock = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbarangmasuk = new javax.swing.JTable();
        panel_barang_keluar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_kode2 = new javax.swing.JTextField();
        txt_tgl2 = new javax.swing.JTextField();
        cb_kopi2 = new javax.swing.JComboBox();
        txt_stock2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblbarangmasuk2 = new javax.swing.JTable();
        panel_jenis = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_kode_tipe = new javax.swing.JTextField();
        txt_nama_tipe = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblbarangmasuk3 = new javax.swing.JTable();
        panel_tipe = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_kode_jenis1 = new javax.swing.JTextField();
        txt_nama_jenis1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblbarangmasuk4 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menu_jenis_kopi = new javax.swing.JMenuItem();
        menu_tipe_kopi = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menu_barang_masuk = new javax.swing.JMenuItem();
        menu_barang_keluar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setText("BARANG MASUK");

        jLabel1.setText("Kode");

        jLabel17.setText("Kopi");

        jLabel18.setText("Tanggal");

        jLabel19.setText("Stock");

        cb_kopi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblbarangmasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode", "Kopi", "Tanggal Keluar", "Stok Keluar"
            }
        ));
        tblbarangmasuk.setEnabled(false);
        tblbarangmasuk.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tblbarangmasuk);

        javax.swing.GroupLayout panel_barang_masukLayout = new javax.swing.GroupLayout(panel_barang_masuk);
        panel_barang_masuk.setLayout(panel_barang_masukLayout);
        panel_barang_masukLayout.setHorizontalGroup(
            panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_barang_masukLayout.createSequentialGroup()
                .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_barang_masukLayout.createSequentialGroup()
                        .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_barang_masukLayout.createSequentialGroup()
                                .addGap(231, 231, 231)
                                .addComponent(jLabel2))
                            .addGroup(panel_barang_masukLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panel_barang_masukLayout.createSequentialGroup()
                                        .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_barang_masukLayout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(39, 39, 39))
                                            .addGroup(panel_barang_masukLayout.createSequentialGroup()
                                                .addComponent(jLabel17)
                                                .addGap(43, 43, 43)))
                                        .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_kode)
                                            .addComponent(cb_kopi, 0, 145, Short.MAX_VALUE)))
                                    .addGroup(panel_barang_masukLayout.createSequentialGroup()
                                        .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel19))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_stock)
                                            .addComponent(txt_tgl, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                                    .addComponent(jButton1))))
                        .addGap(0, 221, Short.MAX_VALUE))
                    .addGroup(panel_barang_masukLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_barang_masukLayout.setVerticalGroup(
            panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_barang_masukLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(11, 11, 11)
                .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cb_kopi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_barang_masukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel5.setText("BARANG KELUAR");

        jLabel6.setText("Kode");

        jLabel23.setText("Kopi");

        jLabel24.setText("Tanggal");

        jLabel25.setText("Stock");

        cb_kopi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton3.setText("Tambah");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tblbarangmasuk2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode", "Kopi", "Tanggal Keluar", "Stok Keluar"
            }
        ));
        tblbarangmasuk2.setEnabled(false);
        tblbarangmasuk2.setFillsViewportHeight(true);
        jScrollPane3.setViewportView(tblbarangmasuk2);

        javax.swing.GroupLayout panel_barang_keluarLayout = new javax.swing.GroupLayout(panel_barang_keluar);
        panel_barang_keluar.setLayout(panel_barang_keluarLayout);
        panel_barang_keluarLayout.setHorizontalGroup(
            panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_barang_keluarLayout.createSequentialGroup()
                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_barang_keluarLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel_barang_keluarLayout.createSequentialGroup()
                                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_barang_keluarLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(39, 39, 39))
                                    .addGroup(panel_barang_keluarLayout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(43, 43, 43)))
                                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_kode2)
                                    .addComponent(cb_kopi2, 0, 145, Short.MAX_VALUE)))
                            .addGroup(panel_barang_keluarLayout.createSequentialGroup()
                                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_stock2)
                                    .addComponent(txt_tgl2, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(0, 299, Short.MAX_VALUE))
                    .addGroup(panel_barang_keluarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_barang_keluarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(207, 207, 207))
        );
        panel_barang_keluarLayout.setVerticalGroup(
            panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_barang_keluarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_kode2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cb_kopi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tgl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_barang_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_stock2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel7.setText("JENIS KOPI");

        jLabel27.setText("Kode Tipe Kopi");

        jLabel28.setText("Nama Tipe  Kopi");

        jButton4.setText("Tambah");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tblbarangmasuk3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Kode Jenis Kopi", "Nama Jenis Kopi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblbarangmasuk3.setEnabled(false);
        tblbarangmasuk3.setFillsViewportHeight(true);
        jScrollPane4.setViewportView(tblbarangmasuk3);

        javax.swing.GroupLayout panel_jenisLayout = new javax.swing.GroupLayout(panel_jenis);
        panel_jenis.setLayout(panel_jenisLayout);
        panel_jenisLayout.setHorizontalGroup(
            panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_jenisLayout.createSequentialGroup()
                .addGroup(panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_jenisLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
                    .addGroup(panel_jenisLayout.createSequentialGroup()
                        .addGap(251, 251, 251)
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panel_jenisLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4)
                    .addGroup(panel_jenisLayout.createSequentialGroup()
                        .addGroup(panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addGap(25, 25, 25)
                        .addGroup(panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nama_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_kode_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_jenisLayout.setVerticalGroup(
            panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_jenisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kode_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_jenisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nama_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel8.setText("TIPE KOPI");

        jLabel29.setText("Kode Jenis Kopi");

        jLabel30.setText("Nama Jenis Kopi");

        jButton5.setText("Tambah");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tblbarangmasuk4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Kode Jenis Kopi", "Nama Jenis Kopi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblbarangmasuk4.setEnabled(false);
        tblbarangmasuk4.setFillsViewportHeight(true);
        jScrollPane5.setViewportView(tblbarangmasuk4);

        javax.swing.GroupLayout panel_tipeLayout = new javax.swing.GroupLayout(panel_tipe);
        panel_tipe.setLayout(panel_tipeLayout);
        panel_tipeLayout.setHorizontalGroup(
            panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tipeLayout.createSequentialGroup()
                .addGroup(panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_tipeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
                    .addGroup(panel_tipeLayout.createSequentialGroup()
                        .addGap(251, 251, 251)
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panel_tipeLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5)
                    .addGroup(panel_tipeLayout.createSequentialGroup()
                        .addGroup(panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))
                        .addGap(25, 25, 25)
                        .addGroup(panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nama_jenis1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_kode_jenis1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_tipeLayout.setVerticalGroup(
            panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tipeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kode_jenis1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nama_jenis1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Master");

        menu_jenis_kopi.setText("Jenis Kopi");
        menu_jenis_kopi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_jenis_kopiActionPerformed(evt);
            }
        });
        jMenu1.add(menu_jenis_kopi);

        menu_tipe_kopi.setText("Tipe Kopi");
        menu_tipe_kopi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_tipe_kopiActionPerformed(evt);
            }
        });
        jMenu1.add(menu_tipe_kopi);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Transaksi");

        menu_barang_masuk.setText("Barang Masuk");
        menu_barang_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_barang_masukActionPerformed(evt);
            }
        });
        jMenu2.add(menu_barang_masuk);

        menu_barang_keluar.setText("Barang Keluar");
        menu_barang_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_barang_keluarActionPerformed(evt);
            }
        });
        jMenu2.add(menu_barang_keluar);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_barang_masuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_barang_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_jenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_tipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(20, 20, 20)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_barang_masuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(panel_barang_keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_jenis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(21, 21, 21)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_tipe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(43, 43, 43)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_barang_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_barang_masukActionPerformed
        // TODO add your handling code here:
        masuk();
    }//GEN-LAST:event_menu_barang_masukActionPerformed

    private void menu_jenis_kopiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_jenis_kopiActionPerformed
        // TODO add your handling code here:
        jenis();
    }//GEN-LAST:event_menu_jenis_kopiActionPerformed

    private void menu_tipe_kopiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_tipe_kopiActionPerformed
        // TODO add your handling code here:
        tipe();
    }//GEN-LAST:event_menu_tipe_kopiActionPerformed

    private void menu_barang_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_barang_keluarActionPerformed
        // TODO add your handling code here:
        keluar();
    }//GEN-LAST:event_menu_barang_keluarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(txt_kode_tipe.getText().equals("") && txt_nama_tipe.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Masih ada field yang belum terisi");
        }else{
            try{
                String kode = (String)this.txt_kode_tipe.getText();
                String nama = (String)this.txt_nama_tipe.getText();
                String query="insert into jenis_kopi values ('"+kode+"','"+nama+"')";
                Connection conn=(Connection)koneksi.Koneksi.konekDB();
                java.sql.PreparedStatement pst=conn.prepareStatement(query);
                pst.execute();
                JOptionPane.showMessageDialog(null,"Transaksi berhasil di tambah");
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Transaksi gagal di tambah"+e.getMessage());
            }
        }
        load_table_jenis();
        txt_kode_tipe.setText("");
        txt_nama_tipe.setText("");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(txt_kode_jenis1.getText().equals("") && txt_nama_jenis1.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Masih ada field yang belum terisi");
        }else{
            try{
                String kode = (String)this.txt_kode_jenis1.getText();
                String nama = (String)this.txt_nama_jenis1.getText();
                String query="insert into tipe_kopi values ('"+kode+"','"+nama+"')";
                Connection conn=(Connection)koneksi.Koneksi.konekDB();
                java.sql.PreparedStatement pst=conn.prepareStatement(query);
                pst.execute();
                JOptionPane.showMessageDialog(null,"Transaksi berhasil di tambah");
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Transaksi gagal di tambah"+e.getMessage());
            }
        }
        load_table_tipe();
        txt_kode_jenis1.setText("");
        txt_nama_jenis1.setText("");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(cb_kopi.getSelectedItem().equals("                             ------PILIH------") || txt_stock.getText().equals("") || txt_tgl.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Masih ada field yang belum terisi");
        }else{
        try{
            user u = new user();
            String cbx_kopi=(String)this.cb_kopi.getSelectedItem();
            String kopi=cbx_kopi.substring(0,5);
            String query="insert into barang_masuk (kd_barangmasuk,kd_kopi,tgl_masuk,stok_masuk, id_user) values ('"+txt_kode.getText()+"','"+kopi+"','"+txt_tgl.getText()+"','"+txt_stock.getText()+"', '"+u.get_id_user()+"')";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(query);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Transaksi berhasil di tambah "+u.get_id_user());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Transaksi gagal di tambah"+e.getMessage());
        }
        }
        updatestok_masuk();
        txt_kode.setText("");
        cb_kopi.setSelectedItem("                             ------PILIH------");
        txt_stock.setText("");
        load_table_barangmasuk();
        autokode_masuk(); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(cb_kopi2.getSelectedItem().equals("                             ------PILIH------") || txt_stock2.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Masih ada field yang belum terisi");
        }else{
        try{
            String cbx_kopi=(String)this.cb_kopi2.getSelectedItem();
            String kopi=cbx_kopi.substring(0,5);
            String query="insert into barang_keluar (kd_barangkeluar,kd_kopi,tgl_keluar,stok_keluar) values ('"+txt_kode2.getText()+"','"+kopi+"','"+txt_tgl2.getText()+"','"+txt_stock2.getText()+"')";
            Connection conn=(Connection)koneksi.Koneksi.konekDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(query);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Transaksi berhasil di tambah");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Transaksi gagal di tambah"+e.getMessage());
        }
        }
        updatestok_keluar();
        txt_kode2.setText("");
        cb_kopi2.setSelectedItem("                             ------PILIH------");
        txt_tgl2.setText("");
        txt_stock2.setText("");
        load_table_barangkeluar();
        autokode_keluar();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cb_kopi;
    private javax.swing.JComboBox cb_kopi2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JMenuItem menu_barang_keluar;
    private javax.swing.JMenuItem menu_barang_masuk;
    private javax.swing.JMenuItem menu_jenis_kopi;
    private javax.swing.JMenuItem menu_tipe_kopi;
    private javax.swing.JPanel panel_barang_keluar;
    private javax.swing.JPanel panel_barang_masuk;
    private javax.swing.JPanel panel_jenis;
    private javax.swing.JPanel panel_tipe;
    private javax.swing.JTable tblbarangmasuk;
    private javax.swing.JTable tblbarangmasuk2;
    private javax.swing.JTable tblbarangmasuk3;
    private javax.swing.JTable tblbarangmasuk4;
    private javax.swing.JTextField txt_kode;
    private javax.swing.JTextField txt_kode2;
    private javax.swing.JTextField txt_kode_jenis1;
    private javax.swing.JTextField txt_kode_tipe;
    private javax.swing.JTextField txt_nama_jenis1;
    private javax.swing.JTextField txt_nama_tipe;
    private javax.swing.JTextField txt_stock;
    private javax.swing.JTextField txt_stock2;
    private javax.swing.JTextField txt_tgl;
    private javax.swing.JTextField txt_tgl2;
    // End of variables declaration//GEN-END:variables
}
