package kz.edev.comment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "kz.edev.comment")
@PropertySource("application.properties")
@EnableJpaRepositories(basePackages = "kz.edev.comment.repository")
public class Config {}
