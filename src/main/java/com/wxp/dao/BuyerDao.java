package com.wxp.dao;


import com.wxp.bean.BuyerBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BuyerDao  {

    BuyerBean findById(@Param("id") Integer id);

    List<BuyerBean> findAll();

    List<BuyerBean> query(Map<String, Object> condition);

    void save(BuyerBean bean);

    void update(BuyerBean bean);

    void deletes(Integer[] ids);

    BuyerBean findByOpenId(String openId);

    void reducePoint(@Param("totalFee") Integer totalFee, @Param("id") Integer insertId);

    void addPoint(@Param("totalFee") Integer totalFee, @Param("id") Integer id);

    void zan(Integer buyerId);
}