package journal;


import utils.TransferObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Part of taskmgr.
 */
public class Journal implements Serializable {
    /**
     * List of current tasks.
     */
    private List<Task> tasks = new CopyOnWriteArrayList<>();
    /**
     * List of completed tasks.
     */

    public Task getTask(int id) {

        for(Task t: tasks) {
            if(t.getID() == id) {
                return t;
            }
        }
       return null;
    }
    /**
     * Adds task in list.
     * @param newTask new task.
     */
    public void addTask(Task newTask) {
        if(tasks != null) {
            tasks.add(newTask);
        }
    }
    /**
     * Deletes task.
     * @param id identifier of task that will be deleted.
     */
    public void deleteTask(int id) {
        for(Task t: tasks)
        {
            if(t.getID() == id)
            {
                tasks.remove(t);
                return;
            }
        }
    }
    /**
     * Delays task.
     * @param id identifier of task that will be delayed.
     * @param newDate new date of task.
     */
    public void delayTask(int id, Date newDate) {
       /* Task t = currentTasks
                .stream()
                .filter(task -> task.getID() == id)
                .findFirst().get();
        t.setDate(newDate);*/
    }

    /**
     * Makes task completed.
     * Removes from current tasks and adds into completed tasks.
     * @param task completed task.
     */
    public void setCompleted(Task task) {
        for(Task t: tasks)
        {
            if(t.getID() == task.getID())
            {
                t.setCompleted(true);
                return;
            }
        }
        /*currentTasks.remove(task);
        completedTasks.add(task);*/
    }

    /**
     * Gets list of current tasks.
     * @return current tasks.
     */
    public List<Task> getCurrentTasks() {
        CopyOnWriteArrayList<Task> list = new CopyOnWriteArrayList<Task>();
        if(!tasks.isEmpty()) {
            tasks.stream().forEach(task -> {
                if(task!=null) {
                    if (!task.getCompleted()) {
                        list.add(task);
                    }
                }
            });
        }
        return list;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
        CopyOnWriteArrayList<Task> list = new CopyOnWriteArrayList<Task>();
        tasks.stream().forEach(task -> {
            if (task.getCompleted()) {
                list.add(task);
            }
        });
        return list;
    }
    /**
     * Sets list of completed tasks.
     * @param completedTasks completed tasks.
     */
   /* public void setCompletedTasks(CopyOnWriteArrayList<Task> completedTasks) {
       this.completedTasks = completedTasks;

    }*/

    /**
     * Checks if there are among the current problems already completed tasks.
     * If finds such tasks, makes them completed.
     */
    public void reload() {
        if(!tasks.isEmpty())
        {
            for (Task t: tasks)
            {
                long delta = t.getDate().getTime() - Calendar.getInstance().getTimeInMillis();
                if(delta <= 0) {
                    t.setCompleted(true);
                }
            }

        }
    }
    public List<Task> getTasks()
    {
        return tasks;
    }


    public void addSubtask(int t_id, Subtask stask) {
        Task t = getTask(t_id);
        if(t != null)
        {
          t.getSubtasks().add(stask);
        }
    }


    public void deleteSubtask(Integer t_id, Integer st_id) {
        Task t = getTask(t_id);
        if(t != null)
        {
            List<Subtask> list = t.getSubtasks();
            if(list != null){
                for(Subtask st: list)
                {
                    if(st.getID() == st_id)
                    {
                        list.remove(st);
                        return;
                    }
                }
            }
        }
    }

    public List getCurrentSubtasks(Integer t_id) {

        Task task = getTask(t_id);

        List<Subtask> list = null;
        if(task != null)
        {
            list = new ArrayList<>();

            final List<Subtask> finalList = list;
            task.getSubtasks().stream().forEach(task1 -> {
                if (!task1.getCompleted()) {
                    finalList.add(task1);
                }
            });

        }
        return list;
    }


    public List getCompletedSubtasks(Integer t_id) {
        Task task = getTask(t_id);

        List<Subtask> list = null;
        if(task != null)
        {
            list = new ArrayList<>();

            final List<Subtask> finalList = list;
            task.getSubtasks().stream().forEach(task1 -> {
                if (task1.getCompleted()) {
                    finalList.add(task1);
                }
            });

        }
        return list;
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
        tasks.stream().forEach(task -> {
            if(task.getID() == t_id)
            {
                task.setName(to.getName());
                task.setDate(to.getDate());
                task.setDescription(to.getDescription());
                task.setContacts(to.getContacts());
            }
        });

    }


    public void updateSubtask(int t_id, int st_id, TransferObject to) {
        Task t = getTask(t_id);

        if(t != null){
            t.getSubtasks().stream().forEach(subtask -> {
                if(subtask.getID() == st_id)
                {
                    subtask.setName(to.getName());
                    subtask.setDate(to.getDate());
                    subtask.setDescription(to.getDescription());
                    subtask.setContacts(to.getContacts());
                }
            });
        }
    }
    public Subtask getSubtask(int t_id,int st_id){
        Task t = getTask(t_id);

        if(t != null){
           List<Subtask> list = t.getSubtasks();
            if(list != null)
            {
                for(Subtask st: list){
                    if(st.getID()==st_id)
                    {
                        return st;
                    }
                }
            }
        }
        return null;
    }

    public List searchTasks(String param) {
        List<Task> t = null;
        if(tasks != null && !tasks.isEmpty())
        {
            t = new ArrayList<>();
            for(Task task:tasks){
                if (task.getName().toLowerCase().contains(param.toLowerCase())){
                    t.add(task);
                }
            }
            return t;
        }
        return null;
    }
}
