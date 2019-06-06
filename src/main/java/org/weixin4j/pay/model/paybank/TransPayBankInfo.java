package org.weixin4j.pay.model.paybank;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 查询企业付款到银行卡信息
 *
 * @author yangqisheng
 * @since 1.0.0
 */
@XmlRootElement(name = "xml")
public class TransPayBankInfo {

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
     * 商户号
     */
    private String mch_id;
    /**
     * 商户企业付款单号
     */
    private String partner_trade_no;
    /**
     * 微信企业付款单号
     */
    private String payment_no;
    /**
     * 银行卡号
     */
    private String bank_no_md5;
    /**
     * 用户真实姓名
     */
    private String true_name_md5;
    /**
     * 代付金额
     */
    private int amount;
    /**
     * 代付单状态
     */
    private String status;
    /**
     * 手续费金额
     */
    private int cmms_amt;
    /**
     * 商户下单时间
     */
    private String create_time;
    /**
     * 成功付款时间
     */
    private String pay_succ_time;
    /**
     * 失败原因
     */
    private String reason;

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

    public String getPayment_no() {
        return payment_no;
    }

    @XmlElement(name = "payment_no")
    public void setPayment_no(String payment_no) {
        this.payment_no = payment_no;
    }

    public String getBank_no_md5() {
        return bank_no_md5;
    }

    @XmlElement(name = "bank_no_md5")
    public void setBank_no_md5(String bank_no_md5) {
        this.bank_no_md5 = bank_no_md5;
    }

    public String getTrue_name_md5() {
        return true_name_md5;
    }

    @XmlElement(name = "true_name_md5")
    public void setTrue_name_md5(String true_name_md5) {
        this.true_name_md5 = true_name_md5;
    }

    public int getAmount() {
        return amount;
    }

    @XmlElement(name = "amount")
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    @XmlElement(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }

    public int getCmms_amt() {
        return cmms_amt;
    }

    @XmlElement(name = "cmms_amt")
    public void setCmms_amt(int cmms_amt) {
        this.cmms_amt = cmms_amt;
    }

    public String getCreate_time() {
        return create_time;
    }

    @XmlElement(name = "create_time")
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPay_succ_time() {
        return pay_succ_time;
    }

    @XmlElement(name = "pay_succ_time")
    public void setPay_succ_time(String pay_succ_time) {
        this.pay_succ_time = pay_succ_time;
    }

    public String getReason() {
        return reason;
    }

    @XmlElement(name = "reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

}
