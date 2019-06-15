package com.jiajiayue.all.regiondrp;

import io.jjy.platform.common.datasource.EnableDynamicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDynamicDataSource
@SpringBootApplication
public class RegionDrpApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegionDrpApplication.class, args);
    }

}
