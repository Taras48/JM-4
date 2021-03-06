package old.factory;

import old.factory.DaoFactory;
import old.factory.DaoHibernateFactory;
import old.factory.DaoJDBCFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoByProprties {
    public static DaoFactory getConnectionByProperties() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream fileReader = classLoader.getResourceAsStream("db.properties");
            properties.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (properties.getProperty("db").contains("jdbc")) {
            return new DaoJDBCFactory();
        } else {
            return new DaoHibernateFactory();
        }
    }
}
