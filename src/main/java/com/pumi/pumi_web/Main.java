package com.pumi.pumi_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static String imgDir ;
	
	public static void main(String[] args) {
		for(String arg : args){
			if(arg.trim().indexOf("--imgDir") == 0){
				imgDir = arg.trim().substring("--imgDir=".length());
				System.out.println(imgDir);
			}
		}
		SpringApplication.run(Main.class, args);
	}
}
