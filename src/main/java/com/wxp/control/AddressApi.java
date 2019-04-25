package com.wxp.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxp.bean.AddressBean;
import com.wxp.bean.BuyerBean;
import com.wxp.common.util.JsonUtil;
import com.wxp.dao.AddressDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by ff on 2018/11/23
 */
@Controller
@ResponseBody
@RequestMapping("/address")
public class AddressApi {
    org.apache.log4j.Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private AddressDao addressDao;


    //保存用户地址
    @RequestMapping("/save")
    public Object saveAddress(@RequestBody AddressBean addressBean, HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        logger.info("保存用户地址,用户:"+ JSON.toJSONString(buyerBean));
        logger.info("保存用户地址,地址:"+ JSON.toJSONString(addressBean));
        addressBean.setInsertId(buyerBean.getId());
        addressBean.setBuyerId(buyerBean.getId());
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",buyerBean.getId());
        condition.put("def",1);
        List list = addressDao.query(condition);
        if (list.size()>0){
            addressBean.setDef(0);
        }else {
            addressBean.setDef(1);
        }
        addressDao.save(addressBean);
        return JsonUtil.genSuccess();
    }


    //    根据用户id获取用户地址
    @RequestMapping("/getAddress")
    public Object getAddress(HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
//        Integer buyerId = buyerBean.getId();
        Map<String,Object> condition = new HashMap<>();
        condition.put("buyerId",buyerBean.getId());
        List list = addressDao.query(condition);
        return JsonUtil.genSuccess(list);
    }

    //    删除用户地址
    @RequestMapping("/deletesAddress")
    public Object deletesAddress(@RequestBody Integer[] ids,HttpSession session){
        logger.info("删除用户地址,地址id:"+ ids.toString());
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        addressDao.deletes(ids,buyerBean.getId());
        return JsonUtil.genSuccess();
    }

    //    删除用户地址
    @RequestMapping("/deleteAddressById")
    public Object deletesAddress(@RequestBody JSONObject data,HttpSession session){
        Integer addressId = data.getInteger("addressId");
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        Integer buyerId = buyerBean.getId();
        if (addressId==null){
            return JsonUtil.genError("默认地址不能为空");
        }
        logger.info("删除用户地址,地址id:"+addressId);
//        Integer[] ids =new Integer[]{addressId};
//        addressDao.deletes(ids,buyerBean.getId());
        addressDao.deleteAddressByid(addressId,buyerId);
        return JsonUtil.genSuccess();
    }


    @RequestMapping("/getById")
    public Object getById(@RequestBody JSONObject data,HttpSession session){
        Integer addressId = data.getInteger("addressId");
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        if (addressId==null){
            return JsonUtil.genError("地址id不能为空");
        }
        AddressBean addressBean = addressDao.findById(addressId);
        return JsonUtil.genSuccess(addressBean);
    }


    //更新用户地址
    @RequestMapping("/updateAddress")
    public Object updateAddress(@RequestBody AddressBean addressBean,HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        logger.info("更新用户地址,用户:"+ JSON.toJSONString(buyerBean));
        logger.info("更新用户地址,地址:"+ JSON.toJSONString(addressBean));
        Integer defInfo = addressDao.getUserAdressDef(buyerBean.getId());
        if(defInfo != null){
            addressBean.setDef(defInfo);
        }else{
            //如果表内DEF（默认地址状态）为空，设为非默认地址（1：默认 0：非默认）
            addressBean.setDef(0);
        }
        addressBean.setUpdateId(buyerBean.getId());
//        addressBean.setDef(null);
        addressBean.setBuyerId(buyerBean.getId());
        addressDao.update(addressBean);
        return JsonUtil.genSuccess();
    }

    //    设置默认收货地址
    @RequestMapping("/setDefAddress")
    public Object setDefAddress(@RequestBody JSONObject data,HttpSession session){
        BuyerBean buyerBean = (BuyerBean) session.getAttribute("buyer");
        Integer buyerId = buyerBean.getId();
        Integer addressId = data.getInteger("addressId");
        if (addressId==null){
            return JsonUtil.genError("默认地址不能为空");
        }
        addressDao.deleteDef(buyerId);
        AddressBean addressBean = new AddressBean();
        addressBean.setId(addressId);
        addressBean.setBuyerId(buyerId);
        addressBean.setDef(1);
        addressDao.update(addressBean);
        return JsonUtil.genSuccess();
    }

}
