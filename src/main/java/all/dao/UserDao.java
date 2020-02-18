package all.dao;

import all.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void addUser(User user);

    boolean isUserExist(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    User getUser(String name, Long password);
}
