package com.gitrepo.kziomek;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Krzysztof Ziomek
 * @since 03/03/2016.
 */
public class SymmetricCipher {

    private int ITERATION_COUNT = 2000;

    private int KEY_LENGTH = 256;

    private String KEY_PASSWORD = "SecretPassword";

    private String SALT="Salt";


    private SecretKeySpec secretKeySpec;
    private Cipher encryptCipher;


    public SymmetricCipher() {
        char[] keyPasswordChars = KEY_PASSWORD.toCharArray();
        byte[] saltBytes = SALT.getBytes(StandardCharsets.UTF_8);
        try {
            /* Prepare secret key with password and salt */
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            PBEKeySpec pbeKeySpec = new PBEKeySpec(keyPasswordChars, saltBytes, ITERATION_COUNT, KEY_LENGTH);
            Key secretKey = factory.generateSecret(pbeKeySpec);
            byte[] encodedSecretKey = secretKey.getEncoded();
            secretKeySpec = new SecretKeySpec(encodedSecretKey, "AES");

            /* Prepare reusable Encrypt Cipher */
            encryptCipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }



    public EncryptionDTO encrypt(String plainBody) {

        try {
            byte[] iv = encryptCipher.getIV();
            byte[] ct = encryptCipher.doFinal(plainBody.getBytes(StandardCharsets.UTF_8));

            EncryptionDTO encryptionDTO = new EncryptionDTO();
            encryptionDTO.setIv(new String(Base64.encodeBase64(iv), StandardCharsets.UTF_8));
            encryptionDTO.setCt(new String(Base64.encodeBase64(ct), StandardCharsets.UTF_8));
            return encryptionDTO;
        } catch (BadPaddingException | IllegalBlockSizeException e ) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Create decrypt cipher with key and iv. Then decrypt ct.
     * @param encryptionDTO
     * @return
     */
    public String decrypt(EncryptionDTO encryptionDTO) {
        try {

            byte[] iv = Base64.decodeBase64(encryptionDTO.getIv());
            byte[] encBytes = Base64.decodeBase64(encryptionDTO.getCt());

            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

            byte[] decrypted = cipher.doFinal(encBytes);
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e ) {
            throw new RuntimeException(e);
        }

    }
}
