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
package org.weixin4j.pay;

import java.util.HashMap;
import java.util.Map;
import org.weixin4j.pay.component.AbstractComponent;
import org.weixin4j.pay.component.PayBankComponment;
import org.weixin4j.pay.component.PayWalletComponment;
import org.weixin4j.pay.component.RedpackComponment;
import org.weixin4j.pay.loader.IRsaPubKeyLoader;
import org.weixin4j.pay.model.rsa.RsaXml;

/**
 * 微信平台基础支持对象
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class WeixinPay {

    /**
     * 同步锁
     */
    private final static byte[] LOCK = new byte[0];

    /**
     * 微信支付配置
     *
     * @since 1.0.0
     */
    private final WeixinPayConfig weixinPayConfig;

    /**
     * 新增组件
     */
    private final Map<String, AbstractComponent> components = new HashMap<String, AbstractComponent>();

    /**
     * 默认构造函数
     */
    public WeixinPay() {
        weixinPayConfig = new WeixinPayConfig();
        weixinPayConfig.setMchId(Configuration.getMchId());
        weixinPayConfig.setMchKey(Configuration.getMchKey());
        weixinPayConfig.setCertPath(Configuration.getProperty("weixin4j.http.cert.path"));
        weixinPayConfig.setCertSecret(Configuration.getProperty("weixin4j.http.cert.secret"));
    }

    /**
     * 外部配置注入方式（带微信支付），更灵活
     *
     * @param weixinPayConfig 微信支付配置
     * @since 1.0.0
     */
    public WeixinPay(WeixinPayConfig weixinPayConfig) {
        if (weixinPayConfig == null) {
            throw new IllegalArgumentException("weixinPayConfig can not be null");
        }
        this.weixinPayConfig = weixinPayConfig;
    }

    /**
     * 获取微信支付配置对象
     *
     * @return 微信支付配置对象
     * @since 1.0.0
     */
    public WeixinPayConfig getWeixinPayConfig() {
        return weixinPayConfig;
    }

    /**
     * RSA公钥加载器
     */
    protected IRsaPubKeyLoader rsaPubKeyLoader = null;

    private IRsaPubKeyLoader getRsaPubKeyLoader() {
        try {
            if (rsaPubKeyLoader != null) {
                return rsaPubKeyLoader;
            }
            synchronized (LOCK) {
                if (rsaPubKeyLoader != null) {
                    return rsaPubKeyLoader;
                }
                String rsaPubKeyLoaderClass = Configuration.getProperty("weixin4j.rsaPubKey.loader");
                rsaPubKeyLoader = (IRsaPubKeyLoader) Class.forName(rsaPubKeyLoaderClass).newInstance();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
        return rsaPubKeyLoader;
    }

    public void setRsaPubKeyLoader(IRsaPubKeyLoader rsaPubKeyLoader) {
        this.rsaPubKeyLoader = rsaPubKeyLoader;
    }
    
    /**
     * 获取RSA公钥
     *
     * @return RSA公钥
     * @throws org.weixin4j.pay.WeixinPayException 微信服务异常
     * @since 1.0.0
     */
    public String getRsaPubKey() throws WeixinPayException {
        String rsaPubKey = getRsaPubKeyLoader().get();
        if (rsaPubKey == null) {
            synchronized (LOCK) {
                rsaPubKey = rsaPubKeyLoader.get();
                if (rsaPubKey == null) {
                    RsaXml rsaXml = payBank().getPublicKey();
                    rsaPubKeyLoader.refresh(rsaXml);
                    rsaPubKey = rsaXml.getPub_key();
                }
            }
        }
        return rsaPubKey;
    }

    /**
     * 获取企业付款到银行卡组件
     *
     * @return 企业付款到银行卡组件
     */
    public PayBankComponment payBank() {
        String key = PayBankComponment.class.getName();
        if (components.containsKey(key)) {
            return (PayBankComponment) components.get(key);
        }
        PayBankComponment component = new PayBankComponment(this);
        components.put(key, component);
        return component;
    }

    /**
     * 获取现金红包组件
     *
     * @return 现金红包组件
     */
    public RedpackComponment redpack() {
        String key = RedpackComponment.class.getName();
        if (components.containsKey(key)) {
            return (RedpackComponment) components.get(key);
        }
        RedpackComponment component = new RedpackComponment(this);
        components.put(key, component);
        return component;
    }

    /**
     * 获取企业付款到零钱组件
     *
     * @return 企业付款到零钱组件
     */
    public PayWalletComponment payWallet() {
        String key = PayWalletComponment.class.getName();
        if (components.containsKey(key)) {
            return (PayWalletComponment) components.get(key);
        }
        PayWalletComponment component = new PayWalletComponment(this);
        components.put(key, component);
        return component;
    }
}
