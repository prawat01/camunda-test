package com.example.demo.delegate; // 1. ต้องแก้ Package ให้ตรงกับโปรเจกต์คุณ

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 2. ต้องใส่ @Component เพื่อให้ Spring จัดการ Bean
// 3. กำหนดชื่อ Bean ในวงเล็บให้ชัดเจน (เช่น "sendEmailDelegate")
@Component("sendEmailDelegate") 
public class SendEmailDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailDelegate.class);

    // ตัวอย่าง: การ Inject Service อื่นๆ เข้ามาใช้งาน (จุดเด่นของการใช้ Spring)
    // private final EmailService emailService;
    // public SendEmailDelegate(EmailService emailService) { this.emailService = emailService; }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // 4. ดึงตัวแปรจาก Process (Variable)
        String requestId = (String) execution.getVariable("requestId");
        String applicant = (String) execution.getVariable("applicantName");

        LOGGER.info(">>> เริ่มทำงาน Delegate: SendEmailDelegate");
        LOGGER.info(">>> Request ID: {}", requestId);
        LOGGER.info(">>> ผู้ยื่นคำขอ: {}", applicant);

        // 5. ใส่ Logic ทางธุรกิจที่นี่
        // เช่น เรียก Service ส่งอีเมล, บันทึกลง Database, เรียก API ภายนอก
        // emailService.sendEmail(applicant, "อนุมัติสำเร็จ");

        // 6. (Optional) เซ็ตตัวแปรกลับเข้าไปใน Process
        execution.setVariable("emailStatus", "SENT");
        
        LOGGER.info(">>> เสร็จสิ้นการทำงาน Delegate");
    }
}