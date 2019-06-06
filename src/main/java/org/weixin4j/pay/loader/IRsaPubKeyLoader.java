package org.weixin4j.pay.loader;

import org.weixin4j.pay.model.rsa.RsaXml;

/**
 * RSA公钥加载器
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public interface IRsaPubKeyLoader {
    
    /**
     * 获取RSA公钥
     *
     * @return RSA公钥
     */
    public String get();

    /**
     * 更新RSA公钥
     *
     * @param rsaXml
     */
    public void refresh(RsaXml rsaXml);
}
