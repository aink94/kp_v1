/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ENZA
 */
public class Koneksi {
private static Connection koneksi;
    public static Connection konekDB() throws SQLException{
        try{
            String url="jdbc:mysql://localhost:3306/kp";
            String user="root";
            String pass="";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            koneksi=DriverManager.getConnection(url,user,pass);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Koneksi Gagal"+e);
        }
        return koneksi;
    } 
}
