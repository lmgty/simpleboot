package com.github.demo;

import annotation.GetMapping;
import annotation.RequestParam;
import annotation.RestController;

/**
 * @author LiuYe
 * @data 2020/11/20
 */

@RestController("/user")
public class UserController {

    @GetMapping
    public User get(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return new User(name, "EMM", age);
    }

}
