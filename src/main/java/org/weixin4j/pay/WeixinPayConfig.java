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

/**
 * 微信支付配置
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class WeixinPayConfig {

    /**
     * 微信支付_商户ID
     */
    private String mchId;
    /**
     * 微信支付_商户密钥
     */
    private String mchKey;
    /**
     * 证书路径
     */
    private String certPath;
    /**
     * 证书密钥
     */
    private String certSecret;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getCertSecret() {
        return certSecret;
    }

    public void setCertSecret(String certSecret) {
        this.certSecret = certSecret;
    }

}
