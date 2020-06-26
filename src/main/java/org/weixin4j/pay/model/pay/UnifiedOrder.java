package org.weixin4j.pay.model.pay;

import org.weixin4j.pay.util.SignUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一下单
 *
 * 接口文档：http://pay.weixin.qq.com/wiki/doc/api/index.php?chapter=9_1
 *
 * <b>应用场景</b>
 *
 * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付。
 *
 * <b>是否需要证书</b>
 *
 * 不需要
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class UnifiedOrder {

    /**
     * 小程序ID/公众号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 随机字符串
     */
    private String nonce_str;
    /**
     * 签名
     */
    private String sign;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 订单总金额，单位为分
     */
    private String total_fee;
    /**
     * 终端IP
     */
    private String spbill_create_ip;
    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    private String notify_url;
    /**
     * 交易类型 取值如下：JSAPI，NATIVE，APP
     *
     * 小程序取值如下：JSAPI
     */
    private String trade_type;
    /**
     * 用户标识
     */
    private String openid;
    /**
     * 商品ID
     */
    private String product_id;

    private Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", appid);
        map.put("body", body);
        map.put("mch_id", mch_id);
        map.put("nonce_str", nonce_str);
        map.put("notify_url", notify_url);
        if ("JSAPI".equals(trade_type)) {
            map.put("openid", openid);
        }
        map.put("out_trade_no", out_trade_no);
        if ("NATIVE".equals(trade_type)) {
            map.put("product_id", product_id);
        }
        map.put("spbill_create_ip", spbill_create_ip);
        map.put("total_fee", total_fee);
        map.put("trade_type", trade_type);
        return map;
    }

    public String toXML(String mchKey) {
        //进行数据签名
        Map<String, String> map = toMap();
        String sign = SignUtil.getSign(map, mchKey);
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<appid><![CDATA[").append(appid).append("]]></appid>");
        sb.append("<body><![CDATA[").append(body).append("]]></body>");
        sb.append("<mch_id><![CDATA[").append(mch_id).append("]]></mch_id>");
        sb.append("<nonce_str><![CDATA[").append(nonce_str).append("]]></nonce_str>");
        sb.append("<notify_url><![CDATA[").append(notify_url).append("]]></notify_url>");
        if ("JSAPI".equals(trade_type)) {
            sb.append("<openid><![CDATA[").append(openid).append("]]></openid>");
        }
        sb.append("<out_trade_no><![CDATA[").append(out_trade_no).append("]]></out_trade_no>");
        if ("NATIVE".equals(trade_type)) {
            sb.append("<product_id><![CDATA[").append(product_id).append("]]></product_id>");
        }
        sb.append("<spbill_create_ip><![CDATA[").append(spbill_create_ip).append("]]></spbill_create_ip>");
        sb.append("<total_fee><![CDATA[").append(total_fee).append("]]></total_fee>");
        sb.append("<trade_type><![CDATA[").append(trade_type).append("]]></trade_type>");
        sb.append("<sign><![CDATA[").append(sign).append("]]></sign>");
        sb.append("</xml>");
        return sb.toString();
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
