package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserRegisterDAO;
import model.UserRegister;

import java.io.IOException;

/**
 * Servlet implementation class UserRegisterServlet
 */
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegisterServlet() {
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
        	request.setAttribute("pageAccessLevel", "3");
	        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
	        rd.include(request, response);
	        
	        HttpSession session = request.getSession();
	        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
	        
	        if (hasAccess == null || !hasAccess) {
	            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
	            return;
	        }
        }
        
//		doGet(request, response);
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String gender = request.getParameter("gender");
		
		if (!password.equals(confirmPassword)) {
	        request.setAttribute("errorMessage", "Passwords do not match!");
	        request.getRequestDispatcher("register.jsp").forward(request, response);
	        return; // Exit early to prevent further processing
	    }
		
		try {
			UserRegisterDAO userRegisterDAO = new UserRegisterDAO();
			
			if(userRegisterDAO.isEmailExist(email)) {
				request.setAttribute("errorMessage", "Email already exists, please key in a different email...");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}else {
				UserRegister insertUser = userRegisterDAO.insertUserInfo(name, email, password, gender);
				
				if(insertUser != null) {
					response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
				}else {
					// error occurred
					response.sendRedirect(request.getContextPath() + "/public/HTML/register.jsp");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			// error occurred
			response.sendRedirect(request.getContextPath() + "/public/HTML/register.jsp");
		}
	}

}