package com.byted.camp.todolist.beans;

public enum Priority {

    Low(0),Normal(1),High(2);
    public final int intValue;

    Priority(int intValue) {
        this.intValue = intValue;
    }
    public static Priority from(int intValue) {
        for (Priority priority : Priority.values()) {
            if (priority.intValue == intValue) {
                return priority;
            }
        }
        return Low; // default
    }
}
