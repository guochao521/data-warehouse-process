package org.mallmark.litemall.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"org.mallmark.litemall.db", "org.mallmark.litemall.core",
        "org.mallmark.litemall.admin"})
@MapperScan("org.mallmark.litemall.db.dao")
@EnableTransactionManagement
@EnableScheduling
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}