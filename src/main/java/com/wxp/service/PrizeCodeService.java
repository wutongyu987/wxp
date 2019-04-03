package com.wxp.service;

import com.wxp.Config;
import com.wxp.bean.PrizeCodeBean;
import com.wxp.common.util.QRCodeUtils;
import com.wxp.dao.PrizeCodeDao;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * create by ff on 2018/12/2
 */
@Service
public class PrizeCodeService {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Resource
    private PrizeCodeDao prizeCodeDao;



    //    保存
    public void save(int num){
        List<PrizeCodeBean> list = new ArrayList<>();
        int id = (int) (System.currentTimeMillis()/1000);
        for (int i=0;i<num;i++){
            PrizeCodeBean prizeCodeBean = new PrizeCodeBean();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            uuid = new SimpleHash("md5",uuid).toHex();
            System.out.println(uuid);
            prizeCodeBean.setImg("https://www.xingyuanji.com/wxp/towx/");
            prizeCodeBean.setUid(uuid);
            prizeCodeBean.setStatus(0);
            list.add(prizeCodeBean);
            id=id+i;
            createQrImg(uuid,id);
            prizeCodeBean.setId(id);
            if (i>9){
                prizeCodeDao.save(list);
                list.clear();
                i=0;
                num=num-10;
            }
        }
        if (list.size()>0) {
            prizeCodeDao.save(list);
        }
    }


//    创建二维码
    private void createQrImg(String uid,Integer id){
        String url = Config.SERVER+"wxp/towx/"+uid;
        String savePath = Config.UPLOAD_DIR+id+".png";
        QRCodeUtils.createQRCode(url,savePath);
    }


    public PrizeCodeBean get() {
        return prizeCodeDao.get();
    }

    public void insertttt() {
        for (int i = 1547779846;i<= 1550804891; i++){
        prizeCodeDao.inseert(i);
        System.out.println(i);
        }
    }
}
