package ru.mera.agileboard.rest.info;

/**
 * Created by antfom on 12.03.2015.
 */
public class TagStringInfo {
    private int task;
    private String tags;

    public TagStringInfo() {
    }


    public TagStringInfo(int task, String tags) {
        this.task = task;
        this.tags = tags;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
