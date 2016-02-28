package test;


import DAO.EmpDAO;
import emp.Employee;
import utils.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
           /* Employee emp = new Employee(login);
            emp.setID(1);
            Employee emp1 = new Employee("qwerty2");
            emp1.setID(2);
            emp1.setFname("Ivan");
            emp1.setLname("Ivanov");

            Employee emp2 = new Employee("qwerty1");
            emp2.setID(3);
            emp2.setFname("Ivan");
            emp2.setLname("Petrov");
            List<Employee> emps = new ArrayList<>();
            emps.add(emp1);
            emps.add(emp2);
            emp.setEmps(emps);*/
            req.getSession().setAttribute("emp",emp);
            req.getSession().setAttribute("username",emp.getID());
            //req.getSession().setAttribute("userid",EmpDAO.getEmp_ID(login));
            resp.sendRedirect("my/tasks.jsp?type=cur");
        }
        else {
            req.getSession().setAttribute("username","error");
            resp.sendRedirect("start.jsp");
        }

    }
}
