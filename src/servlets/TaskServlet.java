package servlets;


import DAO.TaskDAO;
import emp.Employee;
import jm.JournalManager;
import journal.Task;
import utils.Constants;
import utils.TransferObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet for working with tasks.
 * E.g., create new task, update existing task , delete task  etc.
 */
@WebServlet(urlPatterns = {"/newtask","/deletetask",
        "/savetask","/completetask","/activatetask","/copytask","/updatedate","/updatename","/updatestatus","/updateexec","/canceltask"})
public class TaskServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path){
            case "/deletetask":
                delete(req,resp);
                break;
            case "/newtask":
                create(req,resp);
                break;
            case "/savetask":
                update(req,resp);
                break;
            case "/completetask":
                complete(req,resp);
                break;
            case "/canceltask":
                cancel(req,resp);
                break;
            case "/activatetask":
                activate(req,resp);
                break;
            case "/copytask":
                copy(req,resp);
                break;
            case "/updatedate":
                updateDate(req,resp);
                break;
            case "/updatename":
                updateName(req,resp);
                break;
            case "/updatestatus":
                updateStatus(req,resp);
                break;
            case "/updateexec":
                updateExecutor(req,resp);
                break;
        }

    }

    private void cancel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer taskid = Integer.parseInt(req.getParameter("taskid"));
        TaskDAO.updateStatus(taskid,Constants.CANCELLED);
        resp.sendRedirect(req.getHeader("referer"));
    }

    private void updateExecutor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer taskid = Integer.parseInt(req.getParameter("id"));
        Integer ex_id = Integer.parseInt(req.getParameter("ex_id"));
        TaskDAO.updateExecutor(taskid,ex_id);
        resp.sendRedirect(req.getHeader("referer"));
    }

    private void updateStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer taskid = Integer.parseInt(req.getParameter("id"));
        String status = req.getParameter("status");
        TaskDAO.updateStatus(taskid,status);
        resp.sendRedirect(req.getHeader("referer"));
    }
    private void updateName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer taskid = Integer.parseInt(req.getParameter("id"));
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        TaskDAO.updateName(taskid,name);
        resp.sendRedirect(req.getHeader("referer"));
    }
    private void updateDate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer taskid = Integer.parseInt(req.getParameter("id"));
        String date = req.getParameter("date");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date parse_date = sdf.parse(date);
            TaskDAO.updateDate(taskid,parse_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getHeader("referer"));
    }
    private void copy(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer taskid = Integer.parseInt(req.getParameter("id"));
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        TaskDAO.copyTask(taskid,emp.getID());
        resp.sendRedirect(req.getHeader("referer"));
    }

    private void activate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getParameter("taskid");
        assert str!=null;
        Integer taskid=Integer.parseInt(str);
        TaskDAO.activateTask(taskid);
        resp.sendRedirect(req.getHeader("referer"));
    }

    private void complete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getParameter("taskid");
        assert str!=null;
        Integer taskid=Integer.parseInt(str);
        TaskDAO.completeTask(taskid);
        resp.sendRedirect("tasks.jsp?type=my");
    }
    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        req.setCharacterEncoding("UTF-8");
        JournalManager jm = emp.getJournalManager();
        String pt_id = req.getParameter("pt_id");

        TransferObject to = new TransferObject();
        to.setName(req.getParameter("name"));
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        to.setDescription(req.getParameter("desc"));
        try {
            to.setDate(df.parse(req.getParameter("date")));
        } catch (ParseException e) {
            to.setDate(new Date());
        }
        to.setStatus(Constants.NEW);
        to.setContacts(req.getParameter("contacts"));
        to.setPriority(Integer.parseInt(req.getParameter("priority")));
        to.setCr_id(emp.getID());
        to.setEx_id(Integer.parseInt(req.getParameter("ex_id")));
        if(pt_id!=null)
        {
            int pt_id_int = Integer.parseInt(pt_id);
            to.setPt_id(pt_id_int);
            jm.addSubtask(pt_id_int,new Task(to));
            resp.sendRedirect("task.jsp?taskid=" + pt_id_int);
        }
        else {
            jm.add(new Task(to));
            resp.sendRedirect("tasks.jsp?type=my");
        }


    }
    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        JournalManager jm = emp.getJournalManager();
        req.setCharacterEncoding("UTF-8");
        int taskid = Integer.parseInt(req.getParameter("taskid"));
                TransferObject to = new TransferObject();
                to.setId(taskid);
                to.setName(req.getParameter("name"));
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                to.setDescription(req.getParameter("desc"));
                try {
                    to.setDate(df.parse(req.getParameter("date")));
                } catch (ParseException e) {
                    to.setDate(new Date());
                }
                to.setContacts(req.getParameter("contacts"));
                String priority = req.getParameter("priority").toUpperCase();
                switch (priority) {
                    case Constants.FULL_LOW:
                        to.setPriority(Constants.LOW);
                        break;
                    case Constants.FULL_NORMAL:
                        to.setPriority(Constants.NORMAL);
                        break;
                    case Constants.FULL_HIGH:
                        to.setPriority(Constants.HIGH);
                        break;
                    default:
                        to.setPriority(Integer.parseInt(priority));
                        break;
                }
                to.setEx_id(Integer.parseInt(req.getParameter("ex_id")));
                to.setCr_id(Integer.parseInt(req.getParameter("cr_id")));
                String pt_id = req.getParameter("pt_id");
                if(pt_id != null)
                {
                    to.setPt_id(Integer.parseInt(pt_id));
                }
                String status = req.getParameter("status").toUpperCase();
                switch (status) {
                    case Constants.FULL_NEW:
                        to.setStatus(Constants.NEW);
                        break;
                    case Constants.FULL_PERFORMING:
                        to.setStatus(Constants.PERFORMING);
                        break;
                    case Constants.FULL_CANCELLED:
                        to.setStatus(Constants.CANCELLED);
                        break;
                    case Constants.FULL_COMPLETED:
                        to.setStatus(Constants.COMPLETED);
                        break;
                    default:
                        to.setStatus(status);
                        break;
                }
                jm.updateTask(taskid,to);
        resp.sendRedirect(req.getHeader("referer"));


    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        JournalManager jm = emp.getJournalManager();

        String[] ids = req.getParameterValues("id");
        for(String s: ids)
        {
            jm.delete(Integer.parseInt(s));
        }
        resp.sendRedirect(req.getHeader("referer"));
    }

}
