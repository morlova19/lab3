package test;

import DAO.EmpDAO;
import DAO.RegIdDAO;
import emp.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/my/regId"})
public class RegIdServlet extends HttpServlet{
    String list = "";
    final String PUBLIC_KEY ="AIzaSyDMVIshTd6X5Gy7fGSR9h1lFOOpAz2UG0g";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str= req.getParameter("endpoint");
        if(str != null) {
           String[] a = str.split("/");
            str = a[a.length-1];
            System.out.println(list);
        }
        Employee emp = (Employee) req.getSession().getAttribute("emp");
        RegIdDAO.addRegId(str,emp.getID());
    }


}
