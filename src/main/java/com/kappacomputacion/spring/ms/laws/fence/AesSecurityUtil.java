package com.kappacomputacion.spring.ms.laws.fence;

import jakarta.xml.bind.DatatypeConverter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Objects;


@Service
public class AesSecurityUtil {
    private final int keySize = 128;
    private final int iterationCount=1000;
    private final Cipher cipher;
    private final String passPhrase = "8kov1wOMsPsN$5*wPJ67dub$T";

    public AesSecurityUtil() {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw fail(e);
        }
    }

    public synchronized String encrypt(String salt, String iv, String plainText){
        try {
            SecretKey secretKey = generateKey(salt, this.passPhrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, secretKey, iv, plainText.getBytes(StandardCharsets.UTF_8));

            return DatatypeConverter.printBase64Binary(encrypted);
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized String decrypt(String salt, String iv,  String ciphertext) {
        try {
            SecretKey key = generateKey(salt, this.passPhrase);

            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, base64(ciphertext));

            return new String(Objects.requireNonNull(decrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) {
        try {
            cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
            return cipher.doFinal(bytes);
        }
        catch (InvalidKeyException
               | InvalidAlgorithmParameterException
               | IllegalBlockSizeException
               | BadPaddingException e) {
            System.out.println("doFinal --> " +e.getMessage());
            return null;
        }
    }

    private SecretKey generateKey(String salt, String passphrase) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), this.iterationCount, this.keySize);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    public synchronized static byte[] base64(String str) {
        return Base64.decodeBase64(str);
    }

    public synchronized static byte[] hex(String str) {
        try {
            return Hex.decodeHex(str.toCharArray());
        }
        catch (DecoderException e) {
            throw new IllegalStateException(e);
        }
    }

    private IllegalStateException fail(Exception e) {
        return null;
    }
}
