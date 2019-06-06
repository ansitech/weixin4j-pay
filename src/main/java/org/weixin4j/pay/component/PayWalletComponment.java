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
import org.weixin4j.pay.model.paywallet.TransPayWallet;
import org.weixin4j.pay.model.paywallet.TransPayWalletInfo;
import org.weixin4j.pay.model.paywallet.TransPayWalletResult;
import org.weixin4j.pay.util.SignUtil;

/**
 * 企业付款到零钱
 *
 * <p>
 * 企业付款到零钱资金使用商户号余额资金。</p>
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class PayWalletComponment extends AbstractComponent {

    public PayWalletComponment(WeixinPay weixinPay) {
        super(weixinPay);
    }

    /**
     * 企业付款到零钱
     *
     * @param transPayWallet 企业付款到零钱信息
     * @return 企业付款到零钱结果
     * @throws org.weixin4j.pay.WeixinPayException
     */
    public TransPayWalletResult transPayWallet(TransPayWallet transPayWallet) throws WeixinPayException {
        //获取微信支付配置
        WeixinPayConfig weixinPayConfig = weixinPay.getWeixinPayConfig();
        //创建RSA公钥请求对象
        String mchId = weixinPayConfig.getMchId();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //数据签名
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_appid", transPayWallet.getMch_appid());
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", transPayWallet.getPartner_trade_no());
        map.put("openid", transPayWallet.getOpenid());
        if (transPayWallet.getCheck_name()) {
            //强校验真实姓名
            map.put("check_name", "FORCE_CHECK");
            if (transPayWallet.getRe_user_name() == null || transPayWallet.getRe_user_name().equals("")) {
                throw new WeixinPayException("真实姓名不能为空");
            }
            map.put("re_user_name", transPayWallet.getRe_user_name());
        } else {
            //不校验真实姓名
            map.put("check_name", "NO_CHECK");
        }
        map.put("amount", transPayWallet.getAmount() + "");
        map.put("desc", transPayWallet.getDesc());
        map.put("spbill_create_ip", transPayWallet.getSpbill_create_ip());
        //数据签名
        String sign = SignUtil.getSign(map, weixinPayConfig.getMchKey());
        //封装提交数据
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<mch_appid>").append(transPayWallet.getMch_appid()).append("</mch_appid>");
        sb.append("<mch_id>").append(mchId).append("</mch_id>");
        sb.append("<nonce_str>").append(nonceStr).append("</nonce_str>");
        sb.append("<partner_trade_no>").append(transPayWallet.getPartner_trade_no()).append("</partner_trade_no>");
        sb.append("<openid>").append(transPayWallet.getOpenid()).append("</openid>");
        if (transPayWallet.getCheck_name()) {
            sb.append("<check_name>FORCE_CHECK</check_name>");
            sb.append("<re_user_name>").append(transPayWallet.getRe_user_name()).append("</re_user_name>");
        } else {
            sb.append("<check_name>NO_CHECK</check_name>");
        }
        sb.append("<amount>").append(transPayWallet.getAmount()).append("</amount>");
        sb.append("<desc>").append(transPayWallet.getDesc()).append("</desc>");
        sb.append("<spbill_create_ip>").append(transPayWallet.getSpbill_create_ip()).append("</spbill_create_ip>");
        sb.append("<sign>").append(sign).append("</sign>");
        sb.append("</xml>");
        String xml = sb.toString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_企业付款到零钱接口 提交XML数据：" + xml);
        }
        HttpsClient httpsClient = new HttpsClient();
        //发起请求，企业付款到零钱API
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", xml, weixinPayConfig.getMchId(), weixinPayConfig.getCertPath(), weixinPayConfig.getCertSecret());
        //获取返回内容
        String xmlResult = response.asString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_企业付款到零钱接口 返回XML数据：" + xmlResult);
        }
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(TransPayWalletResult.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            TransPayWalletResult transPayWalletResult = (TransPayWalletResult) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(transPayWalletResult.getReturn_code())) {
                throw new WeixinPayException(transPayWalletResult.getReturn_msg());
            }
            return transPayWalletResult;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }

    /**
     * 查询企业付款到零钱信息
     *
     * @param partnerTradeNo 商户企业付款单号
     * @param appid 微信公众帐号ID
     * @return 企业付款到零钱信息
     * @throws org.weixin4j.pay.WeixinPayException
     */
    public TransPayWalletInfo getTransferInfo(String partnerTradeNo, String appid) throws WeixinPayException {
        //获取微信支付配置
        WeixinPayConfig weixinPayConfig = weixinPay.getWeixinPayConfig();
        String mchId = weixinPayConfig.getMchId();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //数据签名
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_id", mchId);
        map.put("nonce_str", nonceStr);
        map.put("partner_trade_no", partnerTradeNo);
        map.put("appid", appid);
        //数据签名
        String sign = SignUtil.getSign(map, weixinPayConfig.getMchKey());
        //封装提交数据
        String xml = "<xml>"
                + "<mch_id>" + mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<partner_trade_no>" + partnerTradeNo + "</partner_trade_no>"
                + "<appid>" + appid + "</appid>"
                + "<sign>" + sign + "</sign>"
                + "</xml>";
        //发起请求，查询企业付款到零钱结果
        HttpsClient httpsClient = new HttpsClient();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_查询企业付款到零钱接口 提交XML数据：" + xml);
        }
        //发起请求，查询企业付款到零钱API
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo", xml, weixinPayConfig.getMchId(), weixinPayConfig.getCertPath(), weixinPayConfig.getCertSecret());
        //获取返回内容
        String xmlResult = response.asString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_查询企业付款到零钱接口 返回XML数据：" + xmlResult);
        }
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(TransPayWalletInfo.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            TransPayWalletInfo transPayWalletInfo = (TransPayWalletInfo) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(transPayWalletInfo.getReturn_code())) {
                throw new WeixinPayException(transPayWalletInfo.getReturn_msg());
            }
            return transPayWalletInfo;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }
}
