package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdException.BDException;

public class Forum {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String title;
	private String description;
	private User owner;
	private Set<User> users = new HashSet<>();

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Forum(String titre, String description, User u) {
		this.title = titre;
		this.description = description;
		this.owner = u;
	}

	public Forum() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static boolean save(Forum forum) {
		boolean success = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(forum);
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

	public static boolean delete(int forumId) {
		boolean success = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "DELETE FROM Forum WHERE id =:forumId";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("forumId", forumId).executeUpdate();
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
	 * In the Message table, there are two manyToone association, I didn'n find a
	 * solution to figure it out.
	 * 
	 * @param forumId
	 * @return
	 */
	public static List<Message> LoadMessages(int forumId) {
		List<Message> lst = new ArrayList<Message>();
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String sql = "select content,editor,datePublication from `db_sr03`.`message` where `destination` = '" + forumId
				+ "';";
		try {
			tx = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			List<Object[]> res = query.list();
			for (Object[] row : res) {
				System.out.println(" " + row[0] + " " + row[1] + " " + (Date) row[2]);
				String content = row[0].toString();
				int editorId = (int) row[1];
				User editor = new User(editorId);
				Date datePublication = (Date) row[2];
				Message msg = new Message(content, editor, datePublication);
				lst.add(msg);
			}
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

	/*
	 * ajoute un nouveau message sur le forum
	 */
	public static void addMessage2(String content, User editor, int destination)
			throws ClassNotFoundException, IOException, SQLException {

		Connection conn = MyConnectionClass.getInstance();
		String sql = "INSERT INTO `db_sr03`.`message`(`content`, `editor`, `destination`, `datePublication`) VALUES (?,?,?,?)";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
//		pstmt.setInt(2, editor.getId());
		pstmt.setInt(3, destination);
		Date date = new Date();
		pstmt.setDate(4, new java.sql.Date(date.getTime()));

		pstmt.execute();

	}

	/**
	 * Publisher message
	 * 
	 * @param content
	 * @param editor
	 * @param destination
	 * @return
	 */
	public static boolean addMessage(String content, User editor, int destination) {
		boolean successed = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String sql = "INSERT INTO `db_sr03`.`message`(`content`, `editor`, `destination`, `datePublication`) VALUES (:content,:editor,:destination,:datePublication)";
		try {
			tx = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			query.setParameter("content", content);
			query.setParameter("editor", editor);
			query.setParameter("destination", destination);
			Date date = new Date();
			query.setParameter("datePublication", new java.sql.Date(date.getTime()));
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			successed = false;
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return successed;
	}

	/**
	 * Gets all Forum available
	 * 
	 * @return
	 */
	public static List<Forum> FindAll() {
		List<Forum> lst = new ArrayList<Forum>();
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "from Forum";
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
	 * 
	 * @param forumId
	 * @return
	 */
	public static List<User> LoadSubscriptions(int forumId) {
		List<User> lst = new ArrayList<User>();
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "select user from User user join user.forumSubscriptions subs where subs.id=:forumId ";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("forumId", forumId);
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
	 * Tests if Forum exist
	 * 
	 * @param title
	 * @return
	 */
	public static boolean FindForumByName(String title) {
		boolean exist = true;
		Session session = MyConnectionClass.getSessionFactory().openSession();
		Transaction tx = null;
		String hql = "from Forum forum where forum.title=:title";
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			Forum forum = (Forum) query.setParameter("title", title).uniqueResult();
			exist = (forum == null) ? false : true;
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

}
