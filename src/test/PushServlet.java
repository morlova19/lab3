package test;

import DAO.TaskDAO;
import com.google.gson.Gson;
import journal.Subtask;
import journal.Task;
import utils.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet(urlPatterns = "/PushServlet")
public class PushServlet extends HttpServlet{
    Map<Integer, Message> map = new ConcurrentHashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String endpoint = req.getParameter("endpoint");
        if(endpoint != null) {
            String[] a = endpoint.split("/");
            endpoint = a[a.length-1];
            System.out.println(endpoint);
        }
        Gson gson = new Gson();
        int empid = -1;
        int taskid = -1;
        int subtaskid = -1;
        if(map!= null && !map.isEmpty()) {

            for(Message m: map.values())
            {
                if(m.getRegIds().contains(endpoint))
                {
                    empid = m.getEmpid();
                    taskid = m.getTaskid();
                    subtaskid = m.getSubtaskid();
                }
            }
        }
        if(subtaskid != -1)
        {
            if(empid != -1 && taskid != -1) {
                Subtask t = TaskDAO.getSubtask(empid, taskid,subtaskid);
                String str = gson.toJson(t);
                System.out.println(str);
                PrintWriter pw = resp.getWriter();
                pw.print(str);
                pw.close();
            }
        }
        else {
            if(empid != -1 && taskid != -1) {
                Task t = TaskDAO.getTask(empid, taskid);
                String str = gson.toJson(t);
                System.out.println(str);
                PrintWriter pw = resp.getWriter();
                pw.print(str);
                pw.close();
            }
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("========================================");
        Enumeration<String> enumeration = req.getParameterNames();
        Message content = null;
        Gson gson = new Gson();
        if (enumeration.hasMoreElements()){
            content = gson.fromJson(enumeration.nextElement(),Message.class);
            if(map.containsKey(content.getEmpid()))
            {
                map.replace(content.getEmpid(),content);
            }
            else {
                map.put(content.getEmpid(), content);
            }
        }



    }
}
