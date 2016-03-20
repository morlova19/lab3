package utils;

import java.util.Date;

/**
 * TransferObject is an object which contains parameters that should be transferred.
 */
public class TransferObject {
    private String name;
    private String description;
    private Date date;
    private String contacts;
    private int id;
    private String status;
    private int cr_id;

    private int ex_id;

    private int pt_id;

    private int priority;

    private Date crdate;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getContacts() {
        return contacts;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getCrdate() {
        return crdate;
    }

    public void setCrdate(Date crdate) {
        this.crdate = crdate;
    }
}
