package com.wxp.service;


import com.sun.corba.se.impl.interceptors.PICurrent;
import com.wxp.Config;
import com.wxp.bean.PrizeBean;
import com.wxp.bean.PrizeCodeBean;
import com.wxp.bean.PrizeCodeLogBean;
import com.wxp.bean.PrizeLogBean;
import com.wxp.dao.PrizeCodeDao;
import com.wxp.dao.PrizeDao;
import com.wxp.dao.PrizeLogDao;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * create by ff on 2018/8/6
 */
@Service
public class PrizeService {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    private PrizeLogDao prizeLogDao;
    @Resource
    private PrizeDao prizeDao;
    @Resource
    private PrizeCodeDao prizeCodeDao;



    public void preCash(PrizeLogBean prizeLogBean){
        prizeLogDao.save(prizeLogBean);
    }

    public void cash(PrizeLogBean prizeLogBean){
        prizeLogDao.cachePrize(prizeLogBean);
    }

    public PrizeLogBean getLogById(Integer id){
       return prizeLogDao.findById(id);
    }

//    获取兑奖信息列表
    public List<Map<String,Object>> getCashList(Integer buyerId){
        return prizeLogDao.findByBuyerId(buyerId);
    }

    public List<HashMap<String, Object>> getPrizes(){
        return prizeDao.findAll();
    }



//    获取抽奖结果
    @Transactional
    public Integer cj(String uid,Integer buyerid,String uuid) throws Exception {
        PrizeLogBean prizeLogBean = new PrizeLogBean();
        PrizeCodeBean prizeCodeBean = prizeCodeDao.getByUid(uid);
        //设置二维码状态位已经使用
        prizeCodeDao.use(uid,buyerid);
        Integer pId = null;
        //生成随机数
        double randomNum = Math.random();

        if(randomNum >=0 && randomNum <= Config.REDPACKET_0_66_PROBABILITY){
            pId = Config.REDPACKET_0_66;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(66);
            }
        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY &&
                randomNum <= Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY){

            pId = Config.REDPACKET_0_88;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(88);
            }

        }else  if (randomNum > Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY &&
                randomNum <= Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY){

            pId = Config.REDPACKET_1_66;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(166);
            }

        }else if(randomNum >= Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY &&
                randomNum <= Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                        Config.REDPACKET_1_88_PROBABILITY){

            pId = Config.REDPACKET_1_88;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(188);
            }

        }else if(randomNum >=
                        Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                        Config.REDPACKET_1_88_PROBABILITY&&
                randomNum <=
                        Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                        Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY){

            pId = Config.REDPACKET_6_6;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(660);
            }
        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY &&
                randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY){

            pId = Config.REDPACKET_8_8;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(880);
            }

        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY &&
                randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY){


            pId = Config.REDPACKET_16_6;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(1660);
            }

        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY &&
                randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY){

            pId = Config.REDPACKET_18_8;
            int priceNum = prizeDao.getPriceNum(pId);
            if(priceNum != 0){
                prizeLogBean.setPrice(1880);
            }

        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY &&
                randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY){

            Random random = new Random();
            boolean select = random.nextBoolean();

            if(select == true){
                pId = Config.REDPACKET_36_6;
                int priceNum = prizeDao.getPriceNum(pId);
                if(priceNum != 0){
                    prizeLogBean.setPrice(3660);
                }else{
                    pId = Config.REDPACKET_88;
                    int priceNum_88 = prizeDao.getPriceNum(pId);
                    if(priceNum_88 != 0){
                        prizeLogBean.setPrice(8800);
                }
            }

        }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY &&
                randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY){

                pId = Config.WANGHONG_DUCK;
                int priceNum = prizeDao.getPriceNum(pId);
                if(priceNum != 0) {
                    prizeLogBean.setPrice(2000);
                }

        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                Config.WANGHONG_DUCK_PROBABILITY &&
                randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY){

                    pId = Config.ZISHENGTANG_HANDRCREAM;
                    int priceNum1 = prizeDao.getPriceNum(pId);
                    if(priceNum1 != 0) {
                        prizeLogBean.setPrice(3500);
                    }

        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY &&
                randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY){

                        pId = Config.MARKTUBU_HUMIDIFIER;
                        int priceNum_m = prizeDao.getPriceNum(pId);
                        if(priceNum_m != 0) {
                            prizeLogBean.setPrice(4000);
                        }

        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY &&
                randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                Config.WANGHONG_BOLSTER_PROBABILITY){

                            pId = Config.WANGHONG_BOLSTER;
                            int priceNum_WANGHONG_BOLSTER = prizeDao.getPriceNum(pId);
                            if(priceNum_WANGHONG_BOLSTER != 0) {
                                prizeLogBean.setPrice(4800);
                            }

        }else if(randomNum >=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                Config.WANGHONG_BOLSTER_PROBABILITY &&
                 randomNum <=
                Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                 Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                 Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY){

                                pId = Config.LINEFRIENDS_TRASH;
                                int priceNum_LINEFRIENDS_TRASH = prizeDao.getPriceNum(pId);
                                if(priceNum_LINEFRIENDS_TRASH != 0) {
                                    prizeLogBean.setPrice(7900);
                                }

        }else if(randomNum >=
                 Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                 Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                 Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                 Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                 Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY &&
                randomNum <=
                 Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                 Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                 Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                 Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                 Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY){

                                    pId = Config.HOLIKA_EYESSHADOW;
                                    int priceNum_HOLIKA_EYESSHADOW = prizeDao.getPriceNum(pId);
                                    if(priceNum_HOLIKA_EYESSHADOW != 0) {
                                        prizeLogBean.setPrice(9900);
                                    }

        }else if(randomNum >=
                  Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                  Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                  Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                  Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                  Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY&&
                randomNum <=
                  Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                  Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                  Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                  Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                  Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                  Config.STARBARKS_GIFTCARD_PROBABILITY){

                pId = Config.STARBARKS_GIFTCARD;
                int priceNum_STARBARKS_GIFTCARD = prizeDao.getPriceNum(pId);
                if(priceNum_STARBARKS_GIFTCARD != 0) {
                    prizeLogBean.setPrice(20000);
                }

        }else if(randomNum >=
                   Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                   Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                   Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                   Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                   Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                   Config.STARBARKS_GIFTCARD_PROBABILITY &&
                randomNum <=
                   Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                   Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                   Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                   Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                   Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                   Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY){

                pId = Config.SHANMOSHI_VACUUMCUP;
                int priceNum_SHANMOSHI_VACUUMCUP = prizeDao.getPriceNum(pId);
                if(priceNum_SHANMOSHI_VACUUMCUP != 0) {
                    prizeLogBean.setPrice(23800);
                }

        }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY &&
                randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY){

                pId = Config.JBL_BLUESPEEKER;
                int priceNum_JBL_BLUESPEEKER = prizeDao.getPriceNum(pId);
                if(priceNum_JBL_BLUESPEEKER != 0) {
                    prizeLogBean.setPrice(24900);
                }
            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY &&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY){

                pId = Config.CPB_LIPSTICK;
                int priceNum_CPB_LIPSTICK = prizeDao.getPriceNum(pId);
                if(priceNum_CPB_LIPSTICK != 0) {
                    prizeLogBean.setPrice(33000);
                }
            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY &&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY){

                pId = Config.TF_LIPSTICK;
                int priceNum_TF_LIPSTICK = prizeDao.getPriceNum(pId);
                if(priceNum_TF_LIPSTICK != 0) {
                    prizeLogBean.setPrice(37500);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY&&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY){

                pId = Config.SK2_FACECLEANER;
                int priceNum_SK2_FACECLEANER = prizeDao.getPriceNum(pId);
                if(priceNum_SK2_FACECLEANER != 0) {
                    prizeLogBean.setPrice(46000);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY &&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY){

                pId = Config.TF_LIPSTICK;
                int priceNum_TIFFANY_DIAMOND = prizeDao.getPriceNum(pId);
                if(priceNum_TIFFANY_DIAMOND != 0) {
                    prizeLogBean.setPrice(59500);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY&&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY){

                pId = Config.DAIKE_AVOCADO;
                int priceNum_DAIKE_AVOCADO = prizeDao.getPriceNum(pId);
                if(priceNum_DAIKE_AVOCADO != 0) {
                    prizeLogBean.setPrice(60000);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY&&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY){

                pId = Config.APM_EARRINGS;
                int priceNum_APM_EARRINGS = prizeDao.getPriceNum(pId);
                if(priceNum_APM_EARRINGS != 0) {
                    prizeLogBean.setPrice(77000);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY  &&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY){

                pId = Config.NARS_LIPSTICK_BOX;
                int priceNum_NARS_LIPSTICK_BOX = prizeDao.getPriceNum(pId);
                if(priceNum_NARS_LIPSTICK_BOX != 0) {
                    prizeLogBean.setPrice(88000);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY+
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY &&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY +Config.BOSE_EARPHONE_PROBABILITY){

                pId = Config.BOSE_EARPHONE;
                int priceNum_BOSE_EARPHONE = prizeDao.getPriceNum(pId);
                if(priceNum_BOSE_EARPHONE != 0) {
                    prizeLogBean.setPrice(199800);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY +Config.BOSE_EARPHONE_PROBABILITY &&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY +Config.BOSE_EARPHONE_PROBABILITY + Config.SWITCH_GAMEBOX_PROBABILITY){

                pId = Config.SWITCH_GAMEBOX;
                int priceNum_SWITCH_GAMEBOX = prizeDao.getPriceNum(pId);
                if(priceNum_SWITCH_GAMEBOX != 0) {
                    prizeLogBean.setPrice(230000);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY +Config.BOSE_EARPHONE_PROBABILITY + Config.SWITCH_GAMEBOX_PROBABILITY &&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY +Config.BOSE_EARPHONE_PROBABILITY + Config.SWITCH_GAMEBOX_PROBABILITY +
                    Config.GOPRO_PROBABILITY){

                pId = Config.GOPRO;
                int priceNum_GOPRO = prizeDao.getPriceNum(pId);
                if(priceNum_GOPRO != 0) {
                    prizeLogBean.setPrice(347800);
                }

            }else if(randomNum >=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY +Config.BOSE_EARPHONE_PROBABILITY + Config.SWITCH_GAMEBOX_PROBABILITY +
                    Config.GOPRO_PROBABILITY&&
                    randomNum <=
                    Config.REDPACKET_0_66_PROBABILITY + Config.REDPACKET_0_88_PROBABILITY + Config.REDPACKET_1_66_PROBABILITY +
                    Config.REDPACKET_1_88_PROBABILITY + Config.REDPACKET_6_6_PROBABILITY + Config.REDPACKET_8_8_PROBABILITY +
                    Config.REDPACKET_16_6_PROBABILITY + Config.REDPACKET_18_8_PROBABILITY + Config.REDPACKET_36_6_PROBABILITY +
                    Config.WANGHONG_DUCK_PROBABILITY + Config.ZISHENGTANG_HANDRCREAM_PROBABILITY + Config.MARKTUBU_HUMIDIFIER_PROBABILITY +
                    Config.WANGHONG_BOLSTER_PROBABILITY + Config.LINEFRIENDS_TRASH_PROBABILITY + Config.HOLIKA_EYESSHADOW_PROBABILITY +
                    Config.STARBARKS_GIFTCARD_PROBABILITY + Config.SHANMOSHI_VACUUMCUP_PROBABILITY + Config.JBL_BLUESPEEKER_PROBABILITY +
                    Config.CPB_LIPSTICK_PROBABILITY + Config.TF_LIPSTICK_PROBABILITY + Config.SK2_FACECLEANER_PROBABILITY +
                    Config.TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY + Config.DAIKE_AVOCADO_PROBABILITY + Config.APM_EARRINGS_PROBABILITY +
                    Config.NARS_LIPSTICK_BOX_PROBABILITY +Config.BOSE_EARPHONE_PROBABILITY + Config.SWITCH_GAMEBOX_PROBABILITY +
                    Config.GOPRO_PROBABILITY + Config.SONY_M3_PROBABILITY){

                pId = Config.SONY_M3;
                int priceNum_SONY_M3 = prizeDao.getPriceNum(pId);
                if(priceNum_SONY_M3 != 0){
                    prizeLogBean.setPrice(379900);
                }
            }

        }
//        奖品总数减去1
        prizeDao.chashById(pId);

        prizeLogBean.setSrcId(uid);
        prizeLogBean.setPrizeId(pId);
        prizeLogBean.setInsertId(buyerid);
        prizeLogBean.setBuyerId(buyerid);
        prizeLogBean.setStatus(1);
        //保存用户信息
        prizeLogDao.save(prizeLogBean);


        if(prizeLogBean.getPrizeId() == 20 || prizeLogBean.getPrizeId() == 21 || prizeLogBean.getPrizeId() == 22 ||
         prizeLogBean.getPrizeId() == 23 || prizeLogBean.getPrizeId() == 24 || prizeLogBean.getPrizeId() == 25 ||
         prizeLogBean.getPrizeId() == 26 || prizeLogBean.getPrizeId() == 27 || prizeLogBean.getPrizeId() == 28
         || prizeLogBean.getPrizeId() == 29){
            Integer prizeId= 20;
            return prizeId;
        }else{
            return pId;
        }

    }






//    随机获取奖品
    public Integer getRandomPrize(){
        double random = Math.random();
        List<HashMap<String, Object>> list = prizeDao.percent();
        double ps=0;
        for (HashMap<String,Object> map:list){
            Integer id = MapUtils.getInteger(map,"id");
            double percent = MapUtils.getDoubleValue(map,"ps");
            if (random>ps && random<=(ps+percent)){
                return id;
            }else {
                ps += percent;
            }
        }
        return null;
    }

}
