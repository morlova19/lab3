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
        ResultSet rs = null;
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
                if(l.equals(login) && p.equals(pass)) {
                    b= true;
                }
                else {
                    b= false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return b;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                return b;
            } catch (SQLException e) {
                e.printStackTrace();
                return b;

            }
        }

    }
    public static int getEmp_ID(String login) {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT EMPID FROM EMP WHERE LOGIN = ?)");
            stat.setString(1,login);
            rs = stat.executeQuery();
            if( rs.next())
            {
                return rs.getInt(1);
            }
            else {
                return -1;
            }


        } catch (SQLException e) {
            return -1;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                return -1;
            }
        }
    }

    public static void registration(EmpTransferObject emp) {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT EMPID FROM EMP WHERE FNAME=? AND LNAME=? AND JOB=? AND DEPTID=(SELECT DEPTID FROM DEPT WHERE DNAME=?)");
            st.setString(1,emp.getFname());
            st.setString(2,emp.getLname());
            st.setString(3,emp.getJob());
            st.setString(4,emp.getDept());
            rs = st.executeQuery();
            int emp_id = 0;
            if(rs != null)
            {
                rs.next();
                emp_id = rs.getInt(1);
            }

            st = conn.prepareStatement("UPDATE EMP SET LOGIN=?, PASS=? WHERE EMPID=?)");
            st.setString(1,emp.getLogin());
            st.setString(2,emp.getPass());
            st.setInt(3,emp_id);

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
    public static List<Employee> getEmps(Integer mgr_id)
    {
        Connection conn = null;
        ResultSet rs = null;
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
        } catch (SQLException e) {
            e.printStackTrace();
            return emps;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return emps;
    }
    public  List<String> getDepts(){
        List<String> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT dname FROM dept");
            rs = stat.executeQuery();
            while (rs.next()){
                list.add(rs.getString(1));
            }

        } catch (SQLException e) {
            return list;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                return list;
            } catch (SQLException e) {

                return list;
            }
        }
    }
    public static List<String> getJobs(String dname){
        List<String> list = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();

            PreparedStatement stat = conn.prepareStatement("SELECT DISTINCT JOB FROM emp WHERE deptid=(select deptid from dept where dname=?)");
            stat.setString(1,dname.toUpperCase());
            rs = stat.executeQuery();
            while (rs.next()){
                list.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            list.add(e.getMessage());
            return list;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                return list;
            } catch (SQLException e) {
                e.printStackTrace();
                return list;
            }
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

        } catch (SQLException e) {
            return list;
        }
        finally {
            try {
                conn.close();
                return list;
            } catch (SQLException e) {
                return list;
            }
        }
    }
    public static int checkEmp(EmpTransferObject emp){
        Connection conn = null;
        ResultSet rs = null;
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
            try {
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean checkLogin(String login){
        Connection conn = null;
        ResultSet rs = null;
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

        } catch (SQLException e) {
            e.printStackTrace();
            return isUnique ;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                return isUnique;
            } catch (SQLException e) {
                e.printStackTrace();
                return isUnique;
            }
        }
    }
    public static void registration(Integer empid,String login,String pass){
        Connection conn = null;
        ResultSet rs = null;
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
            try {
                conn.close();

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
    public static Employee getEmp(String login){
        Connection conn = null;
        ResultSet rs = null;
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
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
        return emp;
    }

    public String getEmpName(int empid){
        Connection conn = null;
        ResultSet rs = null;
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
            try {
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
