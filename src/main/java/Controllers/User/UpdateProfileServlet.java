package Controllers.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import Models.User.User;
import Models.User.UserDAO;

/**
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProfileServlet() {
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
        { // check permission
        	request.setAttribute("pageAccessLevel", "2");
	        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
	        rd.include(request, response);
	        
	        HttpSession session = request.getSession();
	        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
	        
	        if (hasAccess == null || !hasAccess) {
	            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
	            return;
	        }
        }
        
		try {
			HttpSession session = request.getSession();
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			
			// read data
			String input_gender = request.getParameter("input_gender");
			
			int role_id = loggedInUser.getRole_id();
			int user_id = loggedInUser.getUser_id();
			char gender = input_gender.charAt(0);
			String email = request.getParameter("input_email");
			String name = request.getParameter("input_name");
			String profile_photo_url = request.getParameter("input_profile_photo_url");
			
			// processing
			int rowsAffected = UserDAO.updateUserById(user_id, email, gender, name, profile_photo_url, loggedInUser.getRole_id());
			
			// result
			User currentUser = UserDAO.getUserById(user_id);
			
			session.setAttribute("currentUser", currentUser);
			session.setAttribute("loggedInUser", currentUser);
			
			if (rowsAffected == 0 ) {
				throw new Exception("no rows were updated for some odd reason");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// redirect user to profile page
		response.sendRedirect(request.getContextPath() + "/public/HTML/UserCRUD.jsp");
		return;
	}

}
