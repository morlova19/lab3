package emp;

import DAO.EmpDAO;
import DAO.TaskDAO;
import jm.IJournalManager;
import jm.JournalManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Employee implements Serializable{
    private Integer ID;
    private String login;
    private String fname;
    private String lname;
    private String job;
    private String dept;
    private String mgr;
    private int task_count;
    private int completed_tasks;
    private int current_tasks;
    private JournalManager journalManager;
    private List<Employee> emps;

    public Employee(int id) {
        this.ID = id;
        journalManager = new JournalManager(ID);
        emps = EmpDAO.getEmps(ID);
    }
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public JournalManager getJournalManager() {
        return journalManager;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public String getName()
    {
        return fname + " " + lname;
    }


    public Integer getID() {
        return ID;
    }
    public Employee getEmp(int id){

        return EmpDAO.getEmp(id);
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }
    public String getName(int empid)
    {
        if(empid==this.ID)
        {
            return "Me";
        }
        else {
            return EmpDAO.getEmpName(empid);
        }
    }

    public String getMgr() {
        return mgr;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }

    public int getTask_count() {
        return task_count;
    }

    public void setTask_count(int task_count) {
        this.task_count = task_count;
    }

    public int getCompleted_tasks() {
        return completed_tasks;
    }

    public void setCompleted_tasks(int completed_tasks) {
        this.completed_tasks = completed_tasks;
    }

    public int getCurrent_tasks() {
        return current_tasks;
    }

    public void setCurrent_tasks(int current_tasks) {
        this.current_tasks = current_tasks;
    }

    public Employee getBoss()
    {
        return EmpDAO.getBoss();
    }
}
