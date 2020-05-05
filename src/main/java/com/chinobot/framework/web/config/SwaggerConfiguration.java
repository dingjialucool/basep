package com.chinobot.framework.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * api文档服务
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration{

    @Autowired
    private ServerConfig serverConfig;


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chinobot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder ()
                .title("人工智能+无人机混合采查预警平台 API文档")
                .description("人工智能+无人机混合采查预警平台 API文档")
                .termsOfServiceUrl(serverConfig.getUrl())
                .contact("www.chinobot.com")
                .version("1.0.0")
                .build();
    }
}
