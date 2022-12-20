package org.matrixnetwork.matrixlifesystem.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;

import java.util.Properties;
import java.util.logging.Level;

public class SessionFactoryMaker {
    private static SessionFactoryMaker instance;
    private SessionFactory sessionFactory;

    private SessionFactoryMaker() {
        try {
            Configuration configuration = new Configuration();

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/matrix?useSSL=false");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "root");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

            settings.put(Environment.SHOW_SQL, "false");

            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);

            settings.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(PlayerData.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactoryMaker getInstance() {
        if(instance == null)
            instance = new SessionFactoryMaker();

        return instance;
    }


    public SessionFactory getFactory() {
        return sessionFactory;
    }
}
