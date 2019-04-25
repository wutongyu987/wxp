package com.wxp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.wxp.Config;
import com.wxp.WxConfig;
import com.wxp.bean.BuyerBean;
import com.wxp.bean.PrizeLogBean;
import com.wxp.dao.BuyerDao;
import com.wxp.dao.PrizeLogDao;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class WxPayService {

    org.apache.log4j.Logger logger = Logger.getLogger(this.getClass().getName());
    private WXPay wxPay = new WXPay(WxConfig.getPayInstance());

    @Resource
    private PrizeLogDao prizeLogDao;
    @Resource
    private BuyerDao buyerDao;


    /**
     * 微信统一下单
     * @return
     */
    public JSONObject unifiedorder(Map<String,String> data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",0);
        try {
            String tradeType = data.get("trade_type");
            Map<String, String> result = wxPay.unifiedOrder(data);//微信sdk，统一下单接口
            logger.info("统一下单结果"+JSON.toJSONString(result));
            if (!"SUCCESS".equals(result.get("result_code"))){
                jsonObject.put("object",result);
                jsonObject.put("msg","统一下单失败");
                return jsonObject;
            }
            if (!WXPayUtil.isSignatureValid(result, WxConfig.SECRET)){
                jsonObject.put("msg","验证签名失败");
                return jsonObject;
            }
            jsonObject.put("result",0);
            if ("JSAPI".equals(tradeType)){//小程序，微信公众号
                Map<String,String> tt = new HashMap<>();
                tt.put("appId",result.get("appid"));
                tt.put("nonceStr",result.get("nonce_str"));
                tt.put("timeStamp",System.currentTimeMillis()/1000+"");
                tt.put("package","prepay_id="+result.get("prepay_id"));
                tt.put("signType","MD5");
                String sign = WXPayUtil.generateSignature(tt, WxConfig.SECRET);
                tt.put("paySign",sign);
                jsonObject.put("object",tt);
            }
            if ("NATIVE".equals(tradeType)){//扫码
                jsonObject.put("url",result.get("code_url"));
            }
            if ("APP".equals(tradeType)){//app
                jsonObject.put("object",result);
            }
            if ("MWEB".equals(tradeType)){//H5
                jsonObject.put("url",result.get("mweb_url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

//    发红包

    /**
     *
     * @param no 订单号
     * @param openId openid
     * @param amount 金额
     * @param request request请求
     * @return
     * @throws Exception 异常报错
     */
    public String sendRedPackage(String no, String openId, String amount, HttpServletRequest request) throws Exception {
        Map<String, String> data = new HashMap<>();
        String companyName = "猩愿机";
        data.put("nonce_str",WXPayUtil.generateNonceStr());
        data.put("mch_billno",no);//商户订单
        data.put("mch_id",WxConfig.MCHID);
        data.put("wxappid", Config.APPID);
        data.put("send_name",companyName);
        data.put("re_openid",openId);
        data.put("total_amount",amount);//付款金额单位分
        data.put("total_num","1");//发放人数
        data.put("wishing","恭喜你获取红包奖励");
        data.put("client_ip",getRemoteHost(request));
        data.put("act_name","猩愿机抽奖活动");
        data.put("remark","猜越多得越多，快来抢！");//备注
        data.put("scene_id","PRODUCT_2");
        data.put("sign",WXPayUtil.generateSignature(data, WxConfig.SECRET, WXPayConstants.SignType.MD5));//备注

        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
        String respXml = wxPay.requestWithCert(url, data, 8000, 10000);
        Map<String, String> result = WXPayUtil.xmlToMap(respXml);
        String resultCode = "";
        for(Map.Entry<String,String> entity : result.entrySet()) {
            if (entity.getKey().equals("result_code")) {
                resultCode = entity.getValue();
            }
        }
        logger.info("<--发红包返回结果-->"+result+"\n"+new Date());
        return resultCode;
    }



    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    public void pushUserRedPackage(String fromUserName,HttpServletRequest request){
        try {
            String buyerId = buyerDao.getBuyerId(fromUserName);
            PrizeLogBean prizeLogBean = prizeLogDao.getPrizeLogByBuyerId(buyerId);
            logger.info("<--GET_USER_PRIZR_LOG-->:"+"\tPRIZE_ID:\t"+prizeLogBean.getId()+"\tBUYER_ID:\t"+prizeLogBean.getBuyerId()+"\tPRIZE:\t"+prizeLogBean.getPrice());
            String result = PushRedPackage(String.valueOf(prizeLogBean.getId()),fromUserName,String.valueOf(prizeLogBean.getPrice()),request);

            if(result.equals("SUCCESS")){
                prizeLogDao.updateCashPrizeLog(prizeLogBean.getId(),Config.JIN_YONG,new Date(),buyerId);
                logger.info("<---PUSH_RED_PACKAGE--->:"+""+"\tOPENID:\t"+fromUserName+"\t"+"BUYER_ID:\t"+buyerId+"\tCASH:\t"+prizeLogBean.getPrice()+"\tDATE:\t"+new Date());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String PushRedPackage(String no, String openId, String amount, HttpServletRequest request) throws Exception{
        Map<String, String> data = new HashMap<>();
        String companyName = "猩愿机";
        data.put("nonce_str",WXPayUtil.generateNonceStr());
        data.put("mch_billno",no);//商户订单
        data.put("mch_id",WxConfig.MCHID);
        data.put("wxappid", Config.APPID);
        data.put("send_name",companyName);
        data.put("re_openid",openId);
        data.put("total_amount",amount);//付款金额单位分
        data.put("total_num","1");//发放人数
        data.put("wishing","恭喜你获取红包奖励");
        data.put("client_ip",getRemoteHost(request));
        data.put("act_name","猩愿机抽奖活动");
        data.put("remark","猜越多得越多，快来抢！");//备注
        data.put("scene_id","PRODUCT_2");
        data.put("sign",WXPayUtil.generateSignature(data, WxConfig.SECRET, WXPayConstants.SignType.MD5));//备注

        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
        String respXml = wxPay.requestWithCert(url, data, 8000, 10000);
        Map<String, String> result = WXPayUtil.xmlToMap(respXml);
        String resultCode = "";
        for(Map.Entry<String,String> entity : result.entrySet()) {
            if (entity.getKey().equals("result_code")) {
                resultCode = entity.getValue();
            }
        }
        logger.info("<--推送红包返回结果-->"+result+"\n"+new Date());
        return resultCode;
    }




}
