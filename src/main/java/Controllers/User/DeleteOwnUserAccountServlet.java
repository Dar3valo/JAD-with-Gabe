package Controllers.User;

import java.io.IOException;

import Models.User.User;
import Models.User.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteOwnUserAccountServlet
 */
@WebServlet("/DeleteOwnUserAccountServlet")
public class DeleteOwnUserAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteOwnUserAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            String input_password = request.getParameter("input_password");
            int logged_in_user_id = loggedInUser.getUser_id();
            
            if (UserDAO.verifyPassword(logged_in_user_id, input_password)) {
            	System.out.println(input_password);
                try {
                    UserDAO.deleteUserById(logged_in_user_id);
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/public/HTML/register.jsp");
                } catch (Exception e) {
                    throw new Exception("An error has occurred. There was a problem deleting user. Please try again.", e);
                }
            } else {
                throw new Exception("An error has occurred. Password input is incorrect. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/public/HTML/error.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
