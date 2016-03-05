package servlets;


import DAO.EmpDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/jobs","/emps"})
public class AjaxServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        switch (action){
            case "/jobs":
                makeAnswer1(req,resp);
                break;
            case "/emps":
                makeAnswer(req,resp);
                break;
        }
    }

    private void makeAnswer1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String dept = req.getParameter("dept");

        if(dept != null)
        {
            Gson gson = new Gson();
            List<String> jobs = EmpDAO.getJobs(dept);
            PrintWriter pw = resp.getWriter();
            pw.write(gson.toJson(jobs));
            pw.close();

        }
    }

    private void makeAnswer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String dept = req.getParameter("dept");
        String job = req.getParameter("job");
        if(dept != null && job!=null)
        {
            Gson gson = new Gson();
            List<String> jobs = EmpDAO.getEmps(dept,job);
            PrintWriter pw = resp.getWriter();
            pw.write(gson.toJson(jobs));
            pw.close();

        }

    }
}
