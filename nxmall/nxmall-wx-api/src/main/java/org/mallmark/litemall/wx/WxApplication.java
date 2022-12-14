package org.mallmark.litemall.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"org.mallmark.litemall.db", "org.mallmark.litemall.core", "org.mallmark.litemall.wx"})
@MapperScan("org.mallmark.litemall.db.dao")
@EnableTransactionManagement
@EnableScheduling
public class WxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class, args);
    }

}