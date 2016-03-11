package servlets;

import DAO.EmpDAO;
import emp.EmpTransferObject;
import utils.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for registration of employee.
 */
@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname").toUpperCase();
        String lname = req.getParameter("lname").toUpperCase();
        String dept = req.getParameter("dept");
        String job = req.getParameter("job");
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        EmpTransferObject emp = new EmpTransferObject();
        emp.setFname(fname);
        emp.setLname(lname);
        emp.setDept(dept);
        emp.setJob(job);

        int empid = EmpDAO.checkEmp(emp);
        if (empid != -1) {
            boolean isUniqueLogin = EmpDAO.checkLogin(login);
            if(isUniqueLogin){
                EmpDAO.registration(empid,login,EncryptionUtil.encrypt(pass));
                resp.sendRedirect("start.jsp");
            }
            else {
                req.getSession().setAttribute("error","loginerror");
                resp.sendRedirect("registration.jsp");
            }
        }
        else {
            req.getSession().setAttribute("error","nameerror");
            resp.sendRedirect("registration.jsp");
        }
    }
}
