package com.codecool.shop.password;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import com.codecool.shop.database.implementation.ExecuteQuery;
import com.codecool.shop.model.User;
import java.security.NoSuchAlgorithmException;

public class HashAndAuthenticate {

    private HashAndAuthenticate() {
    }

    public static boolean verifyPassword(String email, String passwordForVerification) throws NoSuchAlgorithmException {
        User user = ExecuteQuery.getInstance().getUserObjectByEmail(email);
        String storagedPassword = user.getPassword();
        passwordForVerification = getSecurePassword(passwordForVerification);
        if (storagedPassword.equals(passwordForVerification)) {
            return true;
        }
        return false;
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
    }
}