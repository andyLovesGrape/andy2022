package com.zhou.common;

import lombok.Data;

import java.util.Map;

/**
 * @author zhouyuanke
 * @date 2022/10/25 15:33
 */
@Data
public class TestEntity {
    private int code;
    private Map<Integer, String> map;
}
