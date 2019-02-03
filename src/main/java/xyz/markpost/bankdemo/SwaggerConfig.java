package xyz.markpost.bankdemo;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The configuration to add Swagger to the application
 */
@Profile("!pr")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket bankDemoApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
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