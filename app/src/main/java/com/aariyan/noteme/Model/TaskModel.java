package com.aariyan.noteme.Model;

public class TaskModel {
    private int primaryKey;
    private String taskName,taskCreatedDate,taskDeadline,taskStatus,taskDescription,email,phone,url;

    public TaskModel(){}

    public TaskModel(int primaryKey, String taskName, String taskCreatedDate, String taskDeadline, String taskStatus, String taskDescription, String email, String phone, String url) {
        this.primaryKey = primaryKey;
        this.taskName = taskName;
        this.taskCreatedDate = taskCreatedDate;
        this.taskDeadline = taskDeadline;
        this.taskStatus = taskStatus;
        this.taskDescription = taskDescription;
        this.email = email;
        this.phone = phone;
        this.url = url;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCreatedDate() {
        return taskCreatedDate;
    }

    public void setTaskCreatedDate(String taskCreatedDate) {
        this.taskCreatedDate = taskCreatedDate;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
