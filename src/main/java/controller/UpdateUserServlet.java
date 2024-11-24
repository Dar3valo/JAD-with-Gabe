package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.UserDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// define session
			HttpSession session = request.getSession();
			session.setAttribute("dashboardCurrentFocus", "users-content");
			
			// read data
			String input_user_id = request.getParameter("input_user_id");
			System.out.println(input_user_id);
			String input_gender = request.getParameter("input_gender");
			System.out.println(input_gender);
			String input_role_id = request.getParameter("input_role_id");
			System.out.println(input_role_id);
			
			int user_id = Integer.parseInt(input_user_id);
			System.out.println(user_id);
			char gender = input_gender.charAt(0);
			System.out.println(gender);
			int role_id = Integer.parseInt(input_role_id);
			System.out.println(role_id);
			String email = request.getParameter("input_email");
			System.out.println(email);
			String name = request.getParameter("input_name");
			System.out.println(name);
			String profile_photo_url = request.getParameter("input_profile_photo_url");
			System.out.println(profile_photo_url);
			
			
			// processing
			int rowsAffected = UserDAO.updateUserById(user_id, email, gender, name, profile_photo_url, role_id);
			
			if (rowsAffected == 0 ) {
				throw new Exception("no rows were updated for some odd reason");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// redirect user to dashboard
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GetAllUsersServlet");
		dispatcher.forward(request, response);
		return;
	}

}
