
package controller;

import model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UserManager", urlPatterns = { "/UserManager" })
public class UserManager extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6184217037030344729L;

	@Override
	public void init() throws ServletException {
		super.init(); // To change body of generated methods, choose Tools | Templates.

	}

	/**
	 * Processes requests for both HTTP <code>POST</code> methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException       if a servlet-specific error occurs
	 * @throws IOException            if an I/O error occurs
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {

		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			this.getServletContext().getRequestDispatcher("/redirect.html").forward(request, response);
		} else {
			String action = request.getParameter("action");
			switch (action) {
			case "delete":
				deleteUser(request, response);
				break;
			case "ajouter":
				ajouteUser(request, response);
				break;
			}
		}
	}

	private void ajouteUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ClassNotFoundException, SQLException {

		String firstName = request.getParameter("User first name");
		String lastName = request.getParameter("User familly name");
		String mail = request.getParameter("User email");
		String gender = request.getParameter("gender");
		String password = request.getParameter("User password");
		User user = new User(lastName, firstName, mail, gender, password);
		if (request.getParameter("role") != null) {
			user.setRole(request.getParameter("role"));
		}
//		user.save();
		if (User.save(user))
			request.setAttribute("ajoute_msg", "Un nouveau Utilisateur vient d'être ajouté!");
		else
			request.setAttribute("ajoute_msg", "Failed.....!");
		doGet(request, response);
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int userId = Integer.valueOf(request.getParameter("userId"));
//			User.supprimerUtilisateur(userId);
		if (User.supprimer(userId))
			request.setAttribute("delete_msg", "Suppression avec success!");
		else
			request.setAttribute("delete_msg", "Suppression avec failed!");

		doGet(request, response);
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
		HttpSession session = request.getSession();

		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
			this.getServletContext().getRequestDispatcher("/redirect.html").forward(request, response);
		} else {
			List<User> listUser;
			listUser = User.FindAll();
			request.setAttribute("listUser", listUser);
			this.getServletContext().getRequestDispatcher("/WEB-INF/admin/UserManager.jsp").forward(request, response);
		}

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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
