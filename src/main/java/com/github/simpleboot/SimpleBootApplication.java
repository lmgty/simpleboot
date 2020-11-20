package com.github.simpleboot;

import com.github.simpleboot.core.Router;
import com.github.simpleboot.server.HttpServer;

/**
 * @author LiuYe
 * @data 2020/11/17
 */
public class SimpleBootApplication {
    public static void main(String[] args) {
        Router router = new Router();
        router.loadRoutes("com.github.demo");
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
