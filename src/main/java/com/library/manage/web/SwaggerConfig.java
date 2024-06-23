package com.library.manage.web;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@Configuration
public class SwaggerConfig {
 
    /**
     * 访问官方文档 UI界面：http://127.0.0.1:8080/swagger-ui.html
     */
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("文档标题")
                        .description("文档描述")
                        .contact(
                                new Contact()
                                        .name("fzb")
                                  //      .email("邮箱")
                                  //      .url("博客地址")
                        )
                        .version("v1.0"));

    }

}