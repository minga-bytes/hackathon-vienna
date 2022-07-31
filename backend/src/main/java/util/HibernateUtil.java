package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
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
        	//Initialize the configuration object 
                //with the configuration file data
        	configuration.configure("hibernate.cfg.xml");
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