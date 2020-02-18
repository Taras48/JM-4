package all.dao;

import all.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {

        createTable();
        String sql = "insert user(name, mail) values (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getMail());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("user not add");
        }

    }

    @Override
    public List<User> getAllUsers() {
        createTable();
        String sql = "select * from user";
        List<User> users = new ArrayList<User>();
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("mail"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean isUserExist(Long id) {
        String sql = "select * from user where id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            ResultSet resultSet = st.executeQuery();
            resultSet.next();
            if (resultSet != null) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("not is user - error");
        }
        return false;
    }

    @Override
    public void updateUser(User user) {
        String update = "update user set name = ?,mail = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(update)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getMail());
            ps.setLong(3, user.getId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("user not update1");
        }
    }

    @Override
    public void deleteUser(Long id) {
        String sql = "delete from user where id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("erorr delet user");
        }

    }

    @Override
    public User getUser(String name, Long password) {
        String sql = "select * from user where name = ? and password = ?";
        User user = null;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, name);
            st.setLong(2, password);
            ResultSet resultSet = st.executeQuery();
            resultSet.next();
            user = new User(resultSet.getLong("id"), resultSet.getString("name"),
                    resultSet.getString("mail"));
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return user;
    }

    public void createTable() {
        String sql = "create table if not exists user(id bigint auto_increment, name  varchar (30), mail varchar (80), primary key (id))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("error create table");
        }
    }
}
