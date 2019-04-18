package com.wxp.control;

import com.wxp.common.util.JsonUtil;
import com.wxp.dao.PrizeCodeDao;
import com.wxp.service.PrizeCodeService;
import com.wxp.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * create by ff on 2018/12/3
 */

@Controller
@ResponseBody
@RequestMapping("/prizeCode")
public class PrizeCodeApi {

    @Resource
    private PrizeCodeService prizeCodeService;

    @Resource
    private WxPayService wxPayService;

    @RequestMapping("/save")
    public Object save(){
        prizeCodeService.save(100000);
        return JsonUtil.genSuccess();
    }

    @RequestMapping("/send")
    public Object send(HttpServletRequest request) throws Exception {
        return wxPayService.sendRedPackage("123455","omkkb1OizMbpSHgw0-cZxdk2apk8","100",request);
    }

    @RequestMapping("/get")
    public Object get() throws Exception {
        return prizeCodeService.get();
    }


}
