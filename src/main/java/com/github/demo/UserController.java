package com.github.demo;

import com.github.simpleboot.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LiuYe
 * @data 2020/11/20
 */

@RestController("/user")
public class UserController {

    private static List<User> users = new ArrayList<>(Collections.singletonList(new User("盖伦", "德玛西亚", 18)));

    @GetMapping
    public User get(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return new User(name, "EMM", age);
    }

    @GetMapping("/{name}")
    public User get(@PathVariable("name") String name){
        System.out.println(name);
        return users.get(0);
    }

    @PostMapping
    public List<User> create(@RequestBody UserDto userDto){
        users.add(new User(userDto.getName(),userDto.getDes(),userDto.getAge()));
        return users;
    }

}
