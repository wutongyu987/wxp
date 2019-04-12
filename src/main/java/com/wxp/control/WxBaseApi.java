package com.wxp.control;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.vdurmont.emoji.EmojiParser;
import com.wxp.Config;
import com.wxp.bean.BuyerBean;
import com.wxp.bean.PrizeCodeBean;
import com.wxp.common.util.EmojiConvertUtil;
import com.wxp.common.util.JsonUtil;
import com.wxp.common.util.OkhttpUtil;
import com.wxp.dao.BuyerDao;
import com.wxp.dao.PrizeCodeDao;
import com.wxp.service.BuyerService;
import com.wxp.service.WxMsgService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * create by ff on 2018/11/23
 */
@Controller
public class WxBaseApi {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private PrizeCodeDao prizeCodeDao;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private WxMsgService wxMsgService;
    @Resource
    private BuyerDao buyerDao;


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public Object testMsg(){
        JSONObject msg = new JSONObject();
        msg.put("touser","omkkb1D9Y9saP3QGUknwtLv0dZiM");
        msg.put("msgtype","text");
        msg.put("content","嘿！终于等到你~\n" +
                "\n" +
                "欢迎来到猩愿机的世界\n" +
                "这是一个幸运的公众号\n" +
                "\n" +
                "我们将全世界的小惊喜\n" +
                "都藏在了神秘的盒子里\n" +
                "\n" +
                "手机扫码即可开启惊喜礼物\n" +
                "欢迎你与我们分享哦");
        wxMsgService.sendMsg(msg);
        return "success";
    }


    @RequestMapping(value = "/connect",method = RequestMethod.GET)
    @ResponseBody
    public Object connect1(HttpServletRequest request, HttpServletResponse response){
        logger.info("验证");
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");//随机字符串
        logger.info("加密签名"+signature+" 时间戳"+timestamp+" 随机数"+nonce+" 随机字符串"+echostr);
        return echostr;
    }

    /**
     * 微信服务
     * @return
     */
    @RequestMapping(value = "/connect",method = RequestMethod.POST)
    @ResponseBody
    public Object connect(@RequestBody String string) throws Exception {
            logger.info("接收微信消息");
//            接收的内容转码
            string = new String(string.getBytes("ISO-8859-1"), "UTF-8");
            logger.info(string);
            String xml = "";
            Map<String, String> result = WXPayUtil.xmlToMap(string);
            String msgType = result.get("MsgType");
            String toUserName = result.get("ToUserName");
            logger.info(result);
            String fromUserName = result.get("FromUserName");

            if (msgType.equals("event")){
                String event = result.get("Event");
                String eventKey = result.get("EventKey");
                if (event.equals("subscribe")){//用户订阅公众号触发事件
                    xml = wxMsgService.eventSubscribe(eventKey,toUserName,fromUserName);
                }else if (event.equals("CLICK")){//菜单按钮点击事件
                    xml = wxMsgService.eventClick(eventKey,toUserName,fromUserName);
                }
            }else if (msgType.equals("text")){//接收消息
                String content = result.get("Content");
//                if(!StringUtils.isEmpty(content)){//消息回复
//                    wxMsgService.returnMsg(content,toUserName,fromUserName);
//                }
                if(content.equals("猩愿福利")){
                    wxMsgService.returnUserMsg(content,toUserName,fromUserName);
                    wxMsgService.returnArticleMsg(content,toUserName,fromUserName);
                }
                if(content.equals("报名")){
                    wxMsgService.returnUserBaoMingMsg(content,toUserName,fromUserName);
                }
            }
          logger.info("发送："+xml);
            return xml;
    }

    @RequestMapping("/towx/{id}")
    public String toWx(@PathVariable("id") String id){
        logger.info("扫码code:"+id);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx67cadbc58a45b2cd&redirect_uri=https%3a%2f%2fwww.xingyuanji.com%2fwxp%2flogin&response_type=code&scope=snsapi_userinfo&state="+id+"#wechat_redirect";
        return "redirect:"+url;
    }


    @RequestMapping("/userInfo")
    @ResponseBody
    public Object getUserInfo(HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        return JsonUtil.genSuccess(buyerBean);
    }


//    抽奖界面
    @RequestMapping("/login")
    public Object getToken(String code,String state, HttpSession session) throws UnsupportedEncodingException {
        PrizeCodeBean prizeCodeBean = prizeCodeDao.getByUid(state);
        if (prizeCodeBean==null || prizeCodeBean.getStatus()==1){
        return "Invalid";
        }
        session.setAttribute("cjId",state);
        login(code,session);
        return "cj";
    }

//    用户信息界面
    @RequestMapping("/account")
    public Object getAccount(String code,String state, HttpSession session) throws UnsupportedEncodingException {
        logger.info("微信公众号登录"+code+":::state"+state);
        login(code,session);
        return "my";
    }



    private void login(String code, HttpSession session) throws UnsupportedEncodingException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Config.APPID
                + "&secret=" + Config.SECRET + "&code=" + code
                + "&grant_type=authorization_code";
        String str = OkhttpUtil.get(url);
        System.out.println(str);
        JSONObject jsonObject = JSON.parseObject(str);
        String accessToken = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openid");
        BuyerBean buyerBean = buyerService.haveRegiste(openId);
        if (buyerBean==null) {
            //拉去用户信息
            url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
            str = OkhttpUtil.get(url);
            System.out.println("wxuser:"+str);
            jsonObject = JSON.parseObject(str);
            buyerBean = new BuyerBean();
            buyerBean.setAvatarUrl(jsonObject.getString("headimgurl"));
            //buyerBean.setNickName(jsonObject.getString("nickname"));
            buyerBean.setNickName(EmojiConvertUtil.emojiConvert(jsonObject.getString("nickname")));
            buyerBean.setSex(Integer.parseInt(jsonObject.getString("sex")));
            buyerBean.setOpenId(openId);
            buyerBean = buyerService.login(buyerBean);
        }
        session.setAttribute("buyer",buyerBean);
    }

}
