package gr.aueb.cf.schoolapp.authentication;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;

public class Authentication {
    private static final IUserDAO userDAO = new UserDAOImpl();
    private Authentication() {}

    public static User authenticate(UserDTO userDTO) throws UserDAOException {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        try {
            // Check if User is admin
            if (userDAO.isUserValid(username, password) && username.equals("admin@example.com")) {
                return new User(userDTO.getId(), userDTO.getUsername(),userDTO.getPassword());
            } else if (userDAO.isUserValid(username, password)) {
                return new User(userDTO.getId(), username, password);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            throw new UserDAOException("Error authenticating user " + e);
        }
    }
}
