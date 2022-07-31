package main;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.UserDao;
import model.User;
import util.HibernateUtil;

public class GeoApp {
	

	public static void main(String[] args) {

		
		User user = new User();
		user.setId(1);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		user.setCreatedAt(timestamp);
		user.setUsername("Max");
		
		Transaction tx = null;
		//Get the session object.
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
	              //Start hibernate session.
		      tx = session.beginTransaction();
	 
	              //Insert a new student record in the database.
		      session.save(user); 
	 
	              //Commit hibernate transaction if no exception occurs.
		      tx.commit();
		  }catch (HibernateException e) {
		     if(tx!=null){
	                 //Roll back if any exception occurs. 
		         tx.rollback();
		     }
		     e.printStackTrace(); 
		  }finally {
	             //Close hibernate session.
		     session.close(); 
		  }
	}

	

}
