package old.factory;

import all.dao.UserDao;

public interface DaoFactory {
    UserDao createUserDao();
}
