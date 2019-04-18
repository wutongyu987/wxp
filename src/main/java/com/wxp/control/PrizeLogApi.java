package com.wxp.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxp.Config;
import com.wxp.bean.AddressBean;
import com.wxp.bean.BuyerBean;
import com.wxp.bean.PrizeCodeLogBean;
import com.wxp.bean.PrizeLogBean;
import com.wxp.common.util.JsonUtil;
import com.wxp.dao.AddressDao;
import com.wxp.dao.PrizeLogDao;
import com.wxp.service.PrizeService;
import com.wxp.service.WxPayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;



/**
 * create by ff on 2018/11/23
 */
@Controller
@ResponseBody
@RequestMapping("/prize")
public class PrizeLogApi {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Resource
    private PrizeService prizeService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private AddressDao addressDao;
    @Resource
    private PrizeLogDao prizeLogDao;


    @RequestMapping("/get")
    public Object get(HttpSession session) throws Exception {
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        logger.info("获取兑奖结果,用户:"+ JSON.toJSONString(buyerBean));

        String prizeUuid = (String) session.getAttribute("cjId");
        logger.info("兑奖uuid:"+ prizeUuid);
        if (prizeUuid==null){
            logger.info("未中奖："+ prizeUuid);
            throw new Exception("请扫描二维码再来抽奖");
        }else {
            Integer id = prizeService.cj(prizeUuid, buyerBean.getId(),prizeUuid);
            return JsonUtil.genSuccess(id);
        }
    }





//    获取所有奖品信息
    @RequestMapping("/getPrizes")
    public Object getPrizes(){
        List<HashMap<String,Object>> list = prizeService.getPrizes();
        logger.info("获取所有奖品信息:"+JSON.toJSONString(list));
        return JsonUtil.genSuccess(list);
    }


//获取奖品列表
    @RequestMapping("/getPrizeList")
    public Object getPrizeList(HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        List list = prizeService.getCashList(buyerBean.getId());
        logger.info("获取奖品信息列表:"+ JSON.toJSONString(list.get(0)));
        return JsonUtil.genSuccess(list);
    }

    //兑奖
    @RequestMapping("/cash")
    public Object cash(@RequestBody JSONObject data, HttpServletRequest request) throws Exception {
        Integer id = data.getInteger("id");
        logger.info("正式兑奖,兑奖id:"+ id);

        PrizeLogBean prizeLogBean = prizeService.getLogById(id);
        BuyerBean buyerBean = (BuyerBean) request.getSession().getAttribute("buyer");

        //      判断是否是红包,如果是，则直接兑换红包
        if (prizeLogBean.getPrizeId()== Config.REDPACKET_88 ||
                prizeLogBean.getPrizeId()== Config.REDPACKET_36_6||
                prizeLogBean.getPrizeId()== Config.REDPACKET_18_8 ||
                prizeLogBean.getPrizeId()== Config.REDPACKET_16_6||
                prizeLogBean.getPrizeId()== Config.REDPACKET_8_8||
                prizeLogBean.getPrizeId()== Config.REDPACKET_6_6||
                prizeLogBean.getPrizeId()== Config.REDPACKET_1_88||
                prizeLogBean.getPrizeId()== Config.REDPACKET_1_66||
                prizeLogBean.getPrizeId()== Config.REDPACKET_0_88||
                prizeLogBean.getPrizeId()== Config.REDPACKET_0_66
                ){
//            测试关闭兑换
            System.out.println("红包兑换:"+JSON.toJSONString(buyerBean)+" 兑奖信息："+JSON.toJSONString(prizeLogBean));
            String resultCode= wxPayService.sendRedPackage(prizeLogBean.getId()+"",buyerBean.getOpenId(),prizeLogBean.getPrice()+"",request);
            //判断微信接口返回值
            if(resultCode.equals("SUCCESS")){
                //如果成功从priceLog表中删除相应信息（逻辑删除将status设为未启用）
                prizeLogDao.deletePrizeLog(prizeLogBean.getId(),buyerBean.getId());
                return JsonUtil.genSuccess();
            }else {
                return  JsonUtil.genError();
            }
        }
        Integer addressId = data.getInteger("addressId");
        if (id==null){
            return JsonUtil.genError("id 不能为空");
        }
        if (addressId==null){
            return JsonUtil.genError("addressid 不能为空");
        }
//        BuyerBean buyerBean = (BuyerBean) request.getSession().getAttribute("buyer");
        logger.info("正式兑奖,兑奖人信息:"+ JSON.toJSONString(buyerBean));
//        PrizeLogBean prizeLogBean = prizeService.getLogById(id);
        logger.info("正式兑奖,奖品记录信息:"+ JSON.toJSONString(prizeLogBean));
//        判断兑奖者是否是本人
        if (prizeLogBean.getBuyerId()!=buyerBean.getId()){
            logger.info("兑奖人和中奖人信息不一致:"+ JSON.toJSONString(buyerBean));
            throw  new Exception("兑奖人和中奖人信息不一致");
        }

//      获取获奖信息地址
        AddressBean addressBean = addressDao.findById(addressId);
        if (addressBean==null){
            return JsonUtil.genError("用户收货地址获取错误");
        }
        prizeLogBean.setPostCode(addressBean.getPostCode());
        prizeLogBean.setProvince(addressBean.getProvince());
        prizeLogBean.setCity(addressBean.getCity());
        prizeLogBean.setAddress(addressBean.getAddress());
        prizeLogBean.setAdname(addressBean.getName());
        prizeLogBean.setAdphone(addressBean.getPhone());
        prizeLogBean.setStatus(2);
        prizeLogBean.setUpdateId(buyerBean.getId());
        prizeService.cash(prizeLogBean);

        return JsonUtil.genSuccess();
    }
}
