package jm;

import DAO.TaskDAO;
import journal.Subtask;
import journal.Task;
import journal.Journal;
import utils.TransferObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Part of taskmgr.
 */

public class JournalManager implements IJournalManager, Serializable {
    private Integer emp_id;
    /**
     * Journal of tasks.
     */
    private Journal journal;
    /**
     * Creates journal manager and fills fields.
     */
    public JournalManager(Integer emp_id) {
        this.emp_id = emp_id;
        loadJournal();
    }
    /**
     * Loads journal.
     */
    public void loadJournal()  {
        journal = readJournal();
    }

    public  void add(Task task)  {
        if(task != null) {
            TaskDAO.addTask(emp_id,task);
            journal.addTask(TaskDAO.getLastTask(emp_id));
        }
    }
    public  void delete(int id)  {
        journal.deleteTask(id);
        TaskDAO.deleteTask(emp_id,id);
    }

    @Override
    public List getCurrentTasks() {
        return journal.getCurrentTasks();
    }

    @Override
    public List getCompletedTasks() {
        return journal.getCompletedTasks();
    }

    @Override
    public List getTasks() {
        return journal.getTasks();
    }

    public Task get(int id) {
        return journal.getTask(id);
    }

    @Override
    public void addSubtask(int t_id, Subtask stask) {
        TaskDAO.addSubtask(t_id,stask);
        journal.addSubtask(t_id,TaskDAO.getLastSubtask(t_id));

    }

    @Override
    public void deleteSubtask(Integer t_id, Integer st_id) {
        journal.deleteSubtask(t_id,st_id);
        TaskDAO.deleteSubtask(t_id,st_id);
    }

    @Override
    public Subtask getSubtask(Integer t_id,Integer st_id) {
        return journal.getSubtask(t_id,st_id);
    }

    @Override
    public void delaySubtask(int taskid, int stid, Date newDate) {
        journal.delaySubtask(taskid,stid,newDate);
    }

    @Override
    public List getCurrentSubtasks(Integer t_id) {
        return journal.getCurrentSubtasks(t_id);
    }

    @Override
    public List getCompletedSubtasks(Integer t_id) {
        return journal.getCompletedSubtasks(t_id);
    }


    @Override
    public void completeSubtask(Integer t_id, Integer st_id)  {
        journal.completeSubtask(t_id,st_id);
    }

    @Override
    public List getSubtasks(Integer t_id) {
        return journal.getSubtasks(t_id);
    }

    public void delay(int id, Date newDate) {
        journal.delayTask(id, newDate);
        TaskDAO.updateTask(emp_id,journal.getTask(id));
    }

    public void complete(int id)  {
        journal.setCompleted(journal.getTask(id));
       // TaskDAO.updateTask(emp_id,journal.getTask(id));
    }

    /**
     * Reads journal.
     * @return journal.
     */
    public Journal readJournal()  {
        Journal journal = new Journal();
        List<Task> tasks = TaskDAO.getTasks(emp_id);
        journal.setTasks(tasks);
        return journal;
    }
    public List getTasks(String str)
    {
        return journal.getTasks();
    }

    @Override
    public void updateTask(int t_id,TransferObject to) {
        journal.updateTask(t_id, to);
        TaskDAO.updateTask(emp_id,journal.getTask(t_id));
    }

    @Override
    public void updateSubtask(int t_id, int st_id, TransferObject to) {
       journal.updateSubtask(t_id, st_id, to);
        TaskDAO.updateSubtask(t_id,journal.getSubtask(t_id,st_id));
    }

    @Override
    public List searchTasks(String param) {

        return journal.searchTasks(param);
    }
}
