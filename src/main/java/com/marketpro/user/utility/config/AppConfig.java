package com.marketpro.user.utility.config;


import com.marketpro.user.security.filter.ACustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

 @Bean
 public FilterRegistrationBean<ACustomFilter> filterRegistrationBean() {
  FilterRegistrationBean<ACustomFilter> registrationBean = new FilterRegistrationBean<ACustomFilter>();
  ACustomFilter customURLFilter = new ACustomFilter();

  registrationBean.setFilter(customURLFilter);
  registrationBean.addUrlPatterns("/students/*");
  registrationBean.setOrder(2); //set precedence
  return registrationBean;
 }
}