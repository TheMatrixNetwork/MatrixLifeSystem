package org.matrixnetwork.matrixlifesystem.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.matrixnetwork.matrixlifesystem.entity.PlayerData;

public class SessionFactoryMaker {
    private static SessionFactory factory;

    private static void configureFactory()
    {
        try {
            factory = new Configuration()
                    .addAnnotatedClass(PlayerData.class)
                    .configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static org.hibernate.SessionFactory getFactory() {
        if (factory == null) {
            configureFactory();
        }

        return factory;
    }

}
