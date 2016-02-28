package utils;


import java.util.List;

public class Message {
    private int empid;
    private int taskid;
    private int subtaskid;
    private List<String> regIds;
    public void createMessage(int empid, int taskid){
        this.empid = empid;
        this.taskid = taskid;
    }

    public void setRegIds(List<String> regIds) {
        this.regIds = regIds;
    }

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
