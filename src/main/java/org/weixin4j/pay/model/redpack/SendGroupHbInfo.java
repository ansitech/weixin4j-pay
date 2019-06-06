package org.weixin4j.pay.model.redpack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 裂变红包领取信息
 *
 * @author yangqisheng
 */
@XmlRootElement(name = "hbinfo")
public class SendGroupHbInfo {

    private String openid;  //领取红包的openid
    private String amount;  //领取金额 
    private String rcv_time;//领取红包的时间 

    public String getOpenid() {
        return openid;
    }

    @XmlElement(name = "openid")
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAmount() {
        return amount;
    }

    @XmlElement(name = "amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRcv_time() {
        return rcv_time;
    }

    @XmlElement(name = "rcv_time")
    public void setRcv_time(String rcv_time) {
        this.rcv_time = rcv_time;
    }
}
