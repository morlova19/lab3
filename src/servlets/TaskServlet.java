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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet for working with tasks.
 * E.g., create new task, update existing task , delete task  etc.
 */
@WebServlet(urlPatterns = {"/newtask","/deletetask",
        "/newsubtask","/deletesubtask",
        "/savetask","/savesubtask","/completetask","/completesubtask","/copytask","/updatedate","/updatename"})
public class TaskServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path){
            case "/deletetask":
                deleteTask(req,resp);
                break;
            case "/newtask":
                newTask(req,resp);
                break;
            case "/savetask":
                saveTask(req,resp);
                break;
            case "/deletesubtask":
                deleteSubtask(req,resp);
                break;
            case "/newsubtask":
                newSubtask(req,resp);
                break;
            case "/savesubtask":
                saveSubtask(req,resp);
                break;
            case "/completetask":
                completeTask(req,resp);
                break;
            case "/completesubtask":
                completeSubtask(req,resp);
                break;
            case "/copytask":
                copy(req,resp);
                break;
            case "/updatedate":
                updatedate(req,resp);
                break;
            case "/updatename":
                updatename(req,resp);
                break;
        }

    }

    private void updatename(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer taskid = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        TaskDAO.updateName(taskid,name);
        resp.sendRedirect(req.getHeader("referer"));
    }

    private void updatedate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("qqqqqqq");
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

    private void completeSubtask(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    private void completeTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getParameter("taskid");
        assert str!=null;
      Integer taskid=Integer.parseInt(str);
        TaskDAO.completeTask(taskid);
        resp.sendRedirect(req.getHeader("referer"));

    }
    private void newTask(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
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
    private void saveTask(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        JournalManager jm = emp.getJournalManager();

        String action = req.getParameter("update").toLowerCase();
        int taskid = Integer.parseInt(req.getParameter("taskid"));
        switch (action)
        {
            case "save":
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
                to.setPriority(Integer.parseInt(req.getParameter("priority")));

                to.setEx_id(Integer.parseInt(req.getParameter("ex_id")));
                to.setCr_id(emp.getID());
                to.setStatus(req.getParameter("status"));
                jm.updateTask(taskid,to);
                break;
            case "complete":
               jm.complete(taskid);

                break;
            case "cancel":
                jm.cancel(taskid);
                break;
        }


        resp.sendRedirect(req.getHeader("referer"));

    }
    private void saveSubtask(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        JournalManager jm = emp.getJournalManager();

        Integer st_id = Integer.parseInt(req.getParameter("stid"));
        TransferObject to = new TransferObject();
        to.setName(req.getParameter("name"));
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        to.setDescription(req.getParameter("desc"));
        try {
            to.setDate(df.parse(req.getParameter("date")));
        } catch (ParseException e) {
            to.setDate(new Date());
        }
        to.setContacts(req.getParameter("contacts"));
        int taskid = Integer.parseInt(req.getParameter("taskid"));
        jm.updateSubtask(taskid,st_id,to);
        resp.sendRedirect(req.getHeader("referer"));
    }
    private void newSubtask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        JournalManager jm = emp.getJournalManager();

        Integer t_id = Integer.parseInt(req.getParameter("taskid"));
        TransferObject to = new TransferObject();
        to.setName(req.getParameter("name"));
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        to.setDescription(req.getParameter("desc"));
        try {
            to.setDate(df.parse(req.getParameter("date")));
        } catch (ParseException e) {
            to.setDate(new Date());
        }
//        to.setStatus(false);
        to.setContacts(req.getParameter("contacts"));
        jm.addSubtask(t_id,new Task(to));
        Task t = jm.get(t_id);

            resp.sendRedirect("task.jsp?taskid=" + t_id);


    }
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
    private void deleteSubtask(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        assert emp!=null;
        JournalManager jm = emp.getJournalManager();

        String id = req.getParameter("taskid");
        int t_id = Integer.parseInt(id);
        String[] ids = req.getParameterValues("id");
        for(String s: ids)
        {
            jm.deleteSubtask(t_id,Integer.parseInt(s));
        }

        resp.sendRedirect(req.getHeader("referer"));
    }
}
