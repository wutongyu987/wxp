package com.wxp.service;

import com.alibaba.druid.util.StringUtils;
import com.wxp.bean.BuyerBean;
import com.wxp.dao.BuyerDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BuyerService {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Resource
    private BuyerDao buyerDao;

    /**
     * 登录
     * 用户不存在则注册，用户存在则登录
     * @param buyerBean
     * @return
     */
    public BuyerBean login(BuyerBean buyerBean) {
        BuyerBean buyerBean1 = buyerDao.findByOpenId(buyerBean.getOpenId());
        if (buyerBean1==null){
            buyerBean.setPoints(0);
            buyerBean.setZan(0);
            buyerDao.save(buyerBean);
            return buyerBean;
        }else {
            return buyerBean1;
        }
    }

    public BuyerBean haveRegiste(String openId){
        return buyerDao.findByOpenId(openId);
    }

    public BuyerBean findById(Integer id){
        return buyerDao.findById(id);
    }
}
