package org.weixin4j.pay.loader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.weixin4j.pay.Configuration;
import org.weixin4j.pay.model.rsa.RsaXml;

/**
 * 本地文件RSA公钥存储器
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class LocalFileRsaPubKeyLoader implements IRsaPubKeyLoader {

    private String rsaPubKey = null;

    @Override
    public String get() {
        if (rsaPubKey == null) {
            try {
                //读取
                String rsaPubKeyPath = Configuration.getProperty("weixin4j.rsaPubKey.pkcs8");
                File file = new File(rsaPubKeyPath);
                if (!file.exists()) {
                    if (Configuration.isDebug()) {
                        System.out.println("未找到 weixin4j.rsaPubKey.pkcs8 文件");
                    }
                    return null;
                }
                String absolutePath = file.getAbsolutePath();
                List<String> lines = Files.readAllLines(Paths.get(absolutePath), StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    if (line.charAt(0) == '-') {
                        continue;
                    } else {
                        sb.append(line);
                        sb.append('\r');
                    }
                }
                this.rsaPubKey = sb.toString();
            } catch (IOException ex) {
                System.out.println("读取 weixin4j.rsaPubKey.path 文件出错：" + ex.getMessage());
                ex.printStackTrace();
                return null;
            }
        }
        return rsaPubKey;
    }

    @Override
    public void refresh(RsaXml rsaXml) {
        if (rsaXml == null) {
            throw new IllegalArgumentException("rsaXml can not be null");
        }
        if (Configuration.isDebug()) {
            System.out.println("refresh RSA: " + rsaXml.toString());
        }
        if (!"SUCCESS".equals(rsaXml.getResult_code())) {
            throw new IllegalArgumentException("rsaXml refresh error " + rsaXml.getErr_code() + "," + rsaXml.getErr_code_des());
        }
        try {
            //读取PKCS#1
            String rsaPubKey1Path = Configuration.getProperty("weixin4j.rsaPubKey.pkcs1");
            File file = new File(rsaPubKey1Path);
            String absolutePath = file.getAbsolutePath();
            Path path = Paths.get(absolutePath);
            try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
                writer.write(rsaXml.getPub_key());
            }
            String rsaPubKey8Path = Configuration.getProperty("weixin4j.rsaPubKey.pkcs8");
            //将PKCC#1转为PKCS#8
            Runtime rt = Runtime.getRuntime();
            String binPath = Configuration.getProperty("weixin4j.openssl.path");
            Process ps = rt.exec(binPath + "openssl rsa -RSAPublicKey_in -in " + rsaPubKey1Path + " -pubout -out " + rsaPubKey8Path);
            ps.waitFor();
        } catch (IOException | InterruptedException ex) {
            System.out.println("保存 weixin4j.rsaPubKey.path 文件出错：" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
