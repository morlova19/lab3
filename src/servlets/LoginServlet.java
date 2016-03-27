package servlets;


import DAO.EmpDAO;
import emp.Employee;
import utils.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for authorization of employee.
 */
@WebServlet(urlPatterns = {"/login","/my/logout","/emp/logout","/logout"})
public class LoginServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action){
            case "/login":
                login(req,resp);
                break;

        }
    }
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("emp",null);
        req.getSession().setAttribute("username",null);
        resp.sendRedirect(req.getContextPath() + "/start.jsp");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute("username")==null) {
            req.setCharacterEncoding("UTF-8");
            String pass = req.getParameter("pass");
            String login = req.getParameter("username");
            String encrypted_pass = EncryptionUtil.encrypt(pass);

            if (EmpDAO.login(login, encrypted_pass)) {
                Employee emp = EmpDAO.getEmp(login);
                req.getSession().setAttribute("emp", emp);
                req.getSession().setAttribute("username", emp.getID());
                resp.sendRedirect("tasks.jsp?type=my");
            } else {
                req.getSession().setAttribute("username","error");
                resp.sendRedirect("start.jsp");
            }
        }
        else {
            resp.sendRedirect("tasks.jsp?type=my");
        }

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        switch (action){
            case "/logout":
                logout(req,resp);
                break;
        }
    }
}
