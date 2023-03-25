package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.UserNotFoundException;

import java.util.List;

public interface IUserService {
    /**
     * Inserts a new User into the database.
     *
     * @param userToInsert The UserDTO object containing the details of the User to be inserted.
     * @throws UserDAOException If an error occurs while accessing the database.
     * @return The User object that was inserted.
     */
    User insertUser(UserDTO userToInsert) throws UserDAOException;

    /**
     * Updates an existing User in the database.
     *
     * @param userToUpdate The UserDTO object containing the updated details of the User.
     * @throws UserDAOException If an error occurs while accessing the database.
     * @throws UserNotFoundException If no User with the given ID exists in the database.
     * @return The updated User object.
     */
    User updateUser(UserDTO userToUpdate) throws UserDAOException, UserNotFoundException;

    /**
     * Deletes a User from the database.
     *
     * @param id The ID of the User to be deleted.
     * @throws UserDAOException If an error occurs while accessing the database.
     * @throws UserNotFoundException If no User with the given ID exists in the database.
     */
    void deleteUser(int id) throws UserDAOException, UserNotFoundException;

    /**
     * Retrieves a list of Users from the database that match a given username.
     *
     * @param username The username to search for.
     * @throws UserDAOException If an error occurs while accessing the database.
     * @throws UserNotFoundException If no Users with the given username exist in the database.
     * @return A List of User objects that match the given username.
     */
    List<User> getUserByUsername(String username) throws UserDAOException, UserNotFoundException;

    boolean isUsernameTaken(UserDTO userDTO, String username);
}
