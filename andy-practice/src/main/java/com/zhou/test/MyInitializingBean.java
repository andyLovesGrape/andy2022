package com.zhou.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author zhouyuanke
 * @date 2022/9/8 23:13
 */
@Component
public class MyInitializingBean implements InitializingBean {

    public MyInitializingBean() {
        System.out.println("MyInitializingBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }
}