package journal;


import utils.TransferObject;

import java.util.Date;

public class Subtask {
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
    private int ID;

    private boolean completed;

    /**
     * Creates a task with the given parameters.
     * @param data object that contains parameters of the task.
     */

    public Subtask(TransferObject data) {
        this.name = data.getName();
        this.description = data.getDescription();
        this.date = data.getDate();
        this.contacts = data.getContacts();
        this.completed = data.getCompleted();
        this.ID = data.getId();
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
    public  String getDescription() {
        return description;
    }
    /**
     *  Gets date of execution of the task.
     * @return date of execution.
     */
    public  Date getDate() {
        return date;
    }
    /**
     * Gets contacts.
     * @return contacts.
     */
    public  String getContacts() {
        return contacts;
    }
    /**
     * Sets new date of execution of the task.
     * @param date new date.
     */
    public  void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets identifier of the task.
     * @param ID identifier.
     */
    public  void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Gets identifier of the task.
     * @return identifier.
     */
    public  int getID() {
        return ID;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
}
