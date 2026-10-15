package com.paradise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后端可运行应用入口（聚合模块 + 全局配置）。
 *
 * <p>仅为结构占位：@SpringBootApplication 扫描 com.paradise 下所有模块组件。
 * 业务配置（WebMvc / MyBatis-Plus / Redis / OpenAPI / Async / Security 过滤器链）在 com.paradise.config 包中补充。</p>
 */
@SpringBootApplication(scanBasePackages = "com.paradise")
public class ParadiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParadiseApplication.class, args);
    }
}
