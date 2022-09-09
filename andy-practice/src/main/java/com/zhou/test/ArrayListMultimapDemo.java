package com.zhou.test;

import com.google.common.collect.ArrayListMultimap;
import org.junit.Assert;

/**
 * @author zhouyuanke
 * @date 2022/9/8 23:48
 */
public class ArrayListMultimapDemo {
    public static void main(String[] args) {
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("zhou", "value1");
        multimap.put("zhou", "value2");
        Assert.assertEquals(multimap.get("zhou").size(), 2);
    }
}
