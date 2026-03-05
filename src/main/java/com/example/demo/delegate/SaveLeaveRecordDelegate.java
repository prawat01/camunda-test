package com.example.demo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("saveLeaveRecordDelegate")
public class SaveLeaveRecordDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println(">>> 📝 Saving leave record...");
        
        String employee = (String) execution.getVariable("employee");
        String startDate = (String) execution.getVariable("startDate");
        String endDate = (String) execution.getVariable("endDate");
        String reason = (String) execution.getVariable("reason");
        
        System.out.println(">>> Employee: " + employee);
        System.out.println(">>> Period: " + startDate + " to " + endDate);
        System.out.println(">>> Reason: " + reason);
        
        // TODO: เพิ่มโค้ดบันทึกข้อมูลลง Database ที่นี่
        // leaveRepository.save(new LeaveRecord(...));
        
        execution.setVariable("recordSaved", true);
        System.out.println(">>> ✅ Leave record saved successfully");
    }
}