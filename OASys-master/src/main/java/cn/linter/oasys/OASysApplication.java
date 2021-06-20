package cn.linter.oasys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@SpringBootApplication
public class OASysApplication {

    public static void main(String[] args) {

        SpringApplication
                .run(OASysApplication.class, args);
    }

}
