package gr.aueb.cf.schoolapp.validation;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dto.TeacherDTO;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.UserServiceImpl;

public class Validator {

    private Validator() {}

    public static String validate(TeacherDTO dto) {
        if(dto.getFirstname().equals("")) {
            return "Firstname: Empty";
        }

        if ((dto.getFirstname().length() < 3 || (dto.getFirstname().length()> 32))) {
            return "Firstname: Length";
        }

        if(dto.getLastname().equals("")) {
            return "Lastname: Empty";
        }

        if ((dto.getLastname().length() < 3 || (dto.getLastname().length()> 32))) {
            return "Lastname: Lenght";
        }

        return "";
    }

    public static String validateUser(UserDTO userDTO) {
        final IUserDAO userDAO = new UserDAOImpl();
        final IUserService userService = new UserServiceImpl(userDAO);
        String username = userDTO.getUsername();
        if (username.equals("")) {
            return "Username is required";
        }
        if (username.length() < 3 || username.length() > 32) {
            return "Username must be between 3 and 32 characters long";
        }
        if (userService.isUsernameTaken(userDTO, username)) {
            return "Username is already taken";
        }
        return "";
    }
}
