package com.crcc.test.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell on 2019/7/11.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/test")
    public String get(){
        return "spring security test";
    }
}
