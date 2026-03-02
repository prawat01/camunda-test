package com.example.demo.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LeaveRequestDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/process")
@CrossOrigin(origins = "*") // อนุญาตให้เรียกจาก Browser ได้
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    /**
     * Start Leave Request Process
     */
    @PostMapping("/start-leave-request")
    public Map<String, Object> startLeaveRequest() {
        
        // 1. สร้าง Variables
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "john.doe");
        variables.put("employeeName", "John Doe");
        variables.put("employeeEmail", "john@company.com");
        variables.put("manager", "jane.smith");
        variables.put("startDate", "2026-03-01");
        variables.put("endDate", "2026-03-05");
        variables.put("reason", "Personal matter");

        // 2. Start Process Instance
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
            "leaveRequestProcess", 
            variables
        );

        // 3. สร้าง Response
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Process started successfully");
        response.put("processDefinitionId", processInstance.getProcessDefinitionId());
        response.put("processInstanceId", processInstance.getId());
        response.put("businessKey", processInstance.getBusinessKey());

        return response;
    }

    /**
     * Start Process with Custom Variables
     */
    @PostMapping("/start-leave-request-custom")
    public Map<String, Object> startLeaveRequestCustom(
            @RequestBody Map<String, Object> customVariables) {
        
        // Start Process with custom variables
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
            "leaveRequestProcess", 
            customVariables
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Process started successfully");
        response.put("processInstanceId", processInstance.getId());
        response.put("businessKey", processInstance.getBusinessKey());

        return response;
    }

    /**
     * Get Process Instance by ID
     */
    @GetMapping("/instance/{id}")
    public Map<String, Object> getProcessInstance(@PathVariable String id) {
        
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
            .processInstanceId(id)
            .singleResult();

        Map<String, Object> response = new HashMap<>();
        if (instance != null) {
            response.put("found", true);
            response.put("processInstanceId", instance.getId());
            response.put("processDefinitionId", instance.getProcessDefinitionId());
            response.put("businessKey", instance.getBusinessKey());
            List<String> activeActivityIds = runtimeService.getActiveActivityIds(id);
            response.put("activeActivityIds", activeActivityIds);
        } else {
            response.put("found", false);
            response.put("message", "Process instance not found");
        }

        return response;
    }

    @PostMapping("/start-leave-request-dto")
    public Map<String, Object> startLeaveRequestDTO(@RequestBody LeaveRequestDTO request) {
    
    Map<String, Object> variables = new HashMap<>();
    variables.put("employee", request.getEmployee());
    variables.put("employeeName", request.getEmployeeName());
    variables.put("employeeEmail", request.getEmployeeEmail());
    variables.put("manager", request.getManager());
    variables.put("startDate", request.getStartDate());
    variables.put("endDate", request.getEndDate());
    variables.put("reason", request.getReason());

    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
        "leaveRequestProcess", 
        variables
    );

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Process started successfully");
    response.put("processInstanceId", processInstance.getId());

    return response;
}
}