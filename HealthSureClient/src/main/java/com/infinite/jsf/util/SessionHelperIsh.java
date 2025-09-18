/**
 * A utility class for managing Hibernate SessionFactory.
 * This class is responsible for initializing and providing access to a
 * Hibernate SessionFactory, which is required to interact with the database.
 * It uses the Hibernate configuration file (hibernate.cfg.xml) for its settings.
 * The SessionHelper class ensures that the SessionFactory is initialized only once
 * and can be reused throughout the application to interact with the database.
 *
 */

package com.infinite.jsf.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class SessionHelperIsh {
	// logger instance for the class
    private static final Logger logger = Logger.getLogger(SessionHelper.class);

    private static final SessionFactory sessionFactory;
 
    static {
        try {
            Configuration config = new Configuration().configure(); // Loads hibernate.cfg.xml
            sessionFactory = config.buildSessionFactory();
            System.out.println("✅ Hibernate SessionFactory initialized.");
            logger.info(" Hibernate SessionFactory initialized.");

        } catch (Throwable ex) {
            System.err.println("❌ SessionFactory creation failed: " + ex);
            logger.log(Level.ERROR, "❌ SessionFactory creation failed: ", ex);

            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getConnection() {
        return sessionFactory;
    }
}
 
 

