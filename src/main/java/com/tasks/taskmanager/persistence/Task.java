package com.tasks.taskmanager.persistence;

import com.tasks.taskmanager.common.TaskStatusConstraint;
import com.tasks.taskmanager.rest.Status;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {

    @Id
    @GenericGenerator(name = "task_id", strategy = "com.tasks.taskmanager.common.TaskIdGenerator")
    @GeneratedValue(generator = "task_id")
    private String id;
    private String title;
    private String description;
    @TaskStatusConstraint(enumClass = Status.class)
    private Status status;

}
