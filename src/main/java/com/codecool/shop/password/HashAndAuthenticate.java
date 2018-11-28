package com.codecool.shop.password;

import com.codecool.shop.database.implementation.ExecuteQuery;
import com.codecool.shop.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;

public class HashAndAuthenticate {

    public static boolean verifyPassword(String email, String password) {
        ExecuteQuery executeQuery = ExecuteQuery.getInstance();
        User user = executeQuery.getUserObjectByEmail(email);
        String storagedPassword = user.getPassword();
        if (storagedPassword.equals(password)) {
            return true;
        }
        return false;
    }

    /*
    source:
    https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     */
    public static String getSecurePassword(String painpassword) throws NoSuchAlgorithmException {
        byte[] salt = getSalt();
        String securePassword = get_SHA_256_SecurePassword(painpassword, salt);
        return securePassword;
    }

    private static String get_SHA_256_SecurePassword(String plainPassword, byte[] salt) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(plainPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    //Add salt
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}