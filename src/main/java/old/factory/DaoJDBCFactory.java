package old.factory;

import all.dao.UserDao;

import all.dao.UserDaoJDBCImpl;
import all.dbHelper.DBHelper;

public class DaoJDBCFactory implements DaoFactory {
    @Override
    public UserDao createUserDao() {
        return new UserDaoJDBCImpl(DBHelper.getInstance().getConnection());
    }
}
