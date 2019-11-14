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

/**
 * 现金红包对象
 * 
 * <p>
 * 用于企业向微信用户个人发现金红包</p>
 *
 * <p>
 * 目前支持向指定微信用户的openid发放指定金额红包。</p>
 *
 * <b>是否需要证书</b>
 *
 * 是（证书及使用说明详见https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3）
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class SendRedPack {

    /**
     * 商户订单号
     *
     * 商户订单号（每个订单号必须唯一）
     *
     * 组成：mch_id+yyyymmdd+10位一天内不能重复的数字。
     */
    private String mch_billno;
    /**
     * 公众账号appid
     */
    private String wxappid;
    /**
     * 商户名称
     *
     * 红包发送者名称
     */
    private String send_name;
    /**
     * 用户openid
     *
     * 接受红包的用户
     *
     * 用户在wxappid下的openid
     */
    private String re_openid;
    /**
     * 付款金额
     *
     * 付款金额，单位分
     */
    private int total_amount;
    /**
     * 红包祝福语
     */
    private String wishing;
    /**
     * Ip地址
     */
    private String client_ip;
    /**
     * 活动名称
     */
    private String act_name;
    /**
     * 备注
     */
    private String remark;

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    
}
