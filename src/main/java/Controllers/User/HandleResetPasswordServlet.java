package Controllers.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        
        UserDAO userDAO = new UserDAO();
        
        if(userDAO.isEmailExist(email)) {
        	String hashedPassword = PasswordBcrypt.hashPassword(newPassword);
        	
        	boolean isUpdatedPassword = userDAO.updatePassword(email, hashedPassword);
        	
        	if(isUpdatedPassword) {
        		response.sendRedirect(request.getContextPath() + "/public/HTML/resetSuccess.jsp");
        	}else {
        		response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        	}
        }else {
        	response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
        }
	}

}
