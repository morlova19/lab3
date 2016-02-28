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
        try {
            conn = ds.getConnection();
            PreparedStatement stat = conn.prepareStatement("INSERT INTO registration_id(id,registration_id,empid) VALUES (nextval('reg_id_seq'),?,?)");
            stat.setString(1,regId);
            stat.setInt(2,emp_id);
            stat.executeUpdate();
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
