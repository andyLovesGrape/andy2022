package springmvcvideo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhouyuanke
 * @date 2022/8/30 09:42
 */
@Controller
@RequestMapping("/data")
public class DataBindHandler {

    @RequestMapping("/type")
    @ResponseBody
    public String data(String name) {
        return name;
    }
}