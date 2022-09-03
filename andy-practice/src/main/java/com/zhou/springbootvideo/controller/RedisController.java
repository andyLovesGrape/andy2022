package com.zhou.springbootvideo.controller;

import com.zhou.springbootvideo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhouyuanke
 * @date 2022/9/3 21:33
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/add")
    public void add(@RequestBody Student student) {
        redisTemplate.opsForValue().set("stu", student);
    }

    @GetMapping("/get")
    public Student add(String key) {
        return (Student) redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/delete")
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
