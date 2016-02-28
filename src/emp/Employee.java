package emp;

import DAO.EmpDAO;
import jm.IJournalManager;
import jm.JournalManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employee implements Serializable{
    private Integer ID;
    private String login;
    private String fname;
    private String lname;
    private JournalManager journalManager;
    private List<Employee> emps;

   /* public Employee(String login) {
        this.login = login;
        journalManager = new JournalManager(login);
        emps = new ArrayList<>();
       // emps = EmpDAO.getEmps(ID);
    }*/
    public Employee(int id) {
        this.ID = id;
        journalManager = new JournalManager(ID);
       // emps = new ArrayList<>();
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
            for(Employee e: emps)
            {
                if(e.getID()==id){
                    return e;
                }
            }
            //emps.stream().filter(employee -> employee.getID()==id).findFirst().get();
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
}
