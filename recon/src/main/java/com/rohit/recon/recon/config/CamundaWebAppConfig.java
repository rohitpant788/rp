/*
package com.rohit.recon.recon.config;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class CamundaWebAppConfig {

    @Bean
    public ServletRegistrationBean<?> camundaCockpit() {
        ServletRegistrationBean<GenericServlet> registration = new ServletRegistrationBean<>(
                new GenericServlet() {
                    @Override
                    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
                        req.getRequestDispatcher("/app/cockpit/index.html").forward(req, res);
                    }
                }, "/app/cockpit/*"
        );
        registration.setName("Camunda Cockpit");
        return registration;
    }

    @Bean
    public ServletRegistrationBean<?> camundaTasklist() {
        ServletRegistrationBean<GenericServlet> registration = new ServletRegistrationBean<>(
                new GenericServlet() {
                    @Override
                    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
                        req.getRequestDispatcher("/app/tasklist/index.html").forward(req, res);
                    }
                }, "/app/tasklist/*"
        );
        registration.setName("Camunda Tasklist");
        return registration;
    }

    @Bean
    public ServletRegistrationBean<?> camundaAdmin() {
        ServletRegistrationBean<GenericServlet> registration = new ServletRegistrationBean<>(
                new GenericServlet() {
                    @Override
                    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
                        req.getRequestDispatcher("/app/admin/index.html").forward(req, res);
                    }
                }, "/app/admin/*"
        );
        registration.setName("Camunda Admin");
        return registration;
    }
}*/
