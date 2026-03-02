package com.example.demo.dto;

public class LeaveRequestDTO {
    
    private String employee;
    private String employeeName;
    private String employeeEmail;
    private String manager;
    private String startDate;
    private String endDate;
    private String reason;

    // Constructors
    public LeaveRequestDTO() {}

    public LeaveRequestDTO(String employee, String employeeName, String employeeEmail, 
                          String manager, String startDate, String endDate, String reason) {
        this.employee = employee;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.manager = manager;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    // Getters and Setters
    public String getEmployee() { return employee; }
    public void setEmployee(String employee) { this.employee = employee; }
    
    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    
    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }
    
    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }
    
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}