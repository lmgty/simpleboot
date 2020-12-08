package com.github.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiuYe
 * @data 2020/12/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String des;
    private Integer age;
}
