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
package org.weixin4j.pay.util;

import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.weixin4j.pay.Configuration;

/**
 * 签名算法
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class SignUtil {

    /**
     * 签名
     *
     * @param M 待签名参数
     * @param mchKey 商户密钥
     * @return 签名paySign
     */
    public static String getSign(Map<String, String> M, String mchKey) {
        //1.1 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String, String> sortParams = MapUtil.sortAsc(M);
        //1.2 使用URL键值对的格式
        String string1 = MapUtil.mapJoin(sortParams, false);
        if (Configuration.isDebug()) {
            System.out.println("#1.生成字符串：");
            System.out.println(string1);
        }
        //拼接签名字符串
        String stringSignTemp = string1 + "&key=" + mchKey;
        if (Configuration.isDebug()) {
            System.out.println("#2.连接商户key：");
            System.out.println(stringSignTemp);
        }
        //2.对string1进行MD5签名
        String sign = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        if (Configuration.isDebug()) {
            System.out.println("#3.md5编码并转成大写：");
            System.out.println("sign=" + sign);
        }
        return sign;
    }
}
