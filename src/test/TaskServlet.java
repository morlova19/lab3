package test;


import DAO.TaskDAO;
import emp.Employee;
import jm.JournalManager;
import journal.Subtask;
import journal.Task;
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

@WebServlet(urlPatterns = {"/my/newtask","/my/deletetask",
        "/my/newsubtask","/my/deletesubtask",
        "/my/savetask","/my/savesubtask","/completetask","/completesubtask"})
public class TaskServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        System.out.println(path);
        switch (path){
            case "/my/deletetask":
                deleteTask(req,resp);
                break;
            case "/my/newtask":
                newTask(req,resp);
                break;
            case "/my/savetask":
                saveTask(req,resp);
                break;
            case "/my/deletesubtask":
                deleteSubtask(req,resp);
                break;
            case "/my/newsubtask":
                newSubtask(req,resp);
                break;
            case "/my/savesubtask":
                saveSubtask(req,resp);
                break;
            case "/completetask":
                completeTask(req,resp);
                break;
            case "/completesubtask":
                completeSubtask(req,resp);
                break;
        }

    }

    private void completeSubtask(HttpServletRequest req, HttpServletResponse resp) {
        Integer taskid = Integer.parseInt(req.getParameter("taskid"));
        Integer subtaskid = Integer.parseInt(req.getParameter("subtaskid"));
        String action = req.getParameter("action").toLowerCase();
        if(action.equals("delay"))
        {
            String date = req.getParameter("newdate");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            try {
                TaskDAO.delaySubtask(taskid,subtaskid,sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(action.equals("finish"))
        {
            TaskDAO.copmleteSubtask(taskid,subtaskid);
        }
    }

    private void completeTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer empid = Integer.parseInt(req.getParameter("empid"));
        Integer taskid = Integer.parseInt(req.getParameter("taskid"));
        String action = req.getParameter("action").toLowerCase();
        if(action.equals("delay"))
        {
            String date = req.getParameter("newdate");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            try {
                TaskDAO.delayTask(empid,taskid,sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(action.equals("finish"))
        {
            TaskDAO.copmleteTask(empid,taskid);
        }



    }

    private void newTask(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        JournalManager jm = emp.getJournalManager();

        TransferObject to = new TransferObject();
        to.setName(req.getParameter("name"));
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        to.setDescription(req.getParameter("desc"));
        try {
            to.setDate(df.parse(req.getParameter("date")));
        } catch (ParseException e) {
            to.setDate(new Date());
        }
        to.setCompleted(false);
        to.setContacts(req.getParameter("contacts"));
        jm.add(new Task(to));
        resp.sendRedirect("tasks.jsp?type=cur");
        //resp.sendRedirect("success.jsp?ttype=Task" );
      //  resp.sendRedirect(req.getHeader("referer"));
    }
    private void saveTask(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        JournalManager jm = emp.getJournalManager();

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
      //  resp.sendRedirect("success.jsp?ttype="+taskid);
        jm.updateTask(taskid,to);
        resp.sendRedirect(req.getHeader("referer"));

    }
    private void saveSubtask(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
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
        JournalManager jm = emp.getJournalManager();

        Integer t_id = Integer.parseInt(req.getParameter("taskid"));
        TransferObject to = new TransferObject();
       // Random r = new Random();
        //to.setId(r.nextInt(50));
        to.setName(req.getParameter("name"));
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        to.setDescription(req.getParameter("desc"));
        try {
            to.setDate(df.parse(req.getParameter("date")));
        } catch (ParseException e) {
            to.setDate(new Date());
        }
        to.setCompleted(false);
        to.setContacts(req.getParameter("contacts"));
        jm.addSubtask(t_id,new Subtask(to));
        Task t = jm.get(t_id);
        if(t.getCompleted())
        {
            resp.sendRedirect("comp_task.jsp?taskid=" + t_id);
        }
        else {
            resp.sendRedirect("cur_task.jsp?taskid=" + t_id);
        }
       //resp.sendRedirect("success.jsp?ttype=Subtask");
    }
    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee emp = (Employee) req.getSession().getAttribute("emp");
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
