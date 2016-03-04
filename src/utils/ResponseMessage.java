package utils;

public class ResponseMessage {
    private int empid;
    private int taskid;
    private int subtaskid;
    private  String name;
    private String description;

    public void createMessage(int empid, int taskid, int subtaskid, String name, String desc) {
        this.empid = empid;
        this.taskid = taskid;
        this.subtaskid = subtaskid;
        this.name = name;
        this.description = desc;
    }
}
