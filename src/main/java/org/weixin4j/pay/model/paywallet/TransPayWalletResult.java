package org.weixin4j.pay.model.paywallet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 企业付款零钱结果
 *
 * @author yangqisheng
 */
@XmlRootElement(name = "xml")
public class TransPayWalletResult {

    /**
     * 返回状态码
     */
    private String return_code;
    /**
     * 返回信息
     */
    private String return_msg;
    //以下字段在return_code为SUCCESS的时候有返回
    /**
     * 商户appid
     */
    private String mch_appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 随机字符串
     */
    private String nonce_str;
    /**
     * 业务结果
     */
    private String result_code;
    /**
     * 错误代码
     */
    private String err_code;
    /**
     * 错误代码描述
     */
    private String err_code_des;
    //以下字段在return_code 和result_code都为SUCCESS的时候有返回
    /**
     * 商户订单号
     */
    private String partner_trade_no;
    /**
     * 微信付款单号
     */
    private String payment_no;
    /**
     * 付款成功时间
     */
    private String payment_time;

    public String getReturn_code() {
        return return_code;
    }

    @XmlElement(name = "return_code")
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    @XmlElement(name = "return_msg")
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getMch_appid() {
        return mch_appid;
    }

    @XmlElement(name = "mch_appid")
    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    @XmlElement(name = "mch_id")
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    @XmlElement(name = "nonce_str")
    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getResult_code() {
        return result_code;
    }

    @XmlElement(name = "result_code")
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    @XmlElement(name = "err_code")
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    @XmlElement(name = "err_code_des")
    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    @XmlElement(name = "partner_trade_no")
    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getPayment_no() {
        return payment_no;
    }

    @XmlElement(name = "payment_no")
    public void setPayment_no(String payment_no) {
        this.payment_no = payment_no;
    }

    public String getPayment_time() {
        return payment_time;
    }

    @XmlElement(name = "payment_time")
    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

}
