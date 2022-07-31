package com.mingabytes.hibernate;

import java.sql.Timestamp;

import com.mingabytes.hibernate.dao.UserDao;
import com.mingabytes.hibernate.model.User;

public class App {
	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		User user = new User(1, new Timestamp(System.currentTimeMillis()), "Max");
		userDao.saveStudent(user);
		
		/*
		 * List<Student> students = studentDao.getStudents(); students.forEach(s ->
		 * System.out.println(s.getFirstName()));
		 */
	}
}