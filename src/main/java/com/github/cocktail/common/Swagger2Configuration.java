package com.github.cocktail.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Liuhao
 * Description:Swagger2 Api configuration
 * Date:16:59 2020/5/22
 */
@Component
@EnableSwagger2
public class Swagger2Configuration {

    @Value("${swagger.document.title:Swagger2 restful Api For PEANUT}")
    private String title;

    @Value("${swagger.document.description:Swagger2 restful Api Of Cocktail Service}")
    private String description;

    @Value("${swagger.document.terms.service.url:https://swagger.io/}")
    private String termsOfServiceUrl;

    @Value("${swagger.document.contact.name:Swagger2 Developer Named peanut_Liu}")
    private String name;

    @Value("${swagger.document.contact.url:https://swagger.io/}")
    private String url;

    @Value("${swagger.document.contact.mail:peanut@0713.com}")
    private String mail;

    @Value("${swagger.document.version:0.0.1}")
    private String version;

    @Value("${swagger.document.base.package:io.springfox}")
    private String basePackage;

    @Value("${swagger.enable:true}")
    private boolean enable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(this.enable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(name, url, mail))
                .version(version)
                .build();
    }

}