package com.github.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LiuYe
 * @data 2020/11/20
 */
@Data
@AllArgsConstructor
public class User {
    private String name;
    private String des;
    private Integer age;
}
