package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import UserModel.User;
import UserModel.UserDAO;

/**
 * Servlet implementation class GetUserInformationById
 */
@WebServlet("/GetUserInformationById")
public class GetUserInformationById extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInformationById() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
			User user = (User) session.getAttribute("loggedInUser");
			
			if (user == null) {
				throw new Exception("no user sent");
			} else {
				session.setAttribute("currentUser", user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/public/HTML/UserCRUD.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
