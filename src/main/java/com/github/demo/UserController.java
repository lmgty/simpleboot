package com.github.demo;

import annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuYe
 * @data 2020/11/20
 */

@RestController("/user")
public class UserController {

    private static List<User> users = new ArrayList<>();

    @GetMapping
    public User get(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return new User(name, "EMM", age);
    }

    @PostMapping
    public List<User> create(@RequestBody UserDto userDto){
        users.add(new User(userDto.getName(),userDto.getDes(),userDto.getAge()));
        return users;
    }

}
