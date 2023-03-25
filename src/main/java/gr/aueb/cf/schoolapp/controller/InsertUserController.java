package gr.aueb.cf.schoolapp.controller;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;
import gr.aueb.cf.schoolapp.validation.Validator;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/schoolapp/user-insert")
public class InsertUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserDAO userDAO = new UserDAOImpl();
    private final IUserService userService = new UserServiceImpl(userDAO);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("error", "");
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        request.setAttribute("insertedUser", userDTO);

        try {
            if (userService.isUsernameTaken(userDTO, username)) {
                request.setAttribute("error", "Username is already taken");
                request.getRequestDispatcher("/schoolapp/static/templates/usersmenu.jsp").forward(request, response);
                return;
            }
            String error = Validator.validateUser(userDTO);
            if (!error.equals("")) {
                request.setAttribute("error", error);
                request.getRequestDispatcher("/schoolapp/static/templates/usersmenu.jsp").forward(request, response);
                return;
            }
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            userDTO.setPassword(hashedPassword);
            userService.insertUser(userDTO);
            request.getRequestDispatcher("/schoolapp/static/templates/userinserted.jsp").forward(request, response);
        } catch (UserDAOException e) {
            request.setAttribute("error", "An error occurred while inserting the user");
            request.getRequestDispatcher("/schoolapp/static/templates/usersmenu.jsp").forward(request, response);
        }
    }
}