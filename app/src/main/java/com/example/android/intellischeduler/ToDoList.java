package com.example.android.intellischeduler;

/**
 * Created by AKANKSH on 3/30/2018.
 */

public class ToDoList {
    public String taskName;
    public String duration;
    public String priority;
    public String tstamp;
    ToDoList(String taskName,String duration,String priority,String tstamp){
        this.taskName=taskName;
        this.duration=duration;
        this.priority=priority;
        this.tstamp=tstamp;
    }
}
