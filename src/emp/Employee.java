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
    private int dept;
    private JournalManager journalManager;
    private List<Employee> emps;
    private Map<Integer,Employee> emps_map;

    public Employee(int id) {
        this.ID = id;
        journalManager = new JournalManager(ID);
        emps = EmpDAO.getEmps(ID);
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

    public String getEmpName(int id)
    {
        if(emps != null && !emps.isEmpty())
        {
            emps.stream().filter(employee -> employee.getID()==id).findFirst().get().getName();
        }
        return null;
    }

    public Integer getID() {
        return ID;
    }
    public Employee getEmp(int id){
        if(emps != null && !emps.isEmpty())
        {
            return emps.stream().filter(employee -> employee.getID()==id).findFirst().get();
        }
        return null;
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
}
