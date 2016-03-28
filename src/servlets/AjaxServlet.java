package servlets;


import DAO.EmpDAO;
import com.google.gson.Gson;
import emp.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet processes ajax request.
 */
@WebServlet(urlPatterns = {"/jobs"})
public class AjaxServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        switch (action){
            case "/jobs":
                makeAnswer(req,resp);
                break;
            case "/emps":
                sendEmps(req,resp);
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    private void sendEmps(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee e = (Employee) req.getSession().getAttribute("emp");
        assert e!=null;
        List<String> names = e.getEmps_map().values().stream().map(Employee::getName).collect(Collectors.toList());
        Gson gson = new Gson();
        PrintWriter pw = resp.getWriter();
        pw.write(gson.toJson(names));
        pw.close();
    }

    private void makeAnswer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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


}
