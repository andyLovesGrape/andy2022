package springmvcvideo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhouyuanke
 * @date 2022/8/29 22:14
 */
@Controller
@RequestMapping("/hello")
public class HelloHandler {

    @RequestMapping("/index")
    public String index() {
        // 传统风格
        System.out.println("index");
        return "index";
    }

    @RequestMapping("/rest/{name}/{age}")
    public String rest(@PathVariable("name") String name, @PathVariable("age") Integer age) {
        // rest 风格
        System.out.println(name);
        System.out.println(age);
        return "index";
    }
}
