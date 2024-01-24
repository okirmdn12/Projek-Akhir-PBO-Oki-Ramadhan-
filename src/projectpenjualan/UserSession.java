/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpenjualan;

/**
 *
 * @author admin
 */
class UserSession {
       
    private static String userLogin;

    public static void setUserLogin(String userLogin) {
        UserSession.userLogin = userLogin;
    }
    
    public static String getUserLogin() {
        return userLogin;
    }
    
    private static String userPengguna;
    
     public static void setUserPengguna(String userPengguna) {
        UserSession.userPengguna = userPengguna;
    }
     
     public static String getUserPengguna() {
        return userPengguna;
    }
}
