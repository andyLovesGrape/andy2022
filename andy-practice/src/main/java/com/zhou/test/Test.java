package com.zhou.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zhou.test.test1.Student;

/**
 * @author zhouyuanke
 * @date 2022/8/28 19:12
 */
public class Test {
    public static void main(String[] args) {
        Student student = Student.builder().name("zhou").age(18).build();
        String str = JSON.toJSONString(student);
        JSONObject jsonObject = JSON.parseObject(str);
    }
}
