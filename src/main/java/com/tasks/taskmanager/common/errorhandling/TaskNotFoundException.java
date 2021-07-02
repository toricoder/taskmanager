package com.tasks.taskmanager.common.errorhandling;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String msg) {
        super(msg);
    }

}
