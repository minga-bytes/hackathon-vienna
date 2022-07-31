package com.mingabytes.hibernate.util;

import java.util.Properties;

import javax.imageio.spi.ServiceRegistry;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import com.mingabytes.hibernate.model.User;
 
/**
 * This is a utility class for getting the hibernate session object.
 * @author w3spoint
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = 
                                           buildSessionFactory();
 
    private static SessionFactory buildSessionFactory() {
    	SessionFactory sessionFactory = null;
        try {
        	//Create the configuration object.
        	Configuration configuration = new Configuration(); 
        	
        	Properties settings = new Properties();
        	settings.put(Environment.DRIVER, "org.postgresql.Driver");
         	settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        	settings.put(Environment.USER, "postgres");
        	settings.put(Environment.PASS, "wsx345");        	
        	settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        	settings.put(Environment.SHOW_SQL, "true");
        	settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        	settings.put(Environment.HBM2DDL_AUTO, "create-drop");
        	//Initialize the configuration object with the configuration file data
        	configuration.setProperties(settings);
        	configuration.addAnnotatedClass(User.class);
        	
        	//ServiceRegistry	serviceRegistry = (ServiceRegistry) new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	
        	// Get the SessionFactory object from configuration.
        	sessionFactory = configuration.buildSessionFactory();
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return sessionFactory;
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
}