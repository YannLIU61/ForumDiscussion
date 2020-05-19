package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Forum;
import model.User;

/**
 * Servlet implementation class ForumManager
 */
@WebServlet(name = "ForumManager", urlPatterns = { "/ForumManager" })
public class ForumManager extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForumManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			this.getServletContext().getRequestDispatcher("/redirect.html").forward(request, response);
		} else {
			List<Forum> lst = new ArrayList<Forum>();
			lst = Forum.FindAll();
			request.setAttribute("lst_Forum", lst);
			this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ForumManager.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			this.getServletContext().getRequestDispatcher("/redirect.html").forward(request, response);
		} else {

			String action = request.getParameter("action");
			switch (action) {
			case "ajouter":
				try {
					ajouterForum(request, response);
				} catch (ClassNotFoundException | IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "delete":
				deleteForum(request, response);
				break;
			}
		}
	}

	private void deleteForum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int forumId = Integer.valueOf(request.getParameter("forumId"));

		if (Forum.delete(forumId))
			request.setAttribute("delete_msg", "Suppression avec success!");
		else
			request.setAttribute("delete_msg", "Suppression avec failed!");

		doGet(request, response);

	}

	private void ajouterForum(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("login");
		String title = request.getParameter("Forum title");
		String description = request.getParameter("Forum description");
		Forum forum = new Forum(title, description, loginUser);
//			forum.save();
		if (Forum.save(forum))
			request.setAttribute("ajoute_msg", "Un nouveau Forum vient d'être ajouté!");
		else
			request.setAttribute("ajoute_msg", "Failed....!");
		doGet(request, response);

	}

}
