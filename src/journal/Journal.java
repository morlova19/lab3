package journal;

import utils.TransferObject;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Part of taskmgr.
 */
public class Journal implements Serializable {

    private Map<Integer,Task> tasks_map = new HashMap<>();

    public Task getTask(int id) {

       return tasks_map.get(id);
    }
    /**
     * Adds task in list.
     * @param newTask new task.
     */
    public void addTask(Task newTask) {
        if(tasks_map != null) {
            tasks_map.put(newTask.getID(),newTask);
        }
    }
    /**
     * Deletes task.
     * @param id identifier of task that will be deleted.
     */
    public void deleteTask(int id) {
        tasks_map.remove(id);
    }
    /**
     * Delays task.
     * @param id identifier of task that will be delayed.
     * @param newDate new date of task.
     */
    public void delayTask(int id, Date newDate) {
        tasks_map.get(id).setDate(newDate);
    }

    /**
     * Makes task completed.
     * Removes from current tasks and adds into completed tasks.
     * @param task completed task.
     */
    public void setCompleted(Task task) {

        tasks_map.get(task.getID()).setCompleted(true);
    }

    /**
     * Gets list of current tasks.
     * @return current tasks.
     */
    public List<Task> getCurrentTasks() {
        if(!tasks_map.isEmpty()) {
            return tasks_map.values().stream().filter(task -> !task.getCompleted()).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public void setTasks(List<Task> tasks) {
        if(!tasks_map.isEmpty())
        {
            tasks_map.clear();
        }
        tasks.stream().forEach(task -> tasks_map.put(task.getID(),task));
        //this.tasks = tasks;
    }

    /**
     * Sets list of current tasks.
     * @param currentTasks current tasks.
     */
  /*  public void setCurrentTasks(CopyOnWriteArrayList<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }*/
    /**
     * Gets list of completed tasks.
     * @return completed tasks.
     */
    public List<Task> getCompletedTasks() {
        if(!tasks_map.isEmpty()) {
            return tasks_map.values().stream().filter(Task::getCompleted).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


    /**
     * Checks if there are among the current problems already completed tasks.
     * If finds such tasks, makes them completed.
     */
    public void reload() {
        if(!tasks_map.isEmpty())
        {
            tasks_map.values().stream().forEach(t -> {
                long delta = t.getDate().getTime() - Calendar.getInstance().getTimeInMillis();
                if(delta <= 0) {
                    t.setCompleted(true);
                }
            });


        }
    }
    public List<Task> getTasks()
    {
        return  tasks_map.values().stream().collect(Collectors.toList());
    }


    public void addSubtask(int t_id, Subtask stask) {

        Task t = tasks_map.get(t_id);
        if(t != null)
        {
            t.getSubtasks_map().put(stask.getID(),stask);
         // t.getSubtasks().add(stask);
        }
    }


    public void deleteSubtask(Integer t_id, Integer st_id) {

        Task t = tasks_map.get(t_id);
        if(t != null)
        {
            t.getSubtasks_map().remove(st_id);

        }
    }

    public List getCurrentSubtasks(Integer t_id) {

        Task task = tasks_map.get(t_id);

        List<Subtask> list = null;
        if(task != null)
        {
            return task.getSubtasks().stream().filter(subtask -> !subtask.getCompleted()).collect(Collectors.toList());

        }
        return new CopyOnWriteArrayList<>();
    }


    public List getCompletedSubtasks(Integer t_id) {

        Task task = tasks_map.get(t_id);

        if(task != null)
        {
            return task.getSubtasks().stream().filter(Subtask::getCompleted).collect(Collectors.toList());


        }
        return new CopyOnWriteArrayList<>();
    }



    public void completeSubtask(Integer t_id, Integer st_id)  {

    }


    public List getSubtasks(Integer t_id) {
        Task task = getTask(t_id);

        if(task != null)
        {
           return task.getSubtasks();

        }
        return null;
    }

    public void updateTask(int t_id, TransferObject to) {
        Task task = tasks_map.get(t_id);
        if(!task.getName().equals(to.getName())){
            task.setName(to.getName());
        }
        if(task.getDate().compareTo(to.getDate())!=0){
            task.setDate(to.getDate());
        }
        if(!task.getDescription().equals(to.getDescription())){
            task.setDescription(to.getDescription());
        }
        if(!task.getContacts().equals(to.getContacts())){
            task.setContacts(to.getContacts());
        }
       tasks_map.replace(t_id,task);


    }


    public void updateSubtask(int t_id, int st_id, TransferObject to) {

        Task t = tasks_map.get(t_id);

        if(t != null){
          Subtask task=  t.getSubtasks_map().get(st_id);
            if(!task.getName().equals(to.getName())){
                task.setName(to.getName());
            }
            if(task.getDate().compareTo(to.getDate())!=0){
                task.setDate(to.getDate());
            }
            if(!task.getDescription().equals(to.getDescription())){
                task.setDescription(to.getDescription());
            }
            if(!task.getContacts().equals(to.getContacts())){
                task.setContacts(to.getContacts());
            }
            t.getSubtasks_map().replace(st_id,task);

        }
    }
    public Subtask getSubtask(int t_id, int st_id){

        Task t = tasks_map.get(t_id);

        if(t != null){
            return t.getSubtasks_map().get(st_id);

        }
        return null;
    }

    public List searchTasks(String param) {
        List<Task> t = null;
        if(tasks_map != null && !tasks_map.isEmpty())
        {
            return tasks_map.values().stream().filter(task -> task.getName().toLowerCase().contains(param.toLowerCase())).collect(Collectors.toList());
        }
        return null;
    }

    public Map<Integer, Task> getTasks_map() {
        return tasks_map;
    }
}
