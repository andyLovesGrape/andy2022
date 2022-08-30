package springmvcvideo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springmvcvideo.entity.User;
import springmvcvideo.entity.UserList;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author zhouyuanke
 * @date 2022/8/30 09:42
 * 如果这里使用 @Controller 那么下面的各个方法都需要添加 @ResponseBody。不添加是返回视图，添加是直接返回值
 */
@RestController
@RequestMapping("/data")
public class DataBindHandler {

    @RequestMapping("/type")
    public String data(@RequestParam(value = "num", required = false, defaultValue = "888") String id) {
        return "id is " + id;
    }

    @RequestMapping("/array")
    public String array(String[] name) {
        String str = Arrays.toString(name);
        return str;
    }

    @RequestMapping("/list")
    public String list(UserList userList) {
        return userList.getUsers().stream().map(User::getName).collect(Collectors.joining(","));
    }
}