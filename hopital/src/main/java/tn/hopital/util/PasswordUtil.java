package tn.hopital.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // TEMPORAIRE : génère un hash pour insérer dans la base
    public static void main(String[] args) {
        String hash = hashPassword("mariem123");
        System.out.println("HASH = " + hash);
    }
}
