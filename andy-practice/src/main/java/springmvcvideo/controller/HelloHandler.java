package springmvcvideo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springmvcvideo.entity.User;

/**
 * @author zhouyuanke
 * @date 2022/8/29 22:14
 * 首先启动tomcat才可以在本地页面访问
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
    public String rest(@PathVariable("name") String name, @PathVariable("age") int age) {
        // rest 风格
        System.out.println(name);
        System.out.println(age);
        return "index";
    }

    @RequestMapping("/cookie")
    public String cookie(@CookieValue(value = "JSESSIONID") String sessionId) {
        System.out.println(sessionId);
        return "index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(User user) {
        System.out.println(user.toString());
        return "index";
    }
}
