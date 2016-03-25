package emp;

import DAO.EmpDAO;
import DAO.TaskDAO;
import jm.IJournalManager;
import jm.JournalManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Employee implements Serializable{
    private Integer ID;
    private String login;
    private String fname;
    private String lname;
    private String job;
    private String dept;
    private String mgr;
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

    public int total_count(int id) {
        return journalManager.total_count(id);
    }

    public int comp_count(int id) {
        return journalManager.comp_count(id);
    }

    public int cur_count(int id) {
        return journalManager.cur_count(id);
    }
    public int failed_count(int id) {
        return journalManager.failed_count(id);
    }
    public int cancelled_count(int id) {
        return journalManager.cancelled_count(id);
    }
    public Employee getBoss()
    {
        return EmpDAO.getBoss();
    }

    public boolean showStat(int id)
    {
        if(id==this.ID)
        {
            return true;
        }
        if(emps!=null)
        {
            if( !emps.isEmpty())
            {
                List<Integer> list = emps.stream().map(Employee::getID).collect(Collectors.toList());
                return list.contains(id);
            }
        }

        return false;
    }
}
