package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.authentication.Authentication;
import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.util.PasswordHashUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String adminMail = "admin@example.com";
    IUserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(email);
        userDTO.setPassword(password);

        User user = null;
        try {
            user = Authentication.authenticate(userDTO);
        } catch (UserDAOException e) {
            throw new RuntimeException(e);
        }
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        HttpSession session = request.getSession(true);

        session.setAttribute("username", user.getUsername());

        session.setMaxInactiveInterval(60 * 15);

        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(session.getMaxInactiveInterval());
        response.addCookie(cookie);

        if (email.equals(adminMail) && password.equals(System.getenv("TS_ADMIN_PASSWORD"))) {
            response.sendRedirect(request.getContextPath() + "/schoolapp/admin-menu");
        } else {
            response.sendRedirect(request.getContextPath() + "/schoolapp/menu");
        }
    }
}