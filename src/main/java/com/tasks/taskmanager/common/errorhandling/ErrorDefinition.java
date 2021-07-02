package com.tasks.taskmanager.common.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDefinition implements Serializable {
    private int status;
    private String error;
    private String message;
    List<String> details;
}
