package com.wxp;

import com.alibaba.fastjson.JSONObject;
import com.wxp.common.util.OkhttpUtil;

public class accessToken {

    private static String accessToken=null;
    private static long accessTime=0;

    public static String getAccessToken() {
        long nowTime = System.currentTimeMillis()/1000;
        if (accessToken != null && (nowTime-accessTime)<7200){
            return accessToken;
        }
        accessTime=nowTime;
        String grant_type = "client_credential";//获取access_token填写client_credential
        //这个url链接地址和参数皆不能变
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+Config.APPID+"&secret="+Config.SECRET;
        String str = OkhttpUtil.get(url);
        System.out.println("get access-token");
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println(jsonObject);
        accessToken = jsonObject.getString("access_token");
        return accessToken;
    }

    public static void main(String[] args) {
        System.out.println(getAccessToken());
    }
}
