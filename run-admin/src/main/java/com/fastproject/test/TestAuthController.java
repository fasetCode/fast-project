package com.fastproject.test;

import com.fastproject.utils.utils.JsonUtils;
import com.fastproject.utils.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class TestAuthController {

    @PostMapping("/ws/auth")
    public ResultVo<Object> auth(@RequestBody Map<String, Object> body) {
        return ResultVo.success("auth");
    }

    @PostMapping("/chat/auth")
    public ResultVo<Object> chatAuth(@RequestBody Map<String, Object> body) {
        return ResultVo.success("tokentest");
    }

    /**
     * 获取token
     */
    @PostMapping("/ws/getToken")
    public ResultVo<Object> token(@RequestBody Map<String, Object> body) {
        return ResultVo.success("tokentest");
    }

}
