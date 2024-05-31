package com.sara.cta03_skhon;

public class TaskItem {
    private long id;
    private String task;
    private boolean completed;

    public TaskItem(long id, String task, boolean completed) {
        this.id = id;
        this.task = task;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public boolean isCompleted() {
        return completed;
    }
}
