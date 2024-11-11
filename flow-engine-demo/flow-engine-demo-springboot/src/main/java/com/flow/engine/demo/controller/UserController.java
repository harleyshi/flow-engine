package com.flow.engine.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


/**
 * @author harley.shi
 * @date 2024/11/4
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public String create(@RequestBody Map<String, Object> params) {
        return "";
    }
}
