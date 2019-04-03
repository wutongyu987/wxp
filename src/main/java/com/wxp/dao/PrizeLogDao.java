package com.wxp.dao;


import com.wxp.bean.PrizeBean;
import com.wxp.bean.PrizeLogBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PrizeLogDao {

    PrizeLogBean findById(@Param("id") Integer id);

    List<Map<String,Object>> findByBuyerId(@Param("id") Integer id);

    List<PrizeLogBean> findAll();

    List<Map<String,Object>> query(Map<String, Object> condition);

    void save(PrizeLogBean bean);
    //    兑奖，只能是未兑换的才可以兑奖
    void cachePrize(PrizeLogBean prizeLogBean);

    void insertUseNum(@Param("uid") String uid,@Param("buyerid") Integer buyerid,@Param("address") String address);

    void deletePrizeLog(@Param("logId") Integer logId,@Param("buyerId") Integer buyerId);

    String getRedPackId(@Param("uid") String uid);

}