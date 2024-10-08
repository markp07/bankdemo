package xyz.markpost.bankdemo.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * The configuration to add Swagger to the application
 */
@Configuration
@PropertySource("classpath:swagger.properties")
@Profile("!pr")
public class SwaggerConfig {

  private static final String BASE_PACKAGE = "xyz.markpost.bankdemo.controller";

  @Bean
  public Docket bankDemoApi() {
    return new Docket(DocumentationType.OAS_30)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Bank Demo API",
        "A demo API for a bank application",
        "1.0",
        "Terms of service",
        new Contact("Mark Post",
            "https://www.markpost.xyz",
            "mark@markpost.xyz"),
        "MIT License",
        "https://opensource.org/licenses/MIT",
        new ArrayList<>());
  }
}