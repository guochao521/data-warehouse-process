package org.simulate.dw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.simulate.dw.config.SimulateProperty;
import org.simulate.dw.mock.UserActionDataMock;
import org.simulate.dw.service.UserInfoService;


@EnableScheduling
@EnableTransactionManagement
@MapperScan("org.mallmark.litemall.db.dao")
@EnableConfigurationProperties({SimulateProperty.class})
@SpringBootApplication(scanBasePackages = {"org.mallmark.litemall.db", "org.simulate.dw"})
public class SimulateApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimulateApplication.class, args);
    }

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserActionDataMock userActionDataMock;

    @Override
    public void run(String... args) throws Exception {
        this.userInfoService.addObserver(userActionDataMock);
    }
}
