package com.codecool.shop.password;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;

public class HashAndAuthenticate {

    public static boolean verifyPassword(String name, String password) {
        return true;
    }

    /*
    source:
    https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     */
    public static String getSecurePassword(String painpassword) throws NoSuchAlgorithmException {

        String securePassword = get_SHA_256_SecurePassword(painpassword);
        return securePassword;
    }

    private static String get_SHA_256_SecurePassword(String plainPassword) {

        String sha256hex = Hashing.sha256()
                .hashString(plainPassword, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;


        //String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(plainPassword);
//        String generatedPassword = null;
//
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(salt);
//            byte[] bytes = md.digest(plainPassword.getBytes());
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i< bytes.length ;i++)
//            {
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            generatedPassword = sb.toString();
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        return generatedPassword;
    }

    //Add salt
//    private static byte[] getSalt() throws NoSuchAlgorithmException {
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        byte[] salt = new byte[16];
//        sr.nextBytes(salt);
//        return salt;
//    }
}