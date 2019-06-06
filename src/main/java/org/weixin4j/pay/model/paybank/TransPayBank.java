package org.weixin4j.pay.model.paybank;

/**
 * 企业付款银行卡
 *
 * @author yangqisheng
 */
public class TransPayBank {

    /**
     * 商户企业付款单号
     */
    private String partner_trade_no;
    /**
     * 收款方银行卡号
     */
    private String enc_bank_no;
    /**
     * 收款方用户名
     */
    private String enc_true_name;
    /**
     * 收款方开户行
     * https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=24_4
     */
    private String bank_code;
    /**
     * 付款金额：RMB分
     */
    private Integer amount;
    /**
     * 付款说明
     */
    private String desc;

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getEnc_bank_no() {
        return enc_bank_no;
    }

    public void setEnc_bank_no(String enc_bank_no) {
        this.enc_bank_no = enc_bank_no;
    }

    public String getEnc_true_name() {
        return enc_true_name;
    }

    public void setEnc_true_name(String enc_true_name) {
        this.enc_true_name = enc_true_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    
}
