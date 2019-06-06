/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weixin4j.pay.http;

import org.weixin4j.pay.Configuration;
import org.weixin4j.pay.WeixinPayException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * 请求微信平台及响应的客户端类
 *
 * <p>
 * 每一次请求即对应一个<tt>HttpsClient</tt>，
 * 每次登陆产生一个<tt>OAuth</tt>用户连接,使用<tt>OAuthToken</tt>
 * 可以不用重复向微信平台发送登陆请求，在没有过期时间内，可继续请求。</p>
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class HttpsClient implements java.io.Serializable {

    /**
     * 成功状态码
     */
    private static final int OK = 200;
    /**
     * 连接超时时间
     */
    private static final int CONNECTION_TIMEOUT = Configuration.getConnectionTimeout();
    /**
     * 读取超时时间
     */
    private static final int READ_TIMEOUT = Configuration.getReadTimeout();
    /**
     * 字符编码
     */
    private static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * GET请求
     */
    private static final String GET = "GET";
    /**
     * POST请求
     */
    private static final String POST = "POST";

    public HttpsClient() {
    }

    /**
     * Post XML格式数据
     *
     * @param url 提交地址
     * @param xml XML数据
     * @return 输出流对象
     * @throws org.weixin4j.pay.WeixinPayException 微信操作异常
     */
    public Response postXmlWithOutCert(String url, String xml) throws WeixinPayException {
        return sendHttpsRequest(url, POST, xml, false, null, null, null);
    }

    /**
     * Post XML格式数据
     *
     * @param url 提交地址
     * @param xml XML数据
     * @param mchId 商户ID
     * @param certPath 证书地址
     * @param certSecret 证书密钥
     * @return 输出流对象
     * @throws org.weixin4j.pay.WeixinPayException 微信操作异常
     */
    public Response postXmlWithCert(String url, String xml, String mchId, String certPath, String certSecret) throws WeixinPayException {
        return sendHttpsRequest(url, POST, xml, true, mchId, certPath, certSecret);
    }

    /**
     * 通过https协议请求url
     *
     * @param url 提交地址
     * @param method 提交方式
     * @param postData 提交数据
     * @return 响应流
     * @throws org.weixin4j.pay.WeixinPayException 微信操作异常
     */
    private Response sendHttpsRequest(String url, String method, String postData, boolean needCert, String partnerId, String certPath, String certSecret)
            throws WeixinPayException {
        Response response = null;
        OutputStream outputStream;
        HttpsURLConnection httpsUrlConnection;
        try {
            //创建https请求连接
            URL urlGet = new URL(url);
            //创建https请求
            httpsUrlConnection = (HttpsURLConnection) urlGet.openConnection();
            //设置Header信息，包括https证书
            if (!needCert) {
                //创建https请求证书
                TrustManager[] tm = {new MyX509TrustManager()};
                //创建证书上下文对象
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                //初始化证书信息
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象  
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                //设置ssl证书
                httpsUrlConnection.setSSLSocketFactory(ssf);
            } else {
                //指定读取证书格式为PKCS12
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                //读取本机存放的PKCS12证书文件
                FileInputStream instream = new FileInputStream(new File(certPath));
                try {
                    //指定PKCS12的密码
                    keyStore.load(instream, partnerId.toCharArray());
                } finally {
                    instream.close();
                }
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(keyStore, certSecret.toCharArray());
                //创建管理jks密钥库的x509密钥管理器，用来管理密钥，需要key的密码  
                SSLContext sslContext = SSLContext.getInstance("TLSv1");
                // 构造SSL环境，指定SSL版本为3.0，也可以使用TLSv1，但是SSLv3更加常用。  
                sslContext.init(kmf.getKeyManagers(), null, null);
                // 从上述SSLContext对象中得到SSLSocketFactory对象  
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                //设置ssl证书
                httpsUrlConnection.setSSLSocketFactory(ssf);
            }
            //设置header信息
            httpsUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置User-Agent信息
            httpsUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
            //设置可接受信息
            httpsUrlConnection.setDoOutput(true);
            //设置可输入信息
            httpsUrlConnection.setDoInput(true);
            //设置请求方式
            httpsUrlConnection.setRequestMethod(method);
            //设置连接超时时间
            if (CONNECTION_TIMEOUT > 0) {
                httpsUrlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            } else {
                //默认10秒超时
                httpsUrlConnection.setConnectTimeout(10000);
            }
            //设置请求超时
            if (READ_TIMEOUT > 0) {
                httpsUrlConnection.setReadTimeout(READ_TIMEOUT);
            } else {
                //默认10秒超时
                httpsUrlConnection.setReadTimeout(10000);
            }
            //设置编码
            httpsUrlConnection.setRequestProperty("Charsert", DEFAULT_CHARSET);
            //判断是否需要提交数据
            if (method.equals(POST) && null != postData) {
                //讲参数转换为字节提交
                byte[] bytes = postData.getBytes(DEFAULT_CHARSET);
                //设置头信息
                httpsUrlConnection.setRequestProperty("Content-Length", Integer.toString(bytes.length));
                //开始连接
                httpsUrlConnection.connect();
                //获取返回信息
                outputStream = httpsUrlConnection.getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
                outputStream.close();
            } else {
                //开始连接
                httpsUrlConnection.connect();
            }
            //创建输出对象
            response = new Response(httpsUrlConnection);
            //获取响应代码
            if (response.getStatus() == OK) {
                return response;
            }
            int status = response.getStatus();
            if (status != OK) {
                switch (status) {
                    case 651:
                        throw new WeixinPayException("网络连接失败");
                    case 500:
                        throw new WeixinPayException("服务内部错误");
                    case 404:
                        throw new WeixinPayException("网址不存在");
                    default:
                        throw new WeixinPayException("系统繁忙，请稍候再试");
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new WeixinPayException(ex.getMessage(), ex);
        } catch (KeyManagementException ex) {
            throw new WeixinPayException(ex.getMessage(), ex);
        } catch (NoSuchProviderException ex) {
            throw new WeixinPayException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new WeixinPayException(ex.getMessage(), ex);
        } catch (KeyStoreException ex) {
            throw new WeixinPayException(ex.getMessage(), ex);
        } catch (CertificateException ex) {
            throw new WeixinPayException(ex.getMessage(), ex);
        } catch (UnrecoverableKeyException ex) {
            throw new WeixinPayException(ex.getMessage(), ex);
        }
        return response;
    }
}
