package io.topjob.topjobapplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class TopJobServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TopJobServiceApplication.class, args);

        String mongoDb = ctx.getEnvironment().getProperty("spring.data.mongodb.database");
        log.info("Connected to MongoDb Database: " + mongoDb);
    }
}
