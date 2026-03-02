package com.example.demo.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component("processStartListener") // ⚠️ ชื่อต้องตรงกับใน BPMN
public class ProcessStartListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println(">>> Process Started: " + execution.getProcessInstanceId());
        System.out.println(">>> Process Definition Key: " + execution.getProcessDefinitionId());
        
        // ใส่ Logic ที่ต้องการรันตอน Process เริ่ม เช่น
        // - ส่ง Notification
        // - บันทึก Log
        // - ตั้งค่า Variables เริ่มต้น
    }
}