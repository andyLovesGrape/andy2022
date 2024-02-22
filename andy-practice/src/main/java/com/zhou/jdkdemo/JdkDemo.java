package com.zhou.jdkdemo;

import org.springframework.stereotype.Component;

/**
 * @createTime: 2024-01-05 08:44
 * @description: jdkDemo类
 */
@Component
public class JdkDemo {

    private DependentDemo dependentDemo;

    public JdkDemo(DependentDemo dependentDemo) {
        this.dependentDemo = dependentDemo;
    }
}
