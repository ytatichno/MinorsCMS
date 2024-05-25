package org.prac327.minorscms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MinorsCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinorsCmsApplication.class, args);
    }

}
