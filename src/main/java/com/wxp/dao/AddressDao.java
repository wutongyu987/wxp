package com.wxp.dao;

import com.wxp.bean.AddressBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface AddressDao {

    AddressBean findById(@Param("id") Integer id);

    List<AddressBean> findAll();

    List<AddressBean> query(Map<String, Object> condition);

    void save(AddressBean bean);

    void update(AddressBean bean);

    void deletes(@Param("ids")Integer[] ids,@Param("buyerId") Integer buyerId);

    void deleteDef(@Param("buyerId") Integer buyerId);

    AddressBean findByBuyId(Integer buyerId);

    Integer getUserAdressDef(Integer id);

    void deleteAddressByid(@Param("addressId") Integer addressId,@Param("buyerId") Integer buyerId);

    List<AddressBean> getUserAdress(Integer buyerId);

}