package org.weixin4j.pay.model.paywallet;

/**
 * 企业付款零钱
 *
 * @author yangqisheng
 */
public class TransPayWallet {

    /**
     * 商户账号appid
     *
     * 申请商户号的appid或商户号绑定的appid
     */
    private String mch_appid;
    /**
     * 商户订单号
     */
    private String partner_trade_no;
    /**
     * 用户openid
     */
    private String openid;
    /**
     * 是否校验用户姓名
     */
    private boolean check_name;
    /**
     * 收款用户姓名
     */
    private String re_user_name;
    /**
     * 付款金额：RMB分
     */
    private int amount;
    /**
     * 企业付款备注
     */
    private String desc;
    /**
     * Ip地址
     *
     * 该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。
     */
    private String spbill_create_ip;

    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public boolean getCheck_name() {
        return check_name;
    }

    public void setCheck_name(boolean check_name) {
        this.check_name = check_name;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

}
