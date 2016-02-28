package DAO;


import journal.Subtask;
import journal.Task;
import utils.TransferObject;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private static DataSource ds = DAOFactory.getDataSource();

    public static void addTask(int empid, Task task) {
        Connection conn = null;
        try {
            conn = ds.getConnection();

          /*  PreparedStatement stat = conn.prepareStatement("INSERT INTO TASK(T_ID," +
                    "NAME,COMPLETED,TDESC,TDATE,CONTACTS,EMP_ID) VALUES (TASK_ID_SEQ.NEXTVAL,?,?,?,?,?,?)");*/
            PreparedStatement stat = conn.prepareStatement("INSERT INTO task(T_ID,NAME,COMPLETED,TDESC,TDATE,CONTACTS,EMPID) " +
                    "VALUES (nextval('task_id_seq'),?,?,?,?,?,?)");
            stat.setString(1,task.getName());
            stat.setBoolean(2,task.getCompleted());
            stat.setString(3,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            stat.setTimestamp(4, timestamp);
            stat.setString(5,task.getContacts());
            stat.setInt(6,empid);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
    public static void addSubtask(int t_id, Subtask task) {
        Connection conn = null;
        try {
            conn = ds.getConnection();

          /*  PreparedStatement stat = conn.prepareStatement("INSERT INTO TASK(T_ID," +
                    "NAME,COMPLETED,TDESC,TDATE,CONTACTS,EMP_ID) VALUES (TASK_ID_SEQ.NEXTVAL,?,?,?,?,?,?)");*/
            PreparedStatement stat = conn.prepareStatement("INSERT INTO SUBTASK(ST_ID," +
                    "NAME,COMPLETED,STDESC,STDATE,CONTACTS,T_ID) VALUES (NEXTVAL('subtask_id_seq'),?,?,?,?,?,?)");
            stat.setString(1,task.getName());
            stat.setBoolean(2,task.getCompleted());
            stat.setString(3,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            stat.setTimestamp(4, timestamp);
            stat.setString(5,task.getContacts());
            stat.setInt(6,t_id);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
    public static void deleteTask(int empid, int t_id) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("DELETE FROM SUBTASK WHERE T_ID = ?");
            stat.setInt(1,t_id);
            stat.executeUpdate();
            stat = conn.prepareStatement("DELETE FROM TASK WHERE EMPID = ? AND T_ID=?");
            stat.setInt(1,empid);
            stat.setInt(2,t_id);
            stat.executeUpdate();

        } catch (SQLException e) {
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static void deleteSubtask(int t_id, int st_id) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("DELETE FROM SUBTASK WHERE T_ID = ? AND ST_ID=?");
            stat.setInt(1,t_id);
            stat.setInt(2,st_id);
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public static List<Task> getTasks(int empid) {
        List<Task> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,COMPLETED,TDESC,TDATE,CONTACTS FROM TASK " +
                    "WHERE EMPID = ?");
            stat.setInt(1,empid);
            rs = stat.executeQuery();
            while (rs.next())
            {
                TransferObject to = new TransferObject();
                to.setId(rs.getInt(1));
                to.setName(rs.getString(2));
                to.setCompleted(rs.getBoolean(3));
                to.setDescription(rs.getString(4));
                to.setDate(rs.getTimestamp(5));
                to.setContacts(rs.getString(6));
                tasks.add(new Task(to));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }
    public static Task getTask(int empid, int t_id) {

        Task task = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,COMPLETED,TDESC,TDATE,CONTACTS FROM TASK " +
                    "WHERE EMPID = ? AND T_ID=? ");
            stat.setInt(1,empid);
            stat.setInt(2,t_id);
            rs = stat.executeQuery();
            while (rs.next())
            {
                TransferObject to = new TransferObject();
                to.setId(rs.getInt(1));
                to.setName(rs.getString(2));
                to.setCompleted(rs.getBoolean(3));
                to.setDescription(rs.getString(4));
                to.setDate(rs.getTimestamp(5));
                to.setContacts(rs.getString(6));
                task = new Task(to);
            }
        } catch (SQLException e) {
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return task;
    }
    public static Subtask getSubtask(int empid, int t_id,int stid) {

        Subtask task = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT ST_ID,NAME,COMPLETED,STDESC,STDATE,CONTACTS FROM SUBTASK " +
                    "WHERE EMPID = ? AND T_ID=? AND ST_ID=? ");
            stat.setInt(1,empid);
            stat.setInt(2,t_id);
            stat.setInt(3,stid);
            rs = stat.executeQuery();
            while (rs.next())
            {
                TransferObject to = new TransferObject();
                to.setId(rs.getInt(1));
                to.setName(rs.getString(2));
                to.setCompleted(rs.getBoolean(3));
                to.setDescription(rs.getString(4));
                to.setDate(rs.getTimestamp(5));
                to.setContacts(rs.getString(6));
                task = new Subtask(to);
            }
        } catch (SQLException e) {
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return task;
    }
    public static void updateTask(int empid,Task task) {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE TASK SET NAME = ?,TDESC = ?,TDATE = ?,CONTACTS = ?,COMPLETED = ? WHERE T_ID=? AND EMPID=?");
            st.setString(1, task.getName());
            st.setString(2,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            st.setTimestamp(3, timestamp);
            st.setString(4,task.getContacts());
            st.setBoolean(5,task.getCompleted());
            st.setInt(6,task.getID());
            st.setInt(7,empid);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
    public static void updateSubtask(int t_id,Subtask task) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE SUBTASK SET NAME = ?,STDESC = ?,STDATE = ?,CONTACTS = ?,COMPLETED = ? WHERE ST_ID=? AND T_ID=?");
            st.setString(1, task.getName());
            st.setString(2,task.getDescription());
            Timestamp timestamp = new Timestamp(task.getDate().getTime());
            st.setTimestamp(3, timestamp);
            st.setString(4,task.getContacts());
            st.setBoolean(5,task.getCompleted());
            st.setInt(6,task.getID());
            st.setInt(7,t_id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
    public static List<Subtask> getSubtasks(Integer t_id){
        List<Subtask> tasks = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT ST_ID,NAME,COMPLETED,STDESC,STDATE,CONTACTS FROM SUBTASK " +
                    "WHERE T_ID = ? ");
            stat.setInt(1,t_id);
            rs = stat.executeQuery();
            while (rs.next())
            {
                TransferObject to = new TransferObject();
                to.setId(rs.getInt(1));
                to.setName(rs.getString(2));
                to.setCompleted(rs.getBoolean(3));
                to.setDescription(rs.getString(4));
                to.setDate(rs.getTimestamp(5));
                to.setContacts(rs.getString(6));
                tasks.add(new Subtask(to));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return tasks;
    }
    public static Task getLastTask(int emp_id){
        Task task = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT T_ID,NAME,COMPLETED,TDESC,TDATE,CONTACTS FROM TASK " +
                    "WHERE EMPID = ? AND T_ID = currval('task_id_seq')");
            stat.setInt(1,emp_id);
            rs = stat.executeQuery();
            rs.next();

                TransferObject to = new TransferObject();
                to.setId(rs.getInt(1));
                to.setName(rs.getString(2));
                to.setCompleted(rs.getBoolean(3));
                to.setDescription(rs.getString(4));
                to.setDate(rs.getTimestamp(5));
                to.setContacts(rs.getString(6));
                task = new Task(to);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return task;
    }
    public static Subtask getLastSubtask(int t_id){
        Subtask task = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT ST_ID,NAME,COMPLETED,STDESC,STDATE,CONTACTS FROM SUBTASK " +
                    "WHERE T_ID = ? AND ST_ID = currval('subtask_id_seq')");
            stat.setInt(1,t_id);
            rs = stat.executeQuery();
            rs.next();

                TransferObject to = new TransferObject();
                to.setId(rs.getInt(1));
                to.setName(rs.getString(2));
                to.setCompleted(rs.getBoolean(3));
                to.setDescription(rs.getString(4));
                to.setDate(rs.getTimestamp(5));
                to.setContacts(rs.getString(6));
                task = new Subtask(to);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return task;
    }
}
