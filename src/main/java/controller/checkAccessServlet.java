package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.RoleDAO;
import model.User;

import java.io.IOException;

/**
 * Servlet implementation class checkAccessServlet
 */
@WebServlet("/checkAccessServlet")
public class checkAccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkAccessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		String input_pageAccessLevel = (String) request.getAttribute("pageAccessLevel");
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (input_pageAccessLevel == null) {
			session.setAttribute("errorMessage", "Unidentified access level. Please inform an admin of this issue");
			response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
			return;
		}

		if (loggedInUser == null) {
			response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
			return;
		}
		
		int pageAccessLevel = Integer.parseInt(input_pageAccessLevel);
		
		boolean hasAccess = RoleDAO.userRoleAccess(loggedInUser, pageAccessLevel);
		
		if (!hasAccess) {
			session.setAttribute("errorMessage", "You are unauthorized to enter this page");
			response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
