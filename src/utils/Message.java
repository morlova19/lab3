package utils;


import java.util.List;

/**
 * Message that was received from notification system.
 * Create from json object.
 * Contains details about identifiers of browsers of the empolyee and info about task and subtask.
 */
public class Message {
    private int empid;
    private int taskid;
    private int subtaskid;
    private List<String> regIds;

    public int getEmpid() {
        return empid;
    }

    public int getTaskid() {
        return taskid;
    }

    public List<String> getRegIds() {
        return regIds;
    }

    public int getSubtaskid() {
        return subtaskid;
    }
}
