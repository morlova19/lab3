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
     * @throws RemoteException
     */
    void delete(int id) ;

    /**
     * Gets current tasks.
     * @return list of current tasks.
     * @throws RemoteException
     */
    List getCurrentTasks();
    /**
     * Gets completed tasks.
     * @return list of completed tasks.
     * @throws RemoteException
     */
    List getCompletedTasks();
    List getTasks();

    void complete(int id);

    void delay(int id, Date newDate);

    Task get(int id)throws RemoteException;


    void addSubtask(int t_id, Subtask stask);

    void deleteSubtask(Integer t_id, Integer st_id);
    Subtask getSubtask(Integer t_id,Integer st_id);
    List getCurrentSubtasks(Integer t_id);
    List getCompletedSubtasks(Integer t_id);
    void completeSubtask(Integer t_id, Integer st_id);
    List getSubtasks(Integer t_id);
    List getTasks(String type);

    void updateTask(int t_id, TransferObject to);
    void updateSubtask(int t_id, int st_id, TransferObject to);

    List searchTasks(String param);

}
