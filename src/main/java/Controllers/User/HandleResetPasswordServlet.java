package Controllers.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Models.ForgetPassword.ForgetPasswordDAO;
import Models.User.User;
import Models.User.UserDAO;
import Utils.PasswordBcrypt;

/**
 * Servlet implementation class HandleResetPasswordServlet
 */
@WebServlet("/HandleResetPasswordServlet")
public class HandleResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleResetPasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");

        ForgetPasswordDAO dao = new ForgetPasswordDAO();
        User userWithToken = dao.checkTokenValidity(token); // New method to check if token is valid and not expired
        
        if (userWithToken != null) {
            // Token is valid, show the reset password form
        	HttpSession session = request.getSession();
            session.setAttribute("token", token);
            response.sendRedirect(request.getContextPath() + "/public/HTML/resetPasswordForm.jsp");
        } else {
            // Token is invalid or expired
            response.sendRedirect(request.getContextPath() + "/public/HTML/tokenExpired.jsp");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");

        ForgetPasswordDAO dao = new ForgetPasswordDAO();
        User userWithToken = dao.checkTokenValidity(token); // Check if token is valid
        
        if (userWithToken != null) {
            // Token is valid, proceed with password reset
            String email = userWithToken.getEmail();
            String hashedPassword = PasswordBcrypt.hashPassword(newPassword);
            
            UserDAO userDAO = new UserDAO();
            boolean isUpdatedPassword = userDAO.updatePassword(email, hashedPassword);
            
            if (isUpdatedPassword) {
                response.sendRedirect(request.getContextPath() + "/public/HTML/resetSuccess.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
            }
        } else {
            // Token is expired or invalid
            response.sendRedirect(request.getContextPath() + "/public/HTML/tokenExpired.jsp");
        }
	}

}
