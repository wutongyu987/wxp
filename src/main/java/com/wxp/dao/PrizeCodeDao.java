package com.wxp.dao;


import com.wxp.bean.PrizeCodeBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrizeCodeDao {



    //    保存
    public void save(List<PrizeCodeBean> prizeCodeBeans);

//    更新二维码使用状态
    void use(@Param("uid") String  uid,@Param("buyerId") Integer buyerId);
//   根据uid查询二维码信息
    PrizeCodeBean getByUid(@Param("uid") String  uid);


    PrizeCodeBean get();

    void inseert(@Param("i") int i);

    String getRedPackId(@Param("uid") String uid);
}