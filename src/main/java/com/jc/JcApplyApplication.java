package com.jc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = {"com.jc.mapper", "com.jc.security.mapper"})
@EnableAspectJAutoProxy
public class JcApplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JcApplyApplication.class, args);
    }
}
