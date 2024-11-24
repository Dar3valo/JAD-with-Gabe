package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Role;
import model.RoleDAO;
import model.User;
import model.UserDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class GetUsersByIdServlet
 */
@WebServlet("/GetUsersByRoleServlet")
public class GetUsersByRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsersByRoleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		String input_role_id = request.getParameter("selectedRole");
		int role_id = Integer.parseInt(input_role_id);
		
		List<User> users = UserDAO.getUserByRoleId(role_id);
		Role currentRole = RoleDAO.getRoleById(role_id);
		
		session.setAttribute("users", users);
		session.setAttribute("currentRole", currentRole);
		session.setAttribute("dashboardCurrentFocus", "users-content");
		
		// redirect
		response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
