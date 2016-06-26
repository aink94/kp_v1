/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp_v1;

/**
 *
 * @author Ilham Fauji
 */
public class user {
    public static String username, id_user;
    public static void set_username(String user){
            username=user;
    }
    public static void set_id_user(String iduser){
        id_user=iduser;
    }
    public String get_username(){
        return username;
    }
    public String get_id_user(){
        return id_user;
    }
}
