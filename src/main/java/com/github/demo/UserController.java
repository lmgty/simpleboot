package com.github.demo;

import com.github.simpleboot.annotation.*;

import java.util.*;

/**
 * @author LiuYe
 * @data 2020/11/20
 */

@RestController("/user")
public class UserController {

    private static Map<Integer, User> users;
    private static Integer id;

    {
        users = new HashMap<>();
        users.put(1, new User("Lue", "SH", 22));
        id = 2;
    }

    @GetMapping
    public User get(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return new User(name, "EMM", age);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Integer id) {

        return users.get(id);
    }

    @PostMapping
    public List<User> create(@RequestBody UserDto userDto) {
        users.put(id, new User(userDto.getName(), userDto.getDes(), userDto.getAge()));
        return new ArrayList<>(users.values());
    }

}
