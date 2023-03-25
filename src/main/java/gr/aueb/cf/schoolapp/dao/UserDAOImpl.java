package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    @Override
    public User insert(User user) throws UserDAOException {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            String username = user.getUsername();
            String password = user.getPassword();

            p.setString(1, username);
            p.setString(2,password);
            p.executeUpdate();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new UserDAOException("SQL Error in User " + user + " insertion");
        }
    }

    @Override
    public User update(User user) throws UserDAOException {
        String sql = "UPDATE USERS SET USERNAME = ?, PASSWORD = ? WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            int id = user.getId();
            String username = user.getUsername();
            String password = user.getPassword();

            p.setString(1, username);
            p.setString(2, password);
            p.setInt(3, id);
            p.executeUpdate();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new UserDAOException("SQL Error in User " + user.getUsername()
                    + "update");
        }
    }

    @Override
    public void delete(int id) throws UserDAOException {
        String sql = "DELETE FROM USERS WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new UserDAOException("SQL Error in User with id = "
                    + id  + " deleted");
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM USERS";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                users.add(user);
            }
            return users;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<User> getByUsername(String username) throws UserDAOException {
        String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS WHERE USERNAME LIKE ?";
        ResultSet rs;
        List<User> users = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, username + '%');
            rs = p.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("ID"),rs.getString("USERNAME"), rs.getString("PASSWORD"));
                users.add(user);
            }
            return users;
        } catch (SQLException | ClassNotFoundException e) {
            throw new UserDAOException("SQL Error in User with username = " + username);
        }
    }

    @Override
    public User getById(int id) throws UserDAOException {
        User user = null;
        ResultSet rs;
        String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, id);
            rs = p.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("USERNAME"),rs.getString("PASSWORD"));
            }
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            throw new UserDAOException("SQL Error in User with id " + id);
        }
    }

    @Override
    public User getAdmin(String username, String password) throws UserDAOException {
        if ("admin@example.com".equals(username) && System.getenv("TS_ADMIN_PASSWORD").equals(password)) {
            List<User> users = getByUsername(username);
            if (users.size() > 0) {
                return users.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean isUserValid(String username, String password) {
        String sql = "SELECT PASSWORD FROM USERS WHERE USERNAME = ?";
        String hashedPassword;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, username);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    hashedPassword = rs.getString("PASSWORD");
                } else {
                    return false;
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return BCrypt.checkpw(password, hashedPassword);
    }
}
