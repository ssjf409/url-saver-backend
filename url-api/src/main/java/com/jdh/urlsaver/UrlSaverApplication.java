package com.jdh.urlsaver;

import com.jdh.urlsaver.configuration.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@EnableConfigurationProperties({
//        CorsProperties.class,
        AppProperties.class
})
@SpringBootApplication(
        scanBasePackageClasses = { BasePackage.class }
)
public class UrlSaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlSaverApplication.class, args);
    }
}
