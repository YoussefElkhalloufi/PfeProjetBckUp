package com.example.pfeprojet.Controllers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class PassworManager {

    //To hash the password
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // to verify the password
    public static boolean verifyPassword(String inputPassword, String hashedPassword) {
        System.out.println("inputPassword that is going to be hashed : " +inputPassword);
        System.out.println("hashed password that is ");
        return hashPassword(inputPassword).equals(hashedPassword);
    }

}
