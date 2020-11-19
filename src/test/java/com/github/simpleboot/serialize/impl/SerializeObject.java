package com.github.simpleboot.serialize.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LiuYe
 * @data 2020/11/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerializeObject {
    private String name;
    private String anotherName;
}
