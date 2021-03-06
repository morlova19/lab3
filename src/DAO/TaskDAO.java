package DAO;


import com.google.gson.Gson;
import journal.Task;
import utils.Constants;
import utils.TransferObject;


import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class TaskDAO {
    private static DataSource ds = DAOFactory.getDataSource();


    public static void addTask(Task task) {
        Connection conn = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("INSERT INTO TASK(T_ID," +
                    "NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,CRDATE) VALUES (TASK_ID_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,SYSTIMESTAMP)");

            stat.setString(1,task.getName());
            stat.setString(2,task.getStatus());
            stat.setString(3,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            stat.setTimestamp(4, timestamp);
            stat.setString(5,task.getContacts());
            stat.setInt(6,task.getPriority());
            stat.setInt(7,task.getCr_id());
            stat.setInt(8,task.getEx_id());
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }


    public static void addSubtask(Task task) {
        Connection conn = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("INSERT INTO TASK(T_ID," +
                    "NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE) VALUES (TASK_ID_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSTIMESTAMP)");

            stat.setString(1,task.getName());
            stat.setString(2,task.getStatus());
            stat.setString(3,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            stat.setTimestamp(4, timestamp);
            stat.setString(5,task.getContacts());
            stat.setInt(6,task.getPriority());
            stat.setInt(7,task.getCr_id());
            stat.setInt(8,task.getEx_id());
            stat.setInt(9,task.getPt_id());
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public static void deleteTask(int t_id) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("DELETE FROM TASK WHERE PT_ID = ?");
            stat.setInt(1,t_id);
            stat.executeUpdate();
            stat = conn.prepareStatement("DELETE FROM TASK WHERE T_ID = ?");
            stat.setInt(1,t_id);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void deleteSubtask(int pt_id, int t_id) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("DELETE FROM TASK WHERE T_ID = ? AND PT_ID=?");
            stat.setInt(1,t_id);
            stat.setInt(2,pt_id);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static List<Task> getTasks(int ex_id) {
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT t.T_ID,t.NAME,t.STATUS,t.TDESC,t.TDATE,t.CONTACTS,t.PRIORITY,t.CR_ID,t.EX_ID,t.PT_ID,t.CRDATE FROM TASK t,TASK pt " +
                    "WHERE pt.t_id(+) = t.pt_id AND t.ex_id = ? AND (pt.ex_id IS NULL OR pt.ex_id <> ?)");
            stat.setInt(1,ex_id);
            stat.setInt(2,ex_id);
            rs = stat.executeQuery();
            tasks = createTasksFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return tasks;
    }
    public static List<Task> getTasks(int empid, String status) {
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE FROM TASK " +
                    "WHERE CR_ID = ? AND STATUS=?");

            stat.setInt(1,empid);
            stat.setString(2,status);
            rs = stat.executeQuery();
            tasks = createTasksFromResultSet( rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return tasks;
    }
    public static List<Task> getEmpsTasks(int mgr) {
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT t.T_ID,t.NAME,t.STATUS,t.TDESC,t.TDATE,t.CONTACTS,t.PRIORITY,t.CR_ID,t.EX_ID,t.PT_ID,t.CRDATE " +
                    "FROM task pt, task t WHERE pt.t_id(+)= t.pt_id AND t.ex_id  IN (SELECT  EMPID FROM EMP WHERE MGR=?) AND (pt.ex_id IS NULL OR pt.ex_id <> t.ex_id)");

            stat.setInt(1,mgr);

            rs = stat.executeQuery();
            tasks = createTasksFromResultSet( rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return tasks;
    }
    private static List<Task> createTasksFromResultSet(ResultSet rs) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        while (rs.next())
        {
            TransferObject to = new TransferObject();
            to.setId(rs.getInt(1));
            to.setName(rs.getString(2));
            to.setStatus(rs.getString(3));
            to.setDescription(rs.getString(4));
            to.setDate(rs.getTimestamp(5));
            to.setContacts(rs.getString(6));
            to.setPriority(rs.getInt(7));
            to.setCr_id(rs.getInt(8));
            to.setEx_id(rs.getInt(9));
            to.setPt_id(rs.getInt(10));
            to.setCrdate(new Date(rs.getTimestamp(11).getTime()));
            tasks.add(new Task(to));
        }
        return tasks;
    }
    private static Task createTaskFromResultSet(ResultSet rs) throws SQLException {
        Task task;
        TransferObject to = new TransferObject();
        if(rs.next()) {
            to.setId(rs.getInt(1));
            to.setName(rs.getString(2));
            to.setStatus(rs.getString(3));
            to.setDescription(rs.getString(4));
            to.setDate(rs.getTimestamp(5));
            to.setContacts(rs.getString(6));
            to.setPriority(rs.getInt(7));
            to.setCr_id(rs.getInt(8));
            to.setEx_id(rs.getInt(9));
            to.setPt_id(rs.getInt(10));
            to.setCrdate(new Date(rs.getTimestamp(11).getTime()));
        }
        task = new Task(to);

        return task;
    }
    public static List<Task> getCurrentTasks(int empid) {
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE FROM TASK WHERE CR_ID = ? AND STATUS <> ? AND STATUS <> ?");
            stat.setInt(1,empid);
            stat.setString(2, Constants.COMPLETED);
            stat.setString(3, Constants.CANCELLED);
            rs = stat.executeQuery();
            tasks = createTasksFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return tasks;
    }

    public static Task getTask(int t_id) {
        Task task = null;
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE FROM TASK " +
                    "WHERE T_ID=?");
            stat.setInt(1,t_id);
            rs = stat.executeQuery();
           task = createTaskFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return task;
    }
    public static Task getSubtask(int t_id,int pt_id) {

        Task task = null;
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE FROM TASK  " +
                    "WHERE  T_ID=? AND PT_ID=? ");
            stat.setInt(1,t_id);
            stat.setInt(2,pt_id);
            rs = stat.executeQuery();
            task = createTaskFromResultSet(rs);
            return task;
        } catch (SQLException e) {
            e.printStackTrace();
            return task;
        }
        finally {
          closeConnection(conn);
        }

    }
    public static void updateTask(Task task) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("UPDATE TASK SET NAME = ?,TDESC = ?,TDATE = ?,CONTACTS = ?,STATUS = ?,PRIORITY=? ,EX_ID=?, PT_ID=? WHERE T_ID=?");
            stat.setString(1,task.getName());
            stat.setString(5,task.getStatus());
            stat.setString(2,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            stat.setTimestamp(3, timestamp);
            stat.setString(4,task.getContacts());
            stat.setInt(6,task.getPriority());
            stat.setInt(7,task.getEx_id());
            stat.setInt(8,task.getPt_id());
            stat.setInt(9,task.getID());

            System.out.println("qwerty");
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }

    public static void updateTask(int t_id, TransferObject task) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("UPDATE TASK SET NAME = ?,TDESC = ?,TDATE = ?,CONTACTS = ?,STATUS = ?,PRIORITY=? ,EX_ID=?,CR_ID=?,PT_ID=? WHERE T_ID=?");
            stat.setString(1,task.getName());
            stat.setString(2,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            stat.setTimestamp(3, timestamp);
            stat.setString(4,task.getContacts());
            stat.setString(5,task.getStatus());
            stat.setInt(6,task.getPriority());

            stat.setInt(7,task.getEx_id());
            stat.setInt(8,task.getCr_id());
            if(task.getPt_id()==0)
            {
                stat.setNull(9,Types.INTEGER);
            }
            else {
                stat.setInt(9,task.getPt_id());
            }
            stat.setInt(10,t_id);
           stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }
    public static List<Task> getSubtasks(Integer t_id){
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE FROM TASK WHERE PT_ID = ?");
            stat.setInt(1,t_id);
            rs = stat.executeQuery();
            tasks = createTasksFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return tasks;
    }

    public static void completeTask(int taskid) {
       updateStatus(taskid,Constants.COMPLETED);
    }

    public static void updateDate(int taskid, java.util.Date date) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("UPDATE TASK SET TDATE=? WHERE  T_ID=?");
            Timestamp timestamp = new Timestamp(date.getTime());
            stat.setTimestamp(1,timestamp);
            stat.setInt(2,taskid);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
           closeConnection(conn);
        }
    }


    public static List getCurrentSubtasks(Integer pt_id) {
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE FROM TASK WHERE PT_ID = ? AND STATUS <> ? AND STATUS <> ?");
            stat.setInt(1,pt_id);
            stat.setString(2, Constants.COMPLETED);
            stat.setString(3, Constants.CANCELLED);
            rs = stat.executeQuery();
            tasks = createTasksFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return tasks;
    }


    public static List findTasks(String str,int empid) {
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,STATUS,TDESC,TDATE,CONTACTS,PRIORITY,CR_ID,EX_ID,PT_ID,CRDATE " +
                    "FROM TASK WHERE LOWER(NAME) LIKE ? AND EX_ID IN (SELECT EMPID FROM EMP WHERE MGR=? OR EMPID=?)");
            str = str.trim();
            str = str.toLowerCase();
            stat.setString(1,"%"+ str+"%");
            stat.setInt(2,empid);
            stat.setInt(3,empid);
            rs = stat.executeQuery();
            tasks = createTasksFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return tasks;
    }

    public static void cancelTask(int id) {
        updateStatus(id,Constants.CANCELLED);
    }
    public static void activateTask(int id) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("UPDATE TASK SET STATUS=? WHERE T_ID = ?");
            stat.setString(1,Constants.NEW);
            stat.setInt(2,id);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }
    public static void copyTask(int id,int new_cr_id)
    {
        Task t = getTask(id);
        t.setCr_id(new_cr_id);

        t.setStatus(Constants.NEW);

        if(t.getPt_id()!=0)
        {
            addSubtask(t);
        }else {
            addTask(t);
        }

    }

    public static void updateName(Integer taskid, String name) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("UPDATE TASK SET NAME=? WHERE T_ID = ?");
            stat.setString(1,name);
            stat.setInt(2,taskid);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }

    public static void updateStatus(Integer taskid, String status) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("UPDATE TASK SET STATUS=? WHERE T_ID = ?");
            stat.setString(1,status);
            stat.setInt(2,taskid);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }
    public static int getTotalCount(int empid)
    {

        Connection conn = null;
        try {
            conn = ds.getConnection();
            ResultSet rs;
            PreparedStatement stat =  conn.prepareStatement("SELECT COUNT(*) FROM TASK WHERE EX_ID = ?");
            stat.setInt(1,empid);
            rs = stat.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
       return 0;
    }
    public static int getCurrentCount(int empid)
    {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            ResultSet rs;
            PreparedStatement  stat = conn.prepareStatement("SELECT COUNT(*) FROM TASK WHERE EX_ID = ? AND STATUS IN (?,?) AND extract(second from tdate-(systimestamp )) * 1000 > 0");
            stat.setInt(1,empid);
            stat.setString(2,Constants.NEW);
            stat.setString(3,Constants.PERFORMING);
            rs = stat.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return 0;
    }
    public static int getCompletedCount(int empid)
    {

        Connection conn = null;
        try {
            conn = ds.getConnection();
            ResultSet rs;
            PreparedStatement    stat = conn.prepareStatement("SELECT COUNT(*) FROM TASK WHERE EX_ID = ? AND STATUS = ?");
            stat.setInt(1,empid);
            stat.setString(2,Constants.COMPLETED);
            rs = stat.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return 0;
    }
    public static int getCancelledCount(int empid)
    {

        Connection conn = null;
        try {
            conn = ds.getConnection();
            ResultSet rs;
            PreparedStatement    stat = conn.prepareStatement("SELECT COUNT(*) FROM TASK WHERE EX_ID = ? AND STATUS = ?");
            stat.setInt(1,empid);
            stat.setString(2,Constants.CANCELLED);
            rs = stat.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return 0;
    }
    public static int getFailedCount(int empid)
    {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            ResultSet rs;
            PreparedStatement    stat = conn.prepareStatement("SELECT COUNT(*) FROM TASK WHERE EX_ID = ? AND STATUS NOT IN (?,?) AND extract(second from tdate-(systimestamp )) * 1000 <=0");
            stat.setInt(1,empid);
            stat.setString(2,Constants.COMPLETED);
            stat.setString(3,Constants.CANCELLED);
            rs = stat.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return 0;
    }
    private static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void updateExecutor(Integer taskid, Integer ex_id) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("UPDATE TASK SET EX_ID=? WHERE T_ID = ?");
            stat.setInt(1,ex_id);
            stat.setInt(2,taskid);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }
}
