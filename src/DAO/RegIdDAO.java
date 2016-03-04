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
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("SELECT id,registration_id,empid FROM registration_id WHERE registration_id=?");
            stat.setString(1,regId);
            rs = stat.executeQuery();
            if(!rs.next()) {
                stat = conn.prepareStatement("INSERT INTO registration_id(id,registration_id,empid) VALUES (nextval('reg_id_seq'),?,?)");
                stat.setString(1, regId);
                stat.setInt(2, emp_id);
                stat.executeUpdate();
            }
            else {
                if(rs.getInt(3)!=emp_id)
                {
                    stat = conn.prepareStatement("UPDATE  registration_id SET empid=? WHERE  id=?");
                    stat.setInt(1, emp_id);
                    stat.setInt(2, rs.getInt(1));
                    stat.executeUpdate();
                }
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

    }
}
