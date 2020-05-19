
package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class User {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String lastName;
	private String firstName;
	private String login; // mail adress
	private String gender;
	private String pwd;
	private Role role = Role.Other;
	private Set<Forum> forumSubscriptions = new HashSet<>();

	public void setRole(Role role) {
		this.role = role;
	}

	public void setForumSubscriptions(Set<Forum> forumSubscriptions) {
		this.forumSubscriptions = forumSubscriptions;
	}

	private static String _query = "select * from `db_sr03`.`user`"; // for findAll static Method
	private static String _anonyme = "anonyme";

	public Set<Forum> getForumSubscriptions() {
		return forumSubscriptions;
	}

	public User() {
	}

	public User(String lastName, String firstName, String login, String gender, String pwd) {

		this.lastName = lastName;
		this.firstName = firstName;
		this.login = login;
		this.gender = gender;
		this.pwd = pwd;
		this.forumSubscriptions = new HashSet<>();
	}

	public User(int id) throws IOException, ClassNotFoundException, SQLException {
		System.out.println("Instancer User: " + id);
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String sql = "select id, fname, lname, login, gender, is_admin from `db_sr03`.`user` where `id` = '" + id
				+ "';";
		try {
			tx = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			List<Object[]> res = query.list();
			for (Object[] row : res) {
				this.id = (int) row[0];
				this.firstName = row[1].toString();
				this.lastName = row[2].toString();
				this.login = row[3].toString();
				this.gender = row[4].toString();
				this.role = Role.values()[(byte) row[5]];
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLogin() {
		return login;
	}

	public String getPwd() {
		return pwd;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setRole(String role) {
		this.role = Role.valueOf(role);
	}

	public Role getRole() {
		return role;
	}

	// active record
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + Objects.hashCode(this.lastName);
		hash = 97 * hash + Objects.hashCode(this.firstName);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (!Objects.equals(this.lastName, other.lastName)) {
			return false;
		}
		if (!Objects.equals(this.firstName, other.firstName)) {
			return false;
		}
		return true;
	}

	public User(String lastName, String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;

	}

	@Override
	public String toString() {
		return "User{" + "lastName=" + lastName + ", firstName=" + firstName + "" + ", login=" + login + ", gender="
				+ gender + "," + " pwd=" + pwd + '}';
	}

	/**
	 * Login
	 * 
	 * @param login
	 * @param pwd
	 * @return
	 */
	public static User FindByloginAndPwd(String login, String pwd) {
		User user = null;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "from User u where u.login=:login and u.pwd=:pwd";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			user = (User) query.setParameter("login", login).setParameter("pwd", pwd).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	/**
	 * Gets all User like Objet from User table
	 * 
	 * @return
	 */
	public static List<User> FindAll() {
		List<User> lst = new ArrayList<User>();
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "from User";
		try {
			Query query = session.createQuery(hql);
			lst = query.list();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lst;
	}

	/**
	 * Tests if user exist.- Validate for new user.
	 * 
	 * @param fname
	 * @param lname
	 * @return
	 */
	public static User FindByLastAndFirstName(String fname, String lname) {
		User user = null;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "from User u where u.firstName=:fname and u.lastName=:lname";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			user = (User) query.setParameter("fname", fname).setParameter("lname", lname).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	/**
	 * Tests if mail has been used.- Validate for new user.
	 * 
	 * @param login
	 * @return
	 */
	public static boolean FindByLogin(String login) {
		boolean exist = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "from User u where u.login=:login";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			User user = (User) query.setParameter("login", login).uniqueResult();
			exist = (user == null) ? false : true;
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return exist;
	}

	/**
	 * récupère les forums dont un utilisateur est abonné
	 * 
	 * @return
	 */
	public List<Forum> LoadForumSubscriptions() {
		List<Forum> lst = new ArrayList<>();
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "select forum from Forum forum join forum.users users where users.id= '" + id + "'";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			lst = query.list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lst;
	}

	/**
	 * Inscrire
	 * 
	 * @param forumId
	 * @return
	 */
	public boolean inscrireForum(int forumId) {
		boolean success = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String sql = "INSERT INTO `db_sr03`.`subscriptions`(`id_user`, `id_forum`) VALUES (" + id + "," + forumId + ")";
		try {
			tx = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			success = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}

	/**
	 * Des-inscrire
	 * 
	 * @param forumId
	 * @return
	 */
	public boolean desInscrire(int forumId) {
		boolean success = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String sql = "delete from subscriptions where id_forum=:forumId and id_user =:userId";
		try {
			tx = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			query.setParameter("forumId", forumId).setParameter("userId", id).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			success = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}

	/**
	 * Add new User
	 * 
	 * @param user
	 * @return
	 */
	public static boolean save(User user) {
		boolean success = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			success = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return success;

	}

	/**
	 * Suppression d’un utilisateur
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean supprimer(int userId) {
		boolean success = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String deleteAbonnement = "DELETE FROM subscriptions WHERE id_user =" + userId;

		try {
			tx = session.beginTransaction();
			// 1.Supression abdonnement
			Query query1 = session.createSQLQuery(deleteAbonnement);
			query1.executeUpdate();

			// 2.Les messages persistent avec un utilisateur anonyme
			// Creer un utilisateur anonyme, s'il exist pas dans la table
			if (FindByLastAndFirstName(_anonyme, _anonyme) == null) {
				String sql = "INSERT INTO `db_sr03`.`user`( `fname`, `lname`, `login`) VALUES ('" + _anonyme + "','"
						+ _anonyme + "','" + _anonyme + "')";
				Query query = session.createSQLQuery(sql);
				query.executeUpdate();
			}
			// Les messages persistent avec un utilisateur anonyme
			User anonymeUser = FindByLastAndFirstName(_anonyme, _anonyme);
			System.out.println("Anooooooo id " + anonymeUser.getId());
			String update = "UPDATE `db_sr03`.`message` SET `editor`='" + anonymeUser.getId() + "' WHERE editor='"
					+ userId + "'";
			Query query2 = session.createSQLQuery(update);
			query2.executeUpdate();

			// 3.Supression d'un user
			String deleteUser = "DELETE FROM User WHERE id =" + userId;
			Query query3 = session.createQuery(deleteUser);
			query3.executeUpdate();

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			success = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}

}
