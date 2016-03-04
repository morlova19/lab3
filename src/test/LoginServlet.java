package test;


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


@WebServlet(urlPatterns = {"/login","/my/logout","/emp/logout"})
public class LoginServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String action = req.getServletPath();

        switch (action){
            case "/login":
                login(req,resp);
                break;
            case "/my/logout":
                logout(req,resp);
                break;
            case "/emp/logout":
                logout(req,resp);
                break;
        }

    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("username",null);
        resp.sendRedirect("start.jsp");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pass = req.getParameter("pass");
        String login = req.getParameter("username");
        String encrypted_pass = EncryptionUtil.encrypt(pass);

        if(EmpDAO.login(login,encrypted_pass) /*login.equals("qwerty")*/)
        {
            Employee emp = EmpDAO.getEmp(login);
            req.getSession().setAttribute("emp",emp);
            Cookie username = new Cookie("username", String.valueOf(emp.getID()));
            resp.addCookie(username);
            req.getSession().setAttribute("username",emp.getID());
            resp.sendRedirect("my/tasks.jsp?type=cur");
        }
        else {
            req.getSession().setAttribute("username","error");
            resp.sendRedirect("start.jsp");
        }

    }
}
