package com.github.simpleboot;

import com.github.simpleboot.core.ApplicationContext;
import com.github.simpleboot.server.HttpServer;

/**
 * @author LiuYe
 * @data 2020/11/17
 */
public class SimpleBootApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext =  ApplicationContext.getInstance();
        applicationContext.loadRoutes("com.github.demo");
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
