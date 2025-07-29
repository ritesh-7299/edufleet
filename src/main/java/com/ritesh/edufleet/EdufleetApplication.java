package com.ritesh.edufleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EdufleetApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdufleetApplication.class, args);
    }

}
