
package model;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MyConnectionClass {

	private static SessionFactory sessionFactory;

	// current Session
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();
		}
		return sessionFactory;
	}

}
