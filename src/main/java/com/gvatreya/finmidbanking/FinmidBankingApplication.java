package com.gvatreya.finmidbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.gvatreya.finmidbanking")
public class FinmidBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinmidBankingApplication.class, args);
    }

}
