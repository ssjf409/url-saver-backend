package com.jdh.urlsaver;

import com.jdh.urlsaver.config.properties.AppProperties;
import com.jdh.urlsaver.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties({
        CorsProperties.class,
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
