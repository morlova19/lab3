package servlets;

import DAO.RegIdDAO;
import emp.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for registration identifier of browser and saving it to database.
 */
@WebServlet(urlPatterns = {"/my/regId"})
public class RegIdServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str= req.getParameter("endpoint");
        if(str != null) {
           String[] a = str.split("/");
            str = a[a.length-1];
        }
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        RegIdDAO.addRegId(str,emp.getID());
    }


}
