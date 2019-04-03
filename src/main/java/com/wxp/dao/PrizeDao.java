package com.wxp.dao;




import com.wxp.bean.PrizeBean;
import org.apache.ibatis.annotations.Param;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface PrizeDao  {

    PrizeBean findById(Integer id);

    List<HashMap<String, Object>> findAll();

//    获取奖品中奖概率
    List<HashMap<String, Object>> percent();

    void chashById(@Param("id") Integer id);

    List<PrizeBean> query(Map<String, Object> condition);

    void save(PrizeBean bean);

    void update(PrizeBean bean);

    void deletes(Integer[] ids);

    int getPriceNum(@Param("pId") Integer pId);

}