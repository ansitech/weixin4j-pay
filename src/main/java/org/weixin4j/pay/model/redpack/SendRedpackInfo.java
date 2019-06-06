/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weixin4j.pay.model.redpack;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 红包信息
 *
 * @author yangqisheng
 * @since 1.0.0
 */
@XmlRootElement(name = "xml")
public class SendRedpackInfo {

    /**
     * 字段名：返回状态码
     *
     * 必填：是
     *
     * 类型：String(16)
     *
     * 描述：SUCCESS/FAIL
     *
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    private String return_code;
    /**
     * 字段名：返回信息
     *
     * 必填：否
     *
     * 类型：String(128)
     *
     * 描述：返回信息，如非空，为错误原因
     *
     * 签名失败、参数格式校验错误等
     */
    private String return_msg;
    //*** 以下字段在return_code为SUCCESS的时候有返回 ***//
    private String result_code;     //业务结果  SUCCESS/FAIL
    private String err_code;        //错误代码
    private String err_code_des;    //错误代码描述
    //*** 以下字段在return_code 和result_code都为SUCCESS的时候有返回 ***//
    private String mch_billno;      //商户订单号
    private String mch_id;          //商户号
    private String detail_id;       //红包单号 
    private String status;          //红包状态
    private String send_type;       //发放类型
    private String hb_type;         //红包类型
    private int total_num;          //红包个数
    private int total_amount;       //红包总金额（单位分） 

    private String reason;          //发送失败原因
    private String send_time;       //发送红包时间
    private String refund_time;     //红包的退款时间（如果其未领取的退款）
    private int refund_amount;      //红包退款金额
    private String wishing;         //祝福语
    private String act_name;        //发红包的活动名称 
    private List<SendGroupHbInfo> hblist;  //裂变红包领取列表

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

    public String getMch_billno() {
        return mch_billno;
    }

    @XmlElement(name = "mch_billno")
    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getMch_id() {
        return mch_id;
    }

    @XmlElement(name = "mch_id")
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDetail_id() {
        return detail_id;
    }

    @XmlElement(name = "detail_id")
    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getStatus() {
        return status;
    }

    @XmlElement(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }

    public String getSend_type() {
        return send_type;
    }

    @XmlElement(name = "send_type")
    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public String getHb_type() {
        return hb_type;
    }

    @XmlElement(name = "hb_type")
    public void setHb_type(String hb_type) {
        this.hb_type = hb_type;
    }

    public int getTotal_num() {
        return total_num;
    }

    @XmlElement(name = "total_num")
    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    @XmlElement(name = "total_amount")
    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getReason() {
        return reason;
    }

    @XmlElement(name = "reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSend_time() {
        return send_time;
    }

    @XmlElement(name = "send_time")
    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getRefund_time() {
        return refund_time;
    }

    @XmlElement(name = "refund_time")
    public void setRefund_time(String refund_time) {
        this.refund_time = refund_time;
    }

    public int getRefund_amount() {
        return refund_amount;
    }

    @XmlElement(name = "refund_amount")
    public void setRefund_amount(int refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getWishing() {
        return wishing;
    }

    @XmlElement(name = "wishing")
    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getAct_name() {
        return act_name;
    }

    @XmlElement(name = "act_name")
    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public List<SendGroupHbInfo> getHblist() {
        return hblist;
    }

    @XmlElementWrapper(name = "hblist")
    @XmlElement(name = "hbinfo")
    public void setHblist(List<SendGroupHbInfo> hblist) {
        this.hblist = hblist;
    }
}
