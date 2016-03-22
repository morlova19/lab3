package jm;

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

    Task get(int id)throws RemoteException;


    void addSubtask(int t_id, Task stask);

    void deleteSubtask(Integer t_id, Integer st_id);

    Task getSubtask(Integer pt_id,Integer t_id);

    List getSubtasks(Integer pt_id, String status);

    List getSubtasks(Integer pt_id);

    List getTasks(String status);

    void updateTask(int t_id, TransferObject to);

    List searchTasks(String param);

}
