package org.weixin4j.pay.component;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.weixin4j.pay.Configuration;
import org.weixin4j.pay.WeixinPay;
import org.weixin4j.pay.WeixinPayConfig;
import org.weixin4j.pay.WeixinPayException;
import org.weixin4j.pay.http.HttpsClient;
import org.weixin4j.pay.http.Response;
import org.weixin4j.pay.model.paybank.TransPayBankInfo;
import org.weixin4j.pay.model.paybank.TransPayBank;
import org.weixin4j.pay.model.paybank.TransPayBankResult;
import org.weixin4j.pay.model.rsa.RsaXml;
import org.weixin4j.pay.util.RSAEncrypt;
import org.weixin4j.pay.util.SignUtil;

/**
 * 企业付款到银行卡
 *
 * <p>
 * 用于企业向用户银行卡付款 请确保用户的卡号、户名等银行信息正确</p>
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class PayBankComponment extends AbstractComponent {

    public PayBankComponment(WeixinPay weixinPay) {
        super(weixinPay);
    }

    /**
     * 获取RSA公钥
     *
     * @return RSA公钥
     * @throws org.weixin4j.pay.WeixinPayException
     */
    public RsaXml getPublicKey() throws WeixinPayException {
        //获取微信支付配置
        WeixinPayConfig weixinPayConfig = weixinPay.getWeixinPayConfig();
        //创建RSA公钥请求对象
        String mchId = weixinPayConfig.getMchId();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //签名数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("sign_type", "MD5");
        //进行数据签名
        String sign = SignUtil.getSign(map, weixinPayConfig.getMchKey());

        //提交的XML数据
        String xml = "<xml>"
                + "<mch_id>" + mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<sign>" + sign + "</sign>"
                + "<sign_type>MD5</sign_type>"
                + "</xml>";

        //发起请求，获取RSA公钥
        HttpsClient httpsClient = new HttpsClient();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_获取RSA公钥接口 提交XML数据：" + xml);
        }
        //发起请求，获取RSA加密公钥
        Response response = httpsClient.postXmlWithCert("https://fraud.mch.weixin.qq.com/risk/getpublickey", xml, weixinPayConfig.getMchId(), weixinPayConfig.getCertPath(), weixinPayConfig.getCertSecret());
        //获取返回内容
        String xmlResult = response.asString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_获取RSA公钥接口 返回XML数据：" + xmlResult);
        }
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(RsaXml.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            RsaXml rsaXml = (RsaXml) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(rsaXml.getReturn_code())) {
                throw new WeixinPayException(rsaXml.getReturn_msg());
            }
            return rsaXml;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }

    /**
     * 企业付款到银行卡
     *
     * @param transPayBank 付款到银行卡
     * @return 付款结果
     * @throws org.weixin4j.pay.WeixinPayException
     */
    public TransPayBankResult transPayBank(TransPayBank transPayBank) throws WeixinPayException {
        //获取微信支付配置
        WeixinPayConfig weixinPayConfig = weixinPay.getWeixinPayConfig();
        String mchId = weixinPayConfig.getMchId();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        String rsaPubKey = weixinPay.getRsaPubKey();
        //数据加密
        String encBankNo = RSAEncrypt.encrypt(transPayBank.getEnc_bank_no(), rsaPubKey);
        String encTrueName = RSAEncrypt.encrypt(transPayBank.getEnc_true_name(), rsaPubKey);
        //数据签名
        Map<String, String> map = new HashMap<String, String>();
        map.put("amount", transPayBank.getAmount() + "");
        map.put("bank_code", transPayBank.getBank_code());
        map.put("desc", transPayBank.getDesc());
        map.put("enc_bank_no", encBankNo);
        map.put("enc_true_name", encTrueName);
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", transPayBank.getPartner_trade_no());
        //数据签名
        String sign = SignUtil.getSign(map, weixinPayConfig.getMchKey());
        //封装提交数据
        String xml = "<xml>"
                + "<amount>" + transPayBank.getAmount() + "</amount>"
                + "<bank_code>" + transPayBank.getBank_code() + "</bank_code>"
                + "<desc>" + transPayBank.getDesc() + "</desc>"
                + "<enc_bank_no>" + encBankNo + "</enc_bank_no>"
                + "<enc_true_name>" + encTrueName + "</enc_true_name>"
                + "<mch_id>" + mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<partner_trade_no>" + transPayBank.getPartner_trade_no() + "</partner_trade_no>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";
        //发起请求，获取RSA公钥
        HttpsClient httpsClient = new HttpsClient();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_企业付款到银行卡接口 提交XML数据：" + xml);
        }
        //发起请求，企业付款到银行卡API
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank", xml, weixinPayConfig.getMchId(), weixinPayConfig.getCertPath(), weixinPayConfig.getCertSecret());
        //获取返回内容
        String xmlResult = response.asString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_企业付款到银行卡接口 返回XML数据：" + xmlResult);
        }
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(TransPayBankResult.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            TransPayBankResult transPayBankResult = (TransPayBankResult) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(transPayBankResult.getReturn_code())) {
                throw new WeixinPayException(transPayBankResult.getReturn_msg());
            }
            return transPayBankResult;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }

    /**
     * 查询企业付款到银行卡信息
     *
     * @param partnerTradeNo 商户企业付款单号
     * @return 企业付款到银行卡信息
     * @throws org.weixin4j.pay.WeixinPayException
     */
    public TransPayBankInfo queryBank(String partnerTradeNo) throws WeixinPayException {
        //获取微信支付配置
        WeixinPayConfig weixinPayConfig = weixinPay.getWeixinPayConfig();
        String mchId = weixinPayConfig.getMchId();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //数据签名
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", partnerTradeNo);
        //数据签名
        String sign = SignUtil.getSign(map, weixinPayConfig.getMchKey());
        //封装提交数据
        String xml = "<xml>"
                + "<mch_id>" + mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<partner_trade_no>" + partnerTradeNo + "</partner_trade_no>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";
        //发起请求，获取RSA公钥
        HttpsClient httpsClient = new HttpsClient();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_查询企业付款到银行卡接口 提交XML数据：" + xml);
        }
        //发起请求，企业付款到银行卡API
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaysptrans/query_bank", xml, weixinPayConfig.getMchId(), weixinPayConfig.getCertPath(), weixinPayConfig.getCertSecret());
        //获取返回内容
        String xmlResult = response.asString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_企业付款到银行卡接口 返回XML数据：" + xmlResult);
        }
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(TransPayBankInfo.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            TransPayBankInfo transPayBankInfo = (TransPayBankInfo) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(transPayBankInfo.getReturn_code())) {
                throw new WeixinPayException(transPayBankInfo.getReturn_msg());
            }
            return transPayBankInfo;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }
}
