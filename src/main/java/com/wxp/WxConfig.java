package com.wxp;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.wxp.common.util.OkhttpUtil;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信支付配置文件
 */
public class WxConfig implements WXPayConfig {

    public final static  String APPID="wx7dee141dcedc0421";//应用id
    public final static  String SECRET="xyj185568014781qaz2wsx3edc4rfv5t";//支付秘钥
    public final static  String MCHID="1504738871";//微信支付商户号
    public final static  String NOTIFYURL="https://www.xingyuanji.com/wish/wxpay/notify";//异步结果接口
    public final static String APPSECRET="ee702e5c923782990236ce7f2decdff4";



    private byte[] certData;
    private static WxConfig wxConfig;


    private void setCert() throws IOException {
        URL url = this.getClass().getResource("/apiclient_cert.p12");
        File file = new File(url.getFile());
        InputStream inputStream = new FileInputStream(file);
        certData = new byte[(int) file.length()];
        inputStream.read(certData);
        inputStream.close();
    }

    public static WxConfig getPayInstance(){
        if (wxConfig==null){
            synchronized (WxConfig.class){
                if (wxConfig==null){
                    wxConfig = new WxConfig();
                    try {
                        wxConfig.setCert();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return wxConfig;
    }

    @Override
    public String getAppID() {
        return APPID;
    }

    @Override
    public String getMchID() {
        return MCHID;
    }

    @Override
    public String getKey() {
        return SECRET;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

//    ===================================支付


    private static String accessToken=null;
    private static String ticket=null;
    private static long ticketTime=0;
    private static long accessTime=0;
    /**
     * 获取微信默认签名
     * @return
     */
    public static String sign(String url,String[] apiList){
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String jsapi_ticket = "kgt8ON7yVITDhtdwci0qeWAaH_JaPtnIxZmV4ef8POqF5P9fGi0nsvi0kWgYYNYCZ3DZzNoi1LuiGhsi3IPw3w";
        String string1;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp+
                "&url="+url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        String config;
        StringBuilder sb = new StringBuilder();
        sb.append("url:'").append(url).append("',\n");
        sb.append("jsapi_ticket:'").append(jsapi_ticket).append("',\n");
        sb.append("nonceStr:'").append(nonce_str).append("',\n");
        sb.append("timestamp:'").append(timestamp).append("',\n");
        sb.append("signature:'").append(signature).append("',\n");
        sb.append("jsApiList:[");
        if (apiList==null || apiList.length==0){
            sb.append("]\n");
            config=sb.toString();
        }else {
            for (String str:apiList){
                sb.append("'").append(str).append("',");
            }
            config=sb.substring(0,sb.length()-1) + "]\n";
        }
        return config;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    /**
     * 获取accessToken
     * @return
     */
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
        System.out.println("accessToken =" + accessToken);
        return accessToken;
    }

    /**
     * 获取jsTicket
     * @return
     */
    private static String getTicket(){
        long nowTime = System.currentTimeMillis()/1000;
        if ((nowTime-ticketTime)<7200){
            return ticket;
        }
        ticketTime=nowTime;
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
        String str = OkhttpUtil.get(url);
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println(jsonObject.toJSONString());
        String ticket = jsonObject.getString("ticket");
        return ticket;
    }


    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }



    public static void main(String[] args) {
         WXPay wxPay = new WXPay(WxConfig.getPayInstance());
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", "1234");
        data.put("device_info", "local");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "117.89.169.217");
        data.put("notify_url", NOTIFYURL);
        data.put("trade_type", "NATIVE");
        data.put("product_id", "12");

        data.put("appid", WxConfig.APPID);
        data.put("mch_id", WxConfig.MCHID);
        data.put("nonce_str","5K8264ILTKCH16CQ2502SI8ZNMTM67VS");


        // data.put("time_expire", "20170112104120");

        try {
            Map<String, String> r = wxPay.unifiedOrder(data);
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
