package org.weixin4j.pay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;
import org.weixin4j.pay.WeixinPayException;

/**
 * RSAEncrypt
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class RSAEncrypt {

    /**
     * 公钥长度
     */
    private static final int KEYLENGTH = 2048;
    /**
     * 重置长度
     */
    private static final int RESERVESIZE = 11;
    /**
     * 指定填充模式
     */
    private static final String CIPHERALGORITHM = "RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING";

    /**
     * 数据公钥加密
     *
     * @param str 待加密的字符串
     * @param rsaPubKey 加密公钥(PKCS#8)
     * @return 加密后的数据
     * @throws WeixinPayException
     */
    public static String encrypt(String str, String rsaPubKey) throws WeixinPayException {
        try {
            PublicKey publicKey = loadPublicKey(rsaPubKey);
            return encrypt(str.getBytes("UTF-8"), publicKey);
        } catch (UnsupportedEncodingException e) {
            throw new WeixinPayException(e);
        }
    }

    /**
     * 加载公钥
     *
     * @param rsaPubKeyPkcs8 PKCS#8公钥数据字符串
     * @return 公钥
     * @throws WeixinPayException
     */
    private static PublicKey loadPublicKey(String rsaPubKeyPkcs8) throws WeixinPayException {
        try {
            byte[] buffer = Base64.decodeBase64(rsaPubKeyPkcs8);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new WeixinPayException("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new WeixinPayException("公钥非法");
        } catch (NullPointerException e) {
            throw new WeixinPayException("公钥数据为空");
        }
    }

    private static String encrypt(byte[] plainBytes, PublicKey publicKey) throws WeixinPayException {
        int keyByteSize = KEYLENGTH / 8;
        int encryptBlockSize = keyByteSize - RESERVESIZE;
        int nBlock = plainBytes.length / encryptBlockSize;
        if ((plainBytes.length % encryptBlockSize) != 0) {
            nBlock += 1;
        }
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHERALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                outbuf.write(encryptedBlock);
            }
            outbuf.flush();
            byte[] encryptedData = outbuf.toByteArray();
            return Base64.encodeBase64String(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            throw new WeixinPayException("ENCRYPT ERROR:", e);
        } finally {
            try {
                if (outbuf != null) {
                    outbuf.close();
                }
            } catch (IOException e) {
                throw new WeixinPayException("CLOSE ByteArrayOutputStream ERROR:", e);
            }
        }
    }
}
