package com.javamentor.qa.platform.webapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/regPage").setViewName("regPage");
        registry.addViewController("/loginPage").setViewName("loginPage");
        registry.addViewController("/profile").setViewName("profilePage");
        registry.addViewController("/question").setViewName("question");
        registry.addViewController("/usersPage").setViewName("usersPage");
    }
}
