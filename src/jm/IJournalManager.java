package jm;

import journal.Subtask;
import journal.Task;
import utils.TransferObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Remote interface of journal manager.
 */
public interface IJournalManager extends Remote {
    /**
     * Adds task.
     * @param task new task.
     */
    void add(Task task);
    /**
     * Deletes task with specified identifier.
     * @param id identifier of task.
     */
    void delete(int id) ;

    /**
     * Gets current tasks.
     * @return list of current tasks.
     */
    List getCurrentTasks();
    /**
     * Gets completed tasks.
     * @return list of completed tasks.
     *
     */
    List getCompletedTasks();
    /**
     * Gets all tasks.
     * @return list of tasks.
     */
    List getTasks();

    /**
     * Completes task with specified id.
     * @param id task's id.
     */
    void complete(int id);

    /**
     * Delays task with specified id.
     * @param id task's id.
     * @param newDate new task's date.
     */
    void delay(int id, Date newDate);

    Task get(int id)throws RemoteException;


    void addSubtask(int t_id, Subtask stask);
    void deleteSubtask(Integer t_id, Integer st_id);
    Subtask getSubtask(Integer t_id,Integer st_id);

    void delaySubtask(int taskid,int stid, Date newDate);
    List getCurrentSubtasks(Integer t_id);
    List getCompletedSubtasks(Integer t_id);
    void completeSubtask(Integer t_id, Integer st_id);
    List getSubtasks(Integer t_id);
    List getTasks(String type);
    void updateTask(int t_id, TransferObject to);
    void updateSubtask(int t_id, int st_id, TransferObject to);
    List searchTasks(String param);

}
