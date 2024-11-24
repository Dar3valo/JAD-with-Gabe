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
 * Servlet implementation class GetAllRolesServlet
 */
@WebServlet("/GetAllRolesServlet")
public class GetAllRolesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllRolesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// define session
			HttpSession session = request.getSession();
			
			List<Role> roles = RoleDAO.getRoleByAll();
			
			session.setAttribute("roles", roles);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// redirect user to dashboard
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
