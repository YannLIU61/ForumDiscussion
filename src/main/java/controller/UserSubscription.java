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
 * Servlet implementation class Subscription
 */
@WebServlet(name = "UserSubscription", urlPatterns = { "/UserSubscription" })
public class UserSubscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Forum> lst_forum = new ArrayList<Forum>();
	private List<Forum> my_forum = new ArrayList<Forum>();
	private List<Integer> my_forumId = new ArrayList<Integer>();
	// Check if user's subscription state
	private boolean _updated = true;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserSubscription() {
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
		if (session.getAttribute("login") != null) {

			if (_updated) {
				User user = (User) session.getAttribute("login");
				lst_forum = Forum.FindAll();
				
				my_forum = user.LoadForumSubscriptions();

				my_forumId.clear();
				for (Forum forum : my_forum) {
					my_forumId.add(forum.getId());
				}

				session.setAttribute("lst_forum", lst_forum);
				session.setAttribute("my_forum", my_forum);
				session.setAttribute("my_forumId", my_forumId);

				_updated = false;
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/user/Subscription.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		switch (action) {
		case "inscrire":
			try {
				inscrire(request, response);
			} catch (ClassNotFoundException | IOException | ServletException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "desinscrire":
			try {
				desInscrire(request, response);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void desInscrire(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException, ServletException {
		int forumId = Integer.valueOf(request.getParameter("forum_id"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("login");
//		user.updateForumSubscriptions(forumId);
		if (user.desInscrire(forumId)) {
			_updated = true;
			request.setAttribute("desinscrire_msg", "Vous avez bien désinscrire!!");
		} else {
			request.setAttribute("desinscrire_msg", "Failed....!!");
		}

		doGet(request, response);

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ServletException
	 * @throws SQLException
	 */
	private void inscrire(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException, ServletException, SQLException {
		int forumId = Integer.valueOf(request.getParameter("forum_id"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("login");
//		user.addForumSubscription(forumId);
		if (user.inscrireForum(forumId)) {
			_updated = true;
			request.setAttribute("inscrire_msg", "Vous avez bien inscrire!! ");
		} else {
			request.setAttribute("inscrire_msg", "Failed...!! ");
		}

		doGet(request, response);

	}

}
