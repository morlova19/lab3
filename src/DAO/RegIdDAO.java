package DAO;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegIdDAO {
    private static DataSource ds = DAOFactory.getDataSource();

    public static void addRegId(String regId,int emp_id){
        Connection conn = null;
        ResultSet rs;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT ID,REGISTRATION_ID,EMPID FROM REGISTRATION_ID WHERE REGISTRATION_ID=?");
            stat.setString(1,regId);
            rs = stat.executeQuery();
            if(!rs.next()) {
               // stat = conn.prepareStatement("INSERT INTO registration_id(id,registration_id,empid) VALUES (nextval('reg_id_seq'),?,?)");
                stat = conn.prepareStatement("INSERT INTO REGISTRATION_ID(ID,REGISTRATION_ID,EMPID) VALUES (REG_ID_SEQ.NEXTVAL,?,?)");
                stat.setString(1, regId);
                stat.setInt(2, emp_id);
                stat.executeUpdate();
            }
            else {
                if(rs.getInt(3)!=emp_id)
                {
                    stat = conn.prepareStatement("UPDATE  REGISTRATION_ID SET EMPID=? WHERE  ID=?");
                    stat.setInt(1, emp_id);
                    stat.setInt(2, rs.getInt(1));
                    stat.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
