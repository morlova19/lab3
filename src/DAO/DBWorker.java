package DAO;

import jm.JournalManager;
import journal.Task;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DBWorker {
    public JournalManager getJournalManager(String login)
    {
        return new JournalManager(login);
    }
}
