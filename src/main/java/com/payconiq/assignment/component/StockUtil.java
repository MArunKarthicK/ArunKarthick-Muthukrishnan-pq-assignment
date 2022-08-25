package com.payconiq.assignment.component;

import com.payconiq.assignment.AssignmentApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Slf4j
@Component
public class StockUtil {
    
    @Value("${stocks.encryption.algorithm:AES}")
    private String AES;

    // TODO: 25/08/2022  need to put secret in secure store
    @Value("${stocks.encryption.secret:secret-key-12345}")
    private String SECRET;


    /**
     * generate the stockKey with combination of stockname and randomGeneratedString
     * Example:
     * StockName: Payconiq -> substring of name = pay+"_"+generated_string
     *
     * @param stockName : stock name which we get in stock request to generate StockKey
     * @return :generated stockKey
     */
    public String StockKeyGenerator(String stockName) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String randomGeneratedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        log.info("StockKeyGenerator: stockKey is generated");
        return stockName.substring(0, 3) + "_" + randomGeneratedString;
    }

    /**
     * Encrypt the stockKey to send in api response
     * Encryption is based on AES Algorithm and secret from config
     *
     * @param stockKey stockKey to be encrypted [to send in apiResponse]
     * @return :Encrypted stockKey
     */
    public String EncryptStockKey(String stockKey) {
        try {
            Key key = new SecretKeySpec(SECRET.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bt = Base64.encodeBase64URLSafe(cipher.doFinal(stockKey.getBytes(StandardCharsets.UTF_8)));
            log.info("EncryptStockKey: stockKey is encrypted");
            return new String(bt, "UTF-8");
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("EncryptStockKey: unable to Encrypt the stockKey:{} throw IllegalStateException", stockKey);
            throw new IllegalStateException(e);
        }
    }

    /**
     * Decrypt the stockKey to use in db operation
     * Decryption is based on AES Algorithm and secret from config
     *
     * @param encryptStockKey encryptStockKey to be decrypted [to use in DB operation]
     * @return :Decrypted stockKey
     */
    public String DecryptStockKey(String encryptStockKey) {
        try {
            Key key = new SecretKeySpec(SECRET.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bt = cipher.doFinal(Base64.decodeBase64URLSafe(encryptStockKey));
            log.info("DecryptStockKey: stockKey is decrypted");
            return new String(bt, "UTF-8");
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("EncryptStockKey: unable to Decrypt the stockKey:{} throw IllegalStateException", encryptStockKey);
            throw new IllegalStateException(e);
        }
    }
}
