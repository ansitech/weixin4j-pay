package org.weixin4j.pay.component;

import org.weixin4j.pay.Configuration;
import org.weixin4j.pay.WeixinPay;
import org.weixin4j.pay.WeixinPayException;
import org.weixin4j.pay.http.HttpsClient;
import org.weixin4j.pay.http.Response;
import org.weixin4j.pay.model.pay.UnifiedOrder;
import org.weixin4j.pay.model.pay.UnifiedOrderResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * 订单组件
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class OrderComponent extends AbstractComponent {

    public OrderComponent(WeixinPay weixinPay) {
        super(weixinPay);
    }

    /**
     * 统一下单
     *
     * @param unifiedOrder 统一下单对象
     * @return 下单返回结果对象
     * @throws org.weixin4j.pay.WeixinPayException 微信操作异常
     */
    public UnifiedOrderResult payUnifiedOrder(UnifiedOrder unifiedOrder) throws WeixinPayException {
        //将统一下单对象转成XML
        String xmlPost = unifiedOrder.toXML(weixinPay.getWeixinPayConfig().getMchKey());
        if (Configuration.isDebug()) {
            System.out.println("调试模式_统一下单接口 提交XML数据：" + xmlPost);
        }
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //提交xml格式数据
        Response res = http.postXmlWithOutCert("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlPost);
        //获取微信平台下单接口返回数据
        String xmlResult = res.asString();
        try {
            JAXBContext context = JAXBContext.newInstance(UnifiedOrderResult.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            UnifiedOrderResult result = (UnifiedOrderResult) unmarshaller.unmarshal(new StringReader(xmlResult));
            return result;
        } catch (JAXBException ex) {
            return null;
        }
    }
}
