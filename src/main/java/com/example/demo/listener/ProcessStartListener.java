package com.example.demo.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("processStartListener") // ⚠️ ชื่อต้องตรงกับใน BPMN
public class ProcessStartListener implements ExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ProcessStartListener.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        logger.info(">>> Process Started: {}", execution.getProcessInstanceId());
        logger.info(">>> Process Definition Id: {}", execution.getProcessDefinitionId());
        
        // ใส่ Logic ที่ต้องการรันตอน Process เริ่ม เช่น
        // - ส่ง Notification
        // - บันทึก Log
        // - ตั้งค่า Variables เริ่มต้น
    }
}