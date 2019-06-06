package org.weixin4j.pay.model.rsa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * RsaXml
 *
 * @author yangqisheng
 */
@XmlRootElement(name = "xml")
public class RsaXml {

    /**
     * 返回状态码
     *
     * SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    private String return_code;
    /**
     * 返回信息
     *
     * 返回信息，如非空，为错误原因
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
     * RSA 公钥
     */
    private String pub_key;

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

    public String getPub_key() {
        return pub_key;
    }

    @XmlElement(name = "pub_key")
    public void setPub_key(String pub_key) {
        this.pub_key = pub_key;
    }

    @Override
    public String toString() {
        return "RsaXml{"
                + "return_code='" + return_code + '\''
                + ", return_msg='" + return_msg + '\''
                + ", result_code='" + result_code + '\''
                + ", err_code='" + err_code + '\''
                + ", err_code_des='" + err_code_des + '\''
                + ", mch_id='" + mch_id + '\''
                + ", pub_key='" + pub_key + '\''
                + '}';
    }
}
