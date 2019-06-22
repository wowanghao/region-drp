package io.terminus.common.data.transfer.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Slf4j
@SpringBootApplication
public class DemoCoreBootStrapA {

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(DemoCoreBootStrapA.class).web(false).run(args);
        new CountDownLatch(1).await();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> log.info("DemoCoreBootStrapA started");
    }
}

