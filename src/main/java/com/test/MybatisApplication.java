package com.test;

import com.test.wechat.entity.WeixinTokenConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import sun.plugin2.main.server.AppletID;

@SpringBootApplication
@MapperScan("com.test.mapper")
@EnableScheduling
public class MybatisApplication {


    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}
