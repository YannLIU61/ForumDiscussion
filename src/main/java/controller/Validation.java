
package controller;

import model.Forum;
import model.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Validation", urlPatterns = { "/Validation" })
public class Validation extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7411161295088098152L;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			this.getServletContext().getRequestDispatcher("/redirect.html").forward(request, response);
		} else {
			String item = request.getParameter("item");
			switch (item) {
			case "user":
				userValidator(request, response);
				break;
			case "forum":
				forumValidator(request, response);
				break;
			}
		}
	}

	private void forumValidator(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean valid = true;

		String title = request.getParameter("Forum title");
		String description = request.getParameter("Forum description");

		if (Forum.FindForumByName(title)) {
			valid = false;
			request.setAttribute("exist_msg", "Forum exist déjà, veuillez changer le nom!!");
			request.setAttribute("title", title);
			request.setAttribute("description", description);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin/ForumManager.jsp");// abandonner //
																									// l'insertion
			rd.forward(request, response);
		}

		if (valid) {
			RequestDispatcher rd = request.getRequestDispatcher("ForumManager?action=ajouter");
			rd.forward(request, response);
		}

	}

	private void userValidator(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean valid = true;

		String firstName = request.getParameter("User first name");
		String lastName = request.getParameter("User familly name");
		String login = request.getParameter("User email");
		String gender = request.getParameter("gender");
		String password = request.getParameter("User password");
		User newUser = new User(lastName, firstName, login, gender, password);
		if (request.getParameter("role") != null) {
			newUser.setRole(request.getParameter("role"));
		}

		if (request.getParameter("validator") != null) {// des doublons ont été détectés et l'utilisateur à
														// valider son choix
			if ("oui".equals(request.getParameter("validator"))) {// on insère les doublons
				valid = true;
			} else {
				valid = false;
				RequestDispatcher rd = request.getRequestDispatcher("/NouveauUtilisateur.jsp");// abandonner
																								// l'insertion
				rd.forward(request, response);
			}

		} else if (User.FindByLastAndFirstName(firstName, lastName) != null) {
			valid = false;
			request.setAttribute("exist_msg", "Utilisateur exist déjà, voulez vous l'ajouter encore?");
			request.setAttribute("newUser", newUser);
			RequestDispatcher rd = request.getRequestDispatcher("/NouveauUtilisateur.jsp");// abandonner
																							// l'insertion
			rd.forward(request, response);
		}
		if (User.FindByLogin(login)) {
			valid = false;
			request.setAttribute("error_msg", "Mail déjà utilisé, veuillez vous changer!!!");
			request.setAttribute("newUser", newUser);
			RequestDispatcher rd = request.getRequestDispatcher("/NouveauUtilisateur.jsp");
			rd.forward(request, response);
		}

		if (valid) {
			RequestDispatcher rd = request.getRequestDispatcher("UserManager?action=ajouter");
			rd.forward(request, response);
		}

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
