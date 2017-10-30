package com.pumi.pumi_web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pumi.pumi_web.Main;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
    	
    	if(!Main.imgDir.endsWith("/") && !Main.imgDir.endsWith("\\")){
    		Main.imgDir += "/"; 
    	}
    	System.out.println("file:"+Main.imgDir);
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+Main.imgDir);
        super.addResourceHandlers(registry);
    }
}
