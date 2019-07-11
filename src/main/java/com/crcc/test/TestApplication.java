package com.crcc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by dell on 2019/7/11.
 */
@SpringBootApplication
public class TestApplication{

    char aChar = 'b';
    boolean aBoolean = false;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
