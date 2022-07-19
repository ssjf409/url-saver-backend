package com.jdh.urlsaver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Profile({ "local", "develop" })
@EnableWebMvc
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

//    @Bean
//    public Docket ShopApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("URL saver API")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.jdh.urlsaver"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(this.ShopApiInfo())
//                .tags(new Tag("AuthController", "Auth API"),
//                      new Tag("MemberController", "Member API")
//                );
//
//    }
//
//    private ApiInfo ShopApiInfo() {
//        return new ApiInfoBuilder()
//                .title("shop API")
//                .description("shop API")
//                .termsOfServiceUrl("http://www.shop-api.com")
//                .version("1.0")
//                .build();
//    }
}
