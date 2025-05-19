/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author dary_
 */
public class PasswordUtils {
    
    public static String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedByte = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : hashedByte) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void toogglePasswordIcon(javax.swing.JPasswordField passwordField, javax.swing.JButton showPasswordButton){
        if(passwordField.getEchoChar() == 0){
            passwordField.setEchoChar('*');
            showPasswordButton.setIcon(new javax.swing.ImageIcon("")); // Colocar la ruta del archivo una vez que cree la carpeta   //
        } else {
            passwordField.setEchoChar((char) 0);
            showPasswordButton.setIcon(new javax.swing.ImageIcon());// Colocar la ruta del archivo una vez que cree la carpeta //
        }
    }
}
