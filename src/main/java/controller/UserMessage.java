package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Forum;
import model.User;

/**
 * Servlet implementation class UserMessage
 */
@WebServlet(name = "UserMessage", urlPatterns = { "/UserMessage" })
public class UserMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserMessage() {
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

		int forumId = 0;

		if (session.getAttribute("login") != null) {
			if (request.getParameter("current_forum_id") != null) {
				forumId = Integer.valueOf(request.getParameter("current_forum_id"));
				// Save current forum id in session
				session.setAttribute("current_forum_id", forumId);
			} else {
				forumId = (int) session.getAttribute("current_forum_id");
			}
			request.setAttribute("fil_de_discussion", Forum.LoadMessages(forumId));

		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/user/Message.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User editor = (User) session.getAttribute("login");
		String content = request.getParameter("new_msg");
		int destination = (int) session.getAttribute("current_forum_id");

		if (Forum.addMessage(content, editor, destination))
			request.setAttribute("poster_msg", "Votre message a été publié avec succès!!");
		else
			request.setAttribute("poster_msg", "Votre message n'a pas pu être publié!!");

		doGet(request, response);
	}

}
