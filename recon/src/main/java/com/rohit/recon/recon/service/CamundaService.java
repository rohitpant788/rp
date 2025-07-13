package com.rohit.recon.recon.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CamundaService {

    @Autowired
    private RuntimeService runtimeService;

    public String startWorkflow(String caseId) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(
                "reconCaseProcess", // BPMN ID
                Map.of("caseId", caseId)
        );
        return instance.getProcessInstanceId();
    }
}

