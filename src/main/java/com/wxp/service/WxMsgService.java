package com.wxp.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.wxp.Config;
import com.wxp.WxConfig;
import com.wxp.common.util.JsonUtil;
import com.wxp.common.util.OkhttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * create by ff on 2018/12/18
 */

@Service
public class WxMsgService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private static LinkedBlockingQueue<JSONObject> dealQueue = new LinkedBlockingQueue<JSONObject>();
    private MessageThread messageThread = null;

    @Autowired
    private PrizeService prizeService;
    @Autowired
    private WxPayService wxPayService;
    /**
     * 回复按钮点击事件
     * @param eventKey 场景id
     * @param fromUserName 公众号id
     * @param toUserName 发送给用户的openid
     * @return
     * @throws Exception
     */
    public String eventClick(String eventKey,String fromUserName,String toUserName) throws Exception {
        int currentTime = (int) (System.currentTimeMillis()/1000);
        Map<String,String> xmlMap=null;
        if (eventKey==null){
            return null;
        }else if (eventKey.equals("introduce")){//猩愿机介绍
            xmlMap = new HashMap<>();
            xmlMap.put("ToUserName",toUserName);
            xmlMap.put("FromUserName",fromUserName);
            xmlMap.put("CreateTime",currentTime+"");
            xmlMap.put("MsgType","text");
            xmlMap.put("Content","猩愿机属于自动贩卖机\n" +
                    "一个更符合广大群众的\n" +
                    "一种新颖、智能、方便的新产品\n\n" +
                    "猩愿机更多的是充满神秘感\n" +
                    "带有娱乐性质和物超所值\n" +
                    "并赋予发展机会\n\n" +
                    "移动扫码支付\n" +
                    "这些都是市面上现有机器无法媲美的\n\n" +
                    "机器内分别是30元-1000元\n" +
                    "价值不等的神秘礼品\n" +
                    "商品种类繁多，\n" +
                    "其中不定时会在机器内\n" +
                    "出现不同的高价值产品");
        }else if (eventKey.equals("custom")){
            //猩愿机客服信息
            xmlMap = new HashMap<>();
            xmlMap.put("ToUserName",toUserName);
            xmlMap.put("FromUserName",fromUserName);
            xmlMap.put("CreateTime",currentTime+"");
            xmlMap.put("MsgType","text");
            xmlMap.put("Content","客服联系电话：18112710926 \n" +
                    "客服联系微信号：xyji1888 \n" +
                    "如果您在使用猩愿机的过程中遇到任何问题\n" +
                    "请联系客服人员我们随时在线");
        }
        String xml = WXPayUtil.mapToXml(xmlMap);
        logger.info("TEST++++++++"+xml);
        return xml;
    }



    /**
     * 回复关注消息
     * @param eventKey 场景id
     * @param fromUserName 公众号id
     * @param toUserName 发送给用户的openid
     * @return
     * @throws Exception
     */
    public String eventSubscribe(String eventKey, String fromUserName, String toUserName, HttpServletRequest request) throws Exception{
        int currentTime = (int) (System.currentTimeMillis()/1000);
        Map<String,String> xmlMap=null;

        if (eventKey==null){//用户正常关注，无场景值
            xmlMap = new HashMap<>();
            xmlMap.put("ToUserName",toUserName);
            xmlMap.put("FromUserName",fromUserName);
            xmlMap.put("CreateTime",currentTime+"");
            xmlMap.put("MsgType","text");
            xmlMap.put("Content","猩愿机属于自动贩卖机\n" +
                    "一个更符合广大群众的\n" +
                    "一种新颖、智能、方便的新产品\n\n" +
                    "猩愿机更多的是充满神秘感\n" +
                    "带有娱乐性质和物超所值\n" +
                    "并赋予发展机会");
            xmlMap.put("Content","回复关键字“猩愿福利”即可获取抽奖参与步骤");
            String xml = WXPayUtil.mapToXml(xmlMap);
            return xml;
        }else{
            xmlMap = new HashMap<>();
            xmlMap.put("ToUserName",toUserName);
            xmlMap.put("FromUserName",fromUserName);
            xmlMap.put("CreateTime",currentTime+"");
            xmlMap.put("MsgType","text");
            xmlMap.put("Content","点击“点击个人中心→扫码兑奖领取现金红包”^_^");
            String xml = WXPayUtil.mapToXml(xmlMap);
            return xml;

        }
    }

    /**
     * 回复用户发送的消息
     * @param content
     * @param fromUserName
     * @param toUserName
     * @return
     * @throws Exception
     */
    public void returnMsg(String content,String fromUserName,String toUserName) throws Exception {
        logger.info(content);
        String str = "{\"reqType\":0,\"perception\": {\"inputText\": {\"text\": \""+content+"\"}}," +
                "\"userInfo\": {\"apiKey\": \"c332fc047fae42ecb0dddf5b8c953eac\",\"userId\": \"9cd088161d7cf5e5\"}}";
        String url = "http://openapi.tuling123.com/openapi/api/v2";
        String res = OkhttpUtil.postJson(url,str);
        logger.info("图灵机返回："+res);
        if(res.contains("results")){
            JSONObject resObj = JSON.parseObject(res);
            String result = resObj.getJSONArray("results").get(0).toString();
            JSONObject resultObj = JSON.parseObject(result);
            logger.info("resobj"+resultObj.toJSONString());
            String text = resultObj.getJSONObject("values").getString("text");
            JSONObject send = new JSONObject();
            send.put("touser",toUserName);
            send.put("msgtype","text");
            JSONObject textObj = new JSONObject();
            textObj.put("content",text);
            send.put("text",textObj);
            logger.info("put"+send.toJSONString());
            dealQueue.put(send);
        }
    }


    public void start(){
        logger.info("启动发送微信消息线程");
        if (messageThread==null){
            messageThread = new MessageThread();
            messageThread.start();
        }
    }

    public void sendMsg(JSONObject msg){
        try {
            dealQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public void returnUserMsg(String content, String toUserName, String fromUserName) throws Exception {
//           logger.info(content);
//           logger.info(toUserName);
//            JSONObject send = new JSONObject();
//            send.put("touser",fromUserName);
//            send.put("msgtype","text");
//            JSONObject textObj = new JSONObject();
//            textObj.put("content","感谢猩宝宝的支持!\n"+"请完成以下步骤方可参与抽奖活动：\n"+"1、将下文分享至朋友圈，分享时不得设置分组或不可见。\n"
//                                    +"2、保存截图发送至本公众号，并回复“报名”。\n"+"注意：截图重复将被取消资格。\n");
//            send.put("text",textObj);
//            logger.info("put"+send.toJSONString());
//            dealQueue.put(send);
//    }

//    public void returnUserBaoMingMsg(String content, String toUserName, String fromUserName) throws Exception {
//        logger.info(content);
//
//        JSONObject send = new JSONObject();
//        send.put("touser",fromUserName);
//        send.put("msgtype","text");
//        JSONObject textObj = new JSONObject();
//        textObj.put("content","恭喜猩宝宝报名成功！！！");
//        send.put("text",textObj);
//        logger.info("put"+send.toJSONString());
//        dealQueue.put(send);
//    }

//    public void returnArticleMsg(String content, String toUserName, String fromUserName) throws Exception{
//        JSONObject send = new JSONObject();
//        send.put("touser",fromUserName);
//        send.put("CreateTime",new Date().getTime());
//        send.put("msgtype","news");
//        JSONObject news =new JSONObject();
//        JSONArray articles =new JSONArray();
//        JSONObject list =new JSONObject();
//        list.put("title","【猩愿福利】又双叒叕是\"免费\"送 "); //标题
//        list.put("description","【猩愿福利】又双叒叕是\"免费\"送 "); //描述
//        list.put("url","https://mp.weixin.qq.com/s?__biz=MzU1ODU1ODEwNg==&mid=2247484617&idx=1&sn=ca494f9f351b8a2558fc417d51a3b1bc&chksm=fc25fa3ccb52732a6e8dcf18091318a85b749a12cd6ff477a419dcc81f45485a11f27d27447b&token=394423510&lang=zh_CN#rd"); //点击图文链接跳转的地址
//        list.put("picurl","https://mmbiz.qpic.cn/mmbiz_jpg/GmQkkFCF21W1X74Sa9KOALaa1nx3MAe1NhLYvWR1RF7Kw0LFjey2plh2K5wzSHxdCd9s281uKRPxOorQbuhmyg/0?wx_fmt=jpeg"); //图文链接的图片
//        articles.add(list);
//        news.put("articles", articles);
//        send.put("news",news);
//        logger.info("put"+send.toJSONString());
//        dealQueue.put(send);
//    }
//

    private class MessageThread extends Thread{
        @Override
        public void run() {
            logger.info("thread run");
            JSONObject jsonObject= null;
            try {
                while ((jsonObject = dealQueue.take())!=null){
                    String json = jsonObject.toJSONString();
                    logger.info("收到消息:"+json);
                    String msgType = jsonObject.getString("msgtype");
                    if (msgType.equals("text")){//发送文字消息
                        String token = WxConfig.getAccessToken();
                        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token;
                        String str = OkhttpUtil.postJson(url,json);
                        logger.info(str);
                    }else if (msgType.equals("news")){
                        String token = WxConfig.getAccessToken();
                        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token;
                        String str = OkhttpUtil.postJson(url,json);
                        logger.info(str);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }




}
