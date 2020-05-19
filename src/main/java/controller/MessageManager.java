package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Forum;
import model.Message;

/**
 * Servlet implementation class MessageManager
 */
@WebServlet(name = "MessageManager", urlPatterns = { "/MessageManager" })
public class MessageManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageManager() {
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
		System.out.println((String) session.getAttribute("role"));
		
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			this.getServletContext().getRequestDispatcher("/redirect.html").forward(request, response);
		} else {
			int forumId = Integer.valueOf(request.getParameter("id"));
			List<Message> lst = new ArrayList<Message>();

			lst = Forum.LoadMessages(forumId);
			request.setAttribute("msg_lst", lst);
			this.getServletContext().getRequestDispatcher("/WEB-INF/admin/Message.jsp").forward(request, response);
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
