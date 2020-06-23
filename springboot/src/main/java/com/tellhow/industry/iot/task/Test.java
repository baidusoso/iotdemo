package com.tellhow.industry.iot.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Test {

    @Scheduled(cron="0 */1 * * * ?")
    public void test(){
        System.out.println("test");
    }
}
