package com.luishidalgoa.Nexa.Utils;


import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Login {
    public static boolean Sing_up(User entity){
        entity.setPassword(encrypt(entity.getPassword()));
        try {
            if(UserDAO.getInstance().searchUser(entity.getUser_name())==null && UserDAO.getInstance().save(entity)){
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static UserDTO Sing_in(String username, String passwordString){
        try {
            return UserDAO.getInstance().signIn(username,encrypt(passwordString));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String encrypt(String password) {
        try {
            // Obtenemos una instancia del algoritmo de hash SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Convertimos la contraseña en bytes y la procesamos con el algoritmo de hash
            byte[] passwordBytes = password.getBytes();
            byte[] hashedBytes = md.digest(passwordBytes);

            // Convertimos los bytes hash en una cadena de caracteres en hexadecimal
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashedBytes.length; i++) {
                sb.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
