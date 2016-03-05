package DAO;

import emp.EmpTransferObject;
import emp.Employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO {
    private static DataSource ds = DAOFactory.getDataSource();
    public EmpDAO() {}
    public static boolean login(String login, String pass) {
        Connection conn = null;
        ResultSet rs ;
        boolean b = false;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT LOGIN, PASS FROM EMP WHERE LOGIN = ? ");
            stat.setString(1,login);
            rs = stat.executeQuery();

            if(rs != null) {
                rs.next();
                String l = rs.getString(1);
                String p = rs.getString(2);
                b = l.equals(login) && p.equals(pass);
            }
            return b;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
           closeConnection(conn);
        }

    }
    public static List<Employee> getEmps(Integer mgr_id) {
        Connection conn = null;
        ResultSet rs;
       List<Employee> emps = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT EMPID,FNAME,LNAME FROM EMP WHERE MGR = ?");
            stat.setInt(1,mgr_id);
            rs = stat.executeQuery();
            emps = new ArrayList<>();
            while(rs.next())
            {
                Employee emp = new Employee(rs.getInt(1));
                emp.setFname(rs.getString(2));
                emp.setLname(rs.getString(3));
                emps.add(emp);
                //emp.setLogin(login);
            }
            return emps;
        } catch (SQLException e) {
            e.printStackTrace();
            return emps;
        }
        finally {
            closeConnection(conn);
        }

    }
    public  List<String> getDepts(){
        List<String> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT dname FROM dept");
            rs = stat.executeQuery();
            while (rs.next()){
                list.add(rs.getString(1));
            }
            return list;
        } catch (SQLException e) {
            return list;
        }
        finally {
            closeConnection(conn);
        }
    }


    public static List<String> getJobs(String dname){
        List<String> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT DISTINCT JOB FROM emp WHERE deptid=(select deptid from dept where dname=?)");
            stat.setString(1,dname.toUpperCase());
            rs = stat.executeQuery();
            while (rs.next()){
                list.add(rs.getString(1));
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            list.add(e.getMessage());
            return list;
        }
        finally {
           closeConnection(conn);
        }
    }
    public static List<String> getEmps(String dname, String job){
        List<String> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT FNAME||' '||LNAME ename FROM emp WHERE deptid=(select deptid from dept where dname=?) AND JOB=? AND LOGIN IS NULL");
            stat.setString(1,dname.toUpperCase());
            stat.setString(2,job.toUpperCase());
            rs = stat.executeQuery();
            while (rs.next()){
                list.add(rs.getString(1));
            }
            return list;

        } catch (SQLException e) {
            return list;
        }
        finally {
           closeConnection(conn);

        }
    }
    public static int checkEmp(EmpTransferObject emp){
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT EMPID FROM EMP WHERE FNAME=? AND LNAME=? AND JOB=? AND DEPTID=(SELECT DEPTID FROM DEPT WHERE DNAME=?) AND LOGIN IS NULL");
            st.setString(1,emp.getFname());
            st.setString(2,emp.getLname());
            st.setString(3,emp.getJob());
            st.setString(4,emp.getDept());
            rs = st.executeQuery();
          if(rs.next()){
              return rs.getInt(1);
          }
            else {
              return -1;
          }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            closeConnection(conn);
        }
    }
    public static boolean checkLogin(String login){
        Connection conn = null;
        ResultSet rs;
        boolean isUnique=false;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT LOGIN FROM EMP WHERE LOGIN IS NOT NULL");
            rs = stat.executeQuery();

            if (!rs.next() ) {
                isUnique = true;
            } else {

                do {
                    if(!rs.getString(1).equalsIgnoreCase(login)){
                        isUnique = true;
                    }
                } while (rs.next());
            }
            return isUnique;

        } catch (SQLException e) {
            e.printStackTrace();
            return isUnique ;
        }
        finally {
            closeConnection(conn);

        }
    }
    public static void registration(Integer empid,String login,String pass){
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE EMP SET LOGIN=?, PASS=? WHERE EMPID=?");
            st.setString(1,login);
            st.setString(2,pass);
            st.setInt(3,empid);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }
    public static Employee getEmp(String login){
        Connection conn = null;
        ResultSet rs;
        Employee emp = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT EMPID,FNAME,LNAME FROM EMP WHERE LOGIN = ?");
            stat.setString(1,login);
            rs = stat.executeQuery();
            if(rs.next())
            {
                emp = new Employee(rs.getInt(1));
                emp.setFname(rs.getString(2));
                emp.setLname(rs.getString(3));
                emp.setLogin(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return emp;
        }
        finally {
            closeConnection(conn);
        }
        return emp;
    }
    public String getEmpName(int empid){
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT FNAME ||' '|| LNAME FROM EMP WHERE EMPID=?");
            st.setInt(1,empid);

            rs = st.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
            else {
                return "";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
        finally {
            closeConnection(conn);
        }
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
}
