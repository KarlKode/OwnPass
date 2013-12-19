package ch.ethz.inf.vs.android.gamarc.ownpass;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

public class Encryption {
    private final Server server;
    private final SecureRandom random;
    private Cipher cipher;

    public Encryption(Server server) {
        this.server = server;
        random = new SecureRandom();
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] sha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] encrypt(String toEncrypt) {
        try {
            // Generate random IV
            byte[] iv = new byte[cipher.getBlockSize()];
            random.nextBytes(iv);

            // Encrypt the string
            SecretKeySpec key = new SecretKeySpec(sha256(server.getPassword()), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedString = cipher.doFinal(toEncrypt.getBytes("UTF-8"));

            // Copy both the IV and the encrypted string into a new array
            byte[] ivAndEncryptedString = new byte[iv.length + encryptedString.length];
            System.arraycopy(iv, 0, ivAndEncryptedString, 0, iv.length);
            System.arraycopy(encryptedString, 0, ivAndEncryptedString, iv.length, encryptedString.length);

            return ivAndEncryptedString;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String decrypt(byte[] ivAndEncryptedString) {
        if (ivAndEncryptedString.length < cipher.getBlockSize()) {
            // TODO: Error handling
            return null;
        }

        // Get the IV from the byte array
        byte[] iv = new byte[cipher.getBlockSize()];
        System.arraycopy(ivAndEncryptedString, 0, iv, 0, iv.length);

        // The rest is the encrypted string
        byte[] encryptedString = new byte[ivAndEncryptedString.length - iv.length];
        System.arraycopy(ivAndEncryptedString, iv.length, encryptedString, 0, encryptedString.length);

        // Decrypt the string
        try {
            SecretKeySpec key = new SecretKeySpec(sha256(server.getPassword()), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedString = cipher.doFinal(encryptedString);
            return new String(decryptedString, "UTF-8");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
