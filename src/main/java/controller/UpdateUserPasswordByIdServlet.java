package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import UserModel.User;
import UserModel.UserDAO;
import Utils.PasswordBcrypt;

/**
 * Servlet implementation class UpdateUserPasswordByIdServlet
 */
@WebServlet("/UpdateUserPasswordByIdServlet")
public class UpdateUserPasswordByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserPasswordByIdServlet() {
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
			HttpSession session = request.getSession();
			
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			
			int user_id = loggedInUser.getUser_id();
			String input_current_password = request.getParameter("currentPassword");
			String old_hashed_password = loggedInUser.getPassword();
			
			if (PasswordBcrypt.verifyPassword(input_current_password, old_hashed_password)) {
				String input_new_password = request.getParameter("newPassword");
				String input_confirm_new_password = request.getParameter("confirmPassword");
				
				if (input_new_password.equals(input_confirm_new_password)) {
					String new_hashed_password = PasswordBcrypt.hashPassword(input_new_password);
					
					// processing
					int rowsAffected = UserDAO.updateUserPasswordById(user_id, new_hashed_password);
					
					// result
					User currentUser = UserDAO.getUserById(user_id);
					
					session.setAttribute("currentUser", currentUser);
					session.setAttribute("loggedInUser", currentUser);
					
					if (rowsAffected == 0 ) {
						throw new Exception("password wasnt updated for some odd reason");
					}
					
				}
				
			} else {
				throw new Exception("Error: input password doesnt match user's");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/public/HTML/UserCRUD.jsp");
	}

}
