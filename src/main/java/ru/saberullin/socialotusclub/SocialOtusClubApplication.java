package ru.saberullin.socialotusclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SocialOtusClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialOtusClubApplication.class, args);
    }

}
