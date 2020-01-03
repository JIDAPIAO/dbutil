package com.example.dbutil;


import com.example.dbutil.syncdata.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({EnvConfig.class})
public class DbutilApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbutilApplication.class, args);
    }

}
