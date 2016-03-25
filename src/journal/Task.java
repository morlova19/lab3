package journal;
import DAO.TaskDAO;
import utils.Constants;
import utils.TransferObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A task that will be executed at a specified time.
 * The task has 4 parameters such as name, description, date of execution, contacts.
 */
public class Task implements Serializable {
    /**
     * Name of the task.
     */
    private String name;
    /**
     * Description of the task.
     */
    private String description;
    /**
     * Time of execution of the task.
     */
    private Date date;

    private Date crdate;
    /**
     * Contacts.
     */
    private String contacts;
    /**
     * Identifier of the task.
     */
    private Integer ID;

    private String status;

    private int cr_id;

    private int ex_id;

    private int pt_id;

    private int priority;

    private Map<Integer,Task> subtasks_map;
    /**
     * Creates a task with the given parameters.
     * @param data object that contains parameters of the task.
     */
    public Task(TransferObject data) {
        this.ID = data.getId();
        this.name = data.getName();
        this.description = data.getDescription();
        this.date = data.getDate();
        this.contacts = data.getContacts();
        this.status = data.getStatus();
        subtasks_map = new HashMap<>();
        this.cr_id = data.getCr_id();
        this.ex_id = data.getEx_id();
        this.pt_id = data.getPt_id();
        this.priority = data.getPriority();
        this.crdate = data.getCrdate();
        setSubtasks(TaskDAO.getSubtasks(ID));
    }
    /**
     * Gets name of the task.
     * @return name of the task.
     */
    public String getName() {
        return name;
    }
    /**
     *  Gets description of the task.
     * @return description of the task.
     */
    public String getDescription() {
        return description;
    }
    /**
     *  Gets date of execution of the task.
     * @return date of execution.
     */
    public Date getDate() {
        return date;
    }
    /**
     * Gets contacts.
     * @return contacts.
     */
    public String getContacts() {
        return contacts;
    }
    /**
     * Sets new date of execution of the task.
     * @param date new date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets identifier of the task.
     * @param ID identifier.
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }
    /**
     * Gets identifier of the task.
     * @return identifier.
     */
    public Integer getID() {
        return ID;
    }


    public List<Task> getSubtasks() {
        return subtasks_map.values().stream().collect(Collectors.toList());
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSubtasks(List<Task> subtasks) {
        if(!subtasks_map.isEmpty()) {
            subtasks_map.clear();
        }
        subtasks.stream().forEach(subtask -> subtasks_map.put(subtask.getID(),subtask));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Map<Integer, Task> getSubtasks_map() {
        return subtasks_map;
    }

    public Date getCrdate() {
        return crdate;
    }

    public void setCrdate(Date crdate) {
        this.crdate = crdate;
    }

    public int getCr_id() {
        return cr_id;
    }

    public void setCr_id(int cr_id) {
        this.cr_id = cr_id;
    }

    public int getEx_id() {
        return ex_id;
    }

    public void setEx_id(int ex_id) {
        this.ex_id = ex_id;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getFullStatus()
    {
        if(date.getTime()-System.currentTimeMillis() <= 0)
        {
            if (status != null) {
                switch (status) {
                    case Constants.NEW:
                        return Constants.FULL_FAILED;
                    case Constants.CANCELLED:
                        return Constants.FULL_CANCELLED;
                    case Constants.PERFORMING:
                        return Constants.FULL_FAILED;
                    case Constants.COMPLETED:
                        return Constants.FULL_COMPLETED;
                    default:
                        return Constants.FULL_FAILED;
                }
            }
        }
        else {
            if (status != null) {
                switch (status) {
                    case Constants.NEW:
                        return Constants.FULL_NEW;
                    case Constants.CANCELLED:
                        return Constants.FULL_CANCELLED;
                    case Constants.PERFORMING:
                        return Constants.FULL_PERFORMING;
                    case Constants.COMPLETED:
                        return Constants.FULL_COMPLETED;
                    default:
                        return Constants.FULL_NEW;
                }
            }

        }
        return Constants.FULL_NEW;
    }
}
