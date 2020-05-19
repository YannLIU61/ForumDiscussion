
package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MyConnectionClass {
	private static Connection singleton;

	MyConnectionClass() throws IOException, ClassNotFoundException, SQLException {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:8889/db_sr03?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String utilisateur = "yann";
		String mdp = "test1234";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {

		}
		singleton = DriverManager.getConnection(url, utilisateur, mdp);

	}

	public static Connection getInstance() throws IOException, ClassNotFoundException, SQLException {
		if (singleton == null) {
			new MyConnectionClass();
		}
		return singleton;
	}

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
