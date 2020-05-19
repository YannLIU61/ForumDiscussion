package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet implementation class Forum
 */
@WebServlet(name = "UserForum", urlPatterns = { "/UserForum" })
public class UserForum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserForum() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("login") != null) {
			if (session.getAttribute("my_forum") == null) {
				User user = (User) session.getAttribute("login");
//				try {
//					user.LoadForumSubscriptions();
//					session.setAttribute("my_forum", user.getForumSubscriptions());
//				} catch (ClassNotFoundException | SQLException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				session.setAttribute("my_forum", user.LoadForumSubscriptions());
			}
			this.getServletContext().getRequestDispatcher("/WEB-INF/user/Forum.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
