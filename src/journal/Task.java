package journal;
import DAO.TaskDAO;
import utils.TransferObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    /**
     * Contacts.
     */
    private String contacts;
    /**
     * Identifier of the task.
     */
    private Integer ID;

    private Boolean completed;


    private Map<Integer,Subtask> subtasks_map;
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
        this.completed = data.getCompleted();
        subtasks_map = new HashMap<>();
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("NAME: ")
                .append(this.name)
                .append("\nDESCRIPTION: ")
                .append(this.description)
                .append("\nCONTACTS: ")
                .append(this.contacts);
        return s.toString();
    }

    public List<Subtask> getSubtasks() {
        return subtasks_map.values().stream().collect(Collectors.toList());
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setSubtasks(List<Subtask> subtasks) {
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

    public Map<Integer, Subtask> getSubtasks_map() {
        return subtasks_map;
    }
}
