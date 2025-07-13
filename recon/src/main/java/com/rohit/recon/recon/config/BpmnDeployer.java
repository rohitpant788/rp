package com.rohit.recon.recon.config;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BpmnDeployer implements CommandLineRunner {

    private final RepositoryService repositoryService;

    public BpmnDeployer(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public void run(String... args) {
        Deployment deployment = repositoryService.createDeployment()
                .name("recon-case-deployment")
                .addClasspathResource("processes/recon-case-process.bpmn")
                .deploy();

        System.out.println("✅ BPMN deployed with ID: " + deployment.getId());
    }
}
