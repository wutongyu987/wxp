package com.wxp.common.Init;

import com.wxp.service.WxMsgService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    private WxMsgService wxMsgService;
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("项目启动完成");
        wxMsgService.start();
    }
}
