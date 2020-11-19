package com.github.simpleboot;

import com.github.simpleboot.server.HttpServer;

/**
 * @author LiuYe
 * @data 2020/11/17
 */
public class SimpleBootApplication {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
