package org.weixin4j.pay.model.paywallet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 查询企业付款到零钱信息
 *
 * @author yangqisheng
 * @since 1.0.0
 */
@XmlRootElement(name = "xml")
public class TransPayWalletInfo {

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
     * 商户单号
     */
    private String partner_trade_no;
    /**
     * 商户号的appid
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 付款单号
     */
    private String detail_id;
    /**
     * 转账状态
     *
     * SUCCESS:转账成功
     *
     * FAILED:转账失败
     *
     * PROCESSING:处理中
     */
    private String status;
    /**
     * 失败原因
     */
    private String reason;
    /**
     * 收款用户openid
     */
    private String openid;
    /**
     * 收款用户姓名
     */
    private String transfer_name;
    /**
     * 付款金额
     */
    private int payment_amount;
    /**
     * 转账时间
     */
    private String transfer_time;
    /**
     * 付款成功时间
     */
    private String payment_time;
    /**
     * 企业付款备注
     */
    private String desc;

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

    public String getMch_id() {
        return mch_id;
    }

    @XmlElement(name = "mch_id")
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    @XmlElement(name = "partner_trade_no")
    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getStatus() {
        return status;
    }

    @XmlElement(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    @XmlElement(name = "reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAppid() {
        return appid;
    }

    @XmlElement(name = "appid")
    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDetail_id() {
        return detail_id;
    }

    @XmlElement(name = "detail_id")
    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getOpenid() {
        return openid;
    }

    @XmlElement(name = "openid")
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTransfer_name() {
        return transfer_name;
    }

    @XmlElement(name = "transfer_name")
    public void setTransfer_name(String transfer_name) {
        this.transfer_name = transfer_name;
    }

    public int getPayment_amount() {
        return payment_amount;
    }

    @XmlElement(name = "payment_amount")
    public void setPayment_amount(int payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getTransfer_time() {
        return transfer_time;
    }

    @XmlElement(name = "transfer_time")
    public void setTransfer_time(String transfer_time) {
        this.transfer_time = transfer_time;
    }

    public String getPayment_time() {
        return payment_time;
    }

    @XmlElement(name = "payment_time")
    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getDesc() {
        return desc;
    }

    @XmlElement(name = "desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

}
