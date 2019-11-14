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
import org.weixin4j.pay.model.redpack.SendRedPack;
import org.weixin4j.pay.model.redpack.SendRedPackResult;
import org.weixin4j.pay.model.redpack.SendRedpackInfo;
import org.weixin4j.pay.util.SignUtil;

/**
 * 现金红包
 *
 * <p>
 * 商户可以通过本平台向微信支付用户发放现金红包。
 *
 * 用户领取红包后，资金到达用户微信支付零钱账户，和零钱包的其他资金有一样的使用出口；
 *
 * 若用户未领取，资金将会在24小时后退回商户的微信支付账户中。</p>
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class RedpackComponment extends AbstractComponent {

    public RedpackComponment(WeixinPay weixinPay) {
        super(weixinPay);
    }

    /**
     * 发放普通红包
     *
     * @param sendRedPack 现金红包对象
     * @return 发放普通红包返回结果对象
     * @throws org.weixin4j.pay.WeixinPayException 微信操作异常
     */
    public SendRedPackResult sendRedPack(SendRedPack sendRedPack) throws WeixinPayException {
        //获取微信支付配置
        WeixinPayConfig weixinPayConfig = weixinPay.getWeixinPayConfig();
        String mchId = weixinPayConfig.getMchId();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //签名数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("nonce_str", nonceStr);
        map.put("mch_billno", sendRedPack.getMch_billno());
        map.put("mch_id", mchId);
        map.put("wxappid", sendRedPack.getWxappid());
        map.put("send_name", sendRedPack.getSend_name());
        map.put("re_openid", sendRedPack.getRe_openid());
        map.put("total_amount", sendRedPack.getTotal_amount() + "");
        map.put("total_num", "1");
        map.put("wishing", sendRedPack.getWishing());
        map.put("client_ip", sendRedPack.getClient_ip());
        map.put("act_name", sendRedPack.getAct_name());
        map.put("remark", sendRedPack.getRemark());
        //进行数据签名
        String sign = SignUtil.getSign(map, weixinPayConfig.getMchKey());
        //将统一下单对象转成XML
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<sign><![CDATA[").append(sign).append("]]></sign>");
        sb.append("<mch_billno><![CDATA[").append(sendRedPack.getMch_billno()).append("]]></mch_billno>");
        sb.append("<mch_id><![CDATA[").append(mchId).append("]]></mch_id>");
        sb.append("<wxappid><![CDATA[").append(sendRedPack.getWxappid()).append("]]></wxappid>");
        sb.append("<send_name><![CDATA[").append(sendRedPack.getSend_name()).append("]]></send_name>");
        sb.append("<re_openid><![CDATA[").append(sendRedPack.getRe_openid()).append("]]></re_openid>");
        sb.append("<total_amount><![CDATA[").append(sendRedPack.getTotal_amount()).append("]]></total_amount>");
        sb.append("<total_num><![CDATA[").append(1).append("]]></total_num>");
        sb.append("<wishing><![CDATA[").append(sendRedPack.getWishing()).append("]]></wishing>");
        sb.append("<client_ip><![CDATA[").append(sendRedPack.getClient_ip()).append("]]></client_ip>");
        sb.append("<act_name><![CDATA[").append(sendRedPack.getAct_name()).append("]]></act_name>");
        sb.append("<remark><![CDATA[").append(sendRedPack.getRemark()).append("]]></remark>");
        sb.append("<nonce_str><![CDATA[").append(nonceStr).append("]]></nonce_str>");
        sb.append("</xml>");
        String xml = sb.toString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_发送普通红包接口 提交XML数据：" + xml);
        }
        //创建请求对象
        HttpsClient httpsClient = new HttpsClient();
        //发起请求，发送普通红包
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", xml, weixinPayConfig.getMchId(), weixinPayConfig.getCertPath(), weixinPayConfig.getCertSecret());
        //获取微信平台下单接口返回数据
        String xmlResult = response.asString();
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(SendRedPackResult.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            SendRedPackResult sendRedPackResult = (SendRedPackResult) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(sendRedPackResult.getReturn_code())) {
                throw new WeixinPayException(sendRedPackResult.getReturn_msg());
            }
            return sendRedPackResult;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }

    /**
     * 查询红包记录
     *
     * @param mch_billno 商户订单号
     * @param appid 微信公众帐号ID
     * @return 红包发送记录
     * @throws WeixinPayException 微信服务异常
     */
    public SendRedpackInfo gethbinfo(String mch_billno, String appid) throws WeixinPayException {
        //获取微信支付配置
        WeixinPayConfig weixinPayConfig = weixinPay.getWeixinPayConfig();
        String mchId = weixinPayConfig.getMchId();
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //签名数据
        Map<String, String> map = new HashMap<String, String>();
        map.put("nonce_str", nonceStr);
        map.put("mch_billno", mch_billno);
        map.put("mch_id", mchId);
        map.put("appid", appid);
        map.put("bill_type", "MCHT");
        //进行数据签名
        String sign = SignUtil.getSign(map, weixinPayConfig.getMchKey());
        //将统一下单对象转成XML
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<sign><![CDATA[").append(sign).append("]]></sign>");
        sb.append("<mch_billno><![CDATA[").append(mch_billno).append("]]></mch_billno>");
        sb.append("<mch_id><![CDATA[").append(mchId).append("]]></mch_id>");
        sb.append("<appid><![CDATA[").append(appid).append("]]></appid>");
        sb.append("<bill_type><![CDATA[").append("MCHT").append("]]></bill_type>");
        sb.append("<nonce_str><![CDATA[").append(nonceStr).append("]]></nonce_str>");
        sb.append("</xml>");
        String xml = sb.toString();
        if (Configuration.isDebug()) {
            System.out.println("调试模式_查询红包记录接口 提交XML数据：" + xml);
        }
        //创建请求对象
        HttpsClient httpsClient = new HttpsClient();
        //发起请求，查询红包记录
        Response response = httpsClient.postXmlWithCert("https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo", xml, weixinPayConfig.getMchId(), weixinPayConfig.getCertPath(), weixinPayConfig.getCertSecret());
        //获取微信平台下单接口返回数据
        String xmlResult = response.asString();
        try {
            //创建XML解析对象
            JAXBContext context = JAXBContext.newInstance(SendRedpackInfo.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            xif.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            //防XXE攻击
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(xmlResult));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //解析XML对象
            SendRedpackInfo sendRedpackInfo = (SendRedpackInfo) unmarshaller.unmarshal(xsr);
            if (!"SUCCESS".equals(sendRedpackInfo.getReturn_code())) {
                throw new WeixinPayException(sendRedpackInfo.getReturn_msg());
            }
            return sendRedpackInfo;
        } catch (JAXBException | XMLStreamException ex) {
            return null;
        }
    }
}
