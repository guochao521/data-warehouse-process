package org.simulate.dw.schedule;

import org.simulate.dw.mock.BusinessService;
import org.simulate.dw.mock.FrontAppActionService;
import org.simulate.dw.mock.UserActionDataMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.simulate.dw.service.CommonDataService;

/**
 *
 */
@Component
public class DataSimulate {

    @Autowired
    private UserActionDataMock userActionDataMock;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private FrontAppActionService frontAppActionService;

    /**
     * 用户启动以及用户行为数据模拟生成
     * 生成用户行为操作日志，将日志通过请求发送到nginx里面去了
     */
    @Scheduled(cron = "${simulate.schedule.action}")
    public void action() {
       userActionDataMock.process();
    }

    @Value("${simulate.front-app.enable}")
    private boolean frondEnable;

    @Value("${simulate.offline-enable}")
    private boolean offLineEnable;

    /**
     *
     */
    @Scheduled(cron = "${simulate.schedule.business}")
    public void business() {
        if (offLineEnable) {
            businessService.process();
        }else {
            businessService.processOnlyShip();
        }
    }

    /**
     * 模拟前端请求
     */
    @Scheduled(cron = "${simulate.schedule.frond}")
    public void appAction() {
        if (frondEnable) {
            this.frontAppActionService.process();
        }
    }

    @Value("${simulate.schedule.reload.enable}")
    private boolean reload;

    @Scheduled(cron = "${simulate.schedule.reload.cron}")
    public void reload() {
        if (reload) {
            this.commonDataService.reloadCommonData();
        }
    }

}
