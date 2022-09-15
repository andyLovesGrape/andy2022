package com.zhou.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author zhouyuanke
 * @date 2022/9/14 21:48
 */
@Component
@DependsOn(value = "myInitializingBean")
public class DependsOnAnnotationTest {

    public DependsOnAnnotationTest() {
        System.out.println("DependsOnAnnotationTest");
    }
}
