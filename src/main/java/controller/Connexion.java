
package controller;

import model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Connexion", urlPatterns = { "/Connexion" })
public class Connexion extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4966912902808265826L;

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

		// Vérifier si le login existe
		User u = User.FindByloginAndPwd(request.getParameter("username"), request.getParameter("password"));

		if (u == null) {

			request.setAttribute("bd_Info", "Mot de passe ou login érroné");
			this.getServletContext().getRequestDispatcher("/Connexion.jsp").forward(request, response);

		} else {
			HttpSession session = request.getSession();
			session.setAttribute("login", u);
			String role = u.getRole().toString();
			session.setAttribute("role", role);

			if ("admin".equalsIgnoreCase(role)) {
				this.getServletContext().getRequestDispatcher("/Accueil_admin.jsp").forward(request, response);
			} else {

				this.getServletContext().getRequestDispatcher("/Accueil_user.jsp").forward(request, response);
			}
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

	@Override
	public void init() throws ServletException {
		super.init(); // To change body of generated methods, choose Tools | Templates.

	}

}
