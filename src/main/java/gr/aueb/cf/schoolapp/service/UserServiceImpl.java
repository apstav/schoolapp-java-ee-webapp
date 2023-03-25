package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService{
    private final IUserDAO userDAO;

    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private User mapUser(UserDTO userDTO) {
        return new User(userDTO.getId(),userDTO.getUsername(),userDTO.getPassword());
    }

    @Override
    public User insertUser(UserDTO userToInsert) throws UserDAOException {
        if (userToInsert == null) return null;

        try {
            User user = mapUser(userToInsert);
            return userDAO.insert(user);

        } catch (UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User updateUser(UserDTO userToUpdate) throws UserDAOException, UserNotFoundException {
        if (userToUpdate == null) return null;

        try {
            if (userDAO.getById(userToUpdate.getId()) == null) {
                throw new UserNotFoundException("User with id " +
                        userToUpdate.getId() + " not found.");
            }
            User user = mapUser(userToUpdate);
            return userDAO.update(user);
        } catch (UserDAOException | UserNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteUser(int id) throws UserDAOException, UserNotFoundException {
        try {
            if (userDAO.getById(id) == null) {
                throw new UserNotFoundException("User with id " + id +
                        " does not exist.");
            }
            userDAO.delete(id);
        } catch (UserDAOException | UserNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> getUserByUsername(String username)
            throws UserDAOException {
        List<User> users = new ArrayList<>();
        if(username == null) return users;

        try {
            users = userDAO.getByUsername(username);
            if(users.size() == 0) throw new UserDAOException("No user" +
                    "with username " + username + " was found.");
            return users;
        } catch (UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean isUsernameTaken(UserDTO userDTO, String username){
        List<User> userList = userDAO.getAll();
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
