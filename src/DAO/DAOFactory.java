package DAO;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {
    private static DataSource ds;
    static {
        try {
            InitialContext ic = new InitialContext();
            ds = (DataSource) ic.lookup("jdbc/OraclePool");
            //ds = (DataSource) ic.lookup("jdbc/PostgresqlPool");
        }  catch (NamingException e) {
            e.printStackTrace();
        }
    }
   static DataSource getDataSource()
    {
        return ds;
    }

}
