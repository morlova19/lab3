package utils;

/**
 * Message for sending to service worker as json object.
 * Contains details of task or subtask about which need show push notification.
 */
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
