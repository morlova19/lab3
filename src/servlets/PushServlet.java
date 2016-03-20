package servlets;

import DAO.TaskDAO;
import com.google.gson.Gson;
import journal.Task;
import utils.Message;
import utils.ResponseMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Servlet for processing requests from notification system and service workers.
 * Method doPost processes requests from notification system.
 * Method doGet processes requests from service workers.
 */
@WebServlet(urlPatterns = "/PushServlet")
public class PushServlet extends HttpServlet{
    Map<Integer, List<Message>> map = new ConcurrentHashMap<>();

    /**
     * Processes request from service workers.
     * Gets list of tasks and subtasks for showing notification.
     * @param req request object.
     * @param resp response object.
     * @throws ServletException
     * @throws IOException
     */
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
            for(Collection<Message> msgs:map.values())
            {
                for(Message m: msgs)
                {
                    if(m.getRegIds().contains(endpoint))
                    {
                        empid = m.getEmpid();
                        taskid = m.getTaskid();
                        subtaskid = m.getSubtaskid();

                    }
                }
            }
        }
        if(subtaskid != -1)
        {
            if(empid != -1 && taskid != -1) {

                ResponseMessage msg = new ResponseMessage();
                TaskDAO dao = new TaskDAO();
                Task t = dao.getSubtask(taskid,subtaskid);
                msg.createMessage(empid,taskid,subtaskid,t.getName(),t.getDescription());
                String str = gson.toJson(msg);

                List<Message> m =  map.get(empid);
                for(Message m1:m){
                    if(m1.getEmpid()==empid && m1.getSubtaskid()==subtaskid && m1.getTaskid()==taskid){
                        m.remove(m1);
                    }
                }
                PrintWriter pw = resp.getWriter();
                pw.print(str);
                pw.close();
            }
        }
        else {
            if(empid != -1 && taskid != -1) {

                Task t = TaskDAO.getTask(taskid);
                ResponseMessage msg = new ResponseMessage();
                msg.createMessage(empid,taskid,subtaskid,t.getName(),t.getDescription());
                String str = gson.toJson(msg);
                List<Message> m =  map.get(empid);
                for(Message m1:m){
                    if(m1.getEmpid()==empid && m1.getSubtaskid()==subtaskid && m1.getTaskid()==taskid){
                        m.remove(m1);
                    }
                }
                PrintWriter pw = resp.getWriter();
                pw.print(str);
                pw.close();
            }
        }



    }

    /**
     * Processes request from notification system.
     * Gets info about task for which need show notification
     * and details of employee who will see this notification.
     * Adds this information to map.
     * @param req
     * @param resp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        Enumeration<String> enumeration = req.getParameterNames();
        Message content = null;
        Gson gson = new Gson();

        System.out.println(enumeration.hasMoreElements());
        if (enumeration.hasMoreElements()){

            content = gson.fromJson(enumeration.nextElement(),Message.class);
            if(map.containsKey(content.getEmpid()))
            {
                map.get(content.getEmpid()).add(content);
               // map.replace(content.getEmpid(),content);
            }
            else {
                List<Message> msg = new CopyOnWriteArrayList<>();
                msg.add(content);
                map.put(content.getEmpid(), msg);
            }
        }



    }
}
