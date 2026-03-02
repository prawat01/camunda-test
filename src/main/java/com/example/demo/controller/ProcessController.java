package com.example.demo.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LeaveRequestDTO;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/process")
@CrossOrigin(origins = "*") // อนุญาตให้เรียกจาก Browser ได้
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

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

    @PostMapping("/test-rejection-scenario")
    public Map<String, Object> testRejectionScenario() {
        
        System.out.println(">>> Testing Rejection Scenario");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 1. Start Process
            Map<String, Object> startVariables = new HashMap<>();
            startVariables.put("employee", "test.user");
            startVariables.put("employeeName", "Test User");
            startVariables.put("employeeEmail", "test@company.com");
            startVariables.put("manager", "admin");
            startVariables.put("startDate", "2026-03-10");
            startVariables.put("endDate", "2026-03-12");
            startVariables.put("reason", "ทดสอบการปฏิเสธ");
            
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                "leaveRequestProcess", 
                startVariables
            );
            
            String processInstanceId = processInstance.getId();
            System.out.println(">>> Process Started: " + processInstanceId);
            
            // 2. Complete Task แรก (ยื่นคำขอลา)
            Task submitTask = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("submitRequestTask")
                .singleResult();
            
            if (submitTask != null) {
                Map<String, Object> submitVars = new HashMap<>();
                submitVars.put("startDate", "2026-03-10");
                submitVars.put("endDate", "2026-03-12");
                submitVars.put("reason", "ทดสอบการปฏิเสธ");
                
                taskService.complete(submitTask.getId(), submitVars);
                System.out.println(">>> Task 1 Completed");
            }
            
            // 3. Complete Task ที่สอง (อนุมัติ) - เลือก ปฏิเสธ
            Task approveTask = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("approveTask")
                .singleResult();
            
            if (approveTask != null) {
                Map<String, Object> approveVars = new HashMap<>();
                approveVars.put("approved", false);  // ← สำคัญ! เลือกปฏิเสธ
                approveVars.put("managerComment", "งานสำคัญ ต้องอยู่ทำโปรเจกต์");
                
                taskService.complete(approveTask.getId(), approveVars);
                System.out.println(">>> Task 2 Completed (REJECTED)");
            }
            
            // 4. Response
            response.put("success", true);
            response.put("message", "Rejection scenario completed");
            response.put("processInstanceId", processInstanceId);
            response.put("result", "REJECTED");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
}