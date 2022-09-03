package com.zhou.springbootvideo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouyuanke
 * @date 2022/9/3 21:41
 */
@Data
public class Student implements Serializable {
    private int id;
    private String name;
    private int age;
}
