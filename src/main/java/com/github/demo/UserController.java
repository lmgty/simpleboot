package com.github.demo;

import annotation.GetMapping;
import annotation.RestController;

/**
 * @author LiuYe
 * @data 2020/11/20
 */

@RestController("/user")
public class UserController {

    @GetMapping
    public User get(){
        return new User("Tom","EMM");
    }

}
