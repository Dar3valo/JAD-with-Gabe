package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import ServiceCategoryModel.ServiceCategory;
import ServiceCategoryModel.ServiceCategoryDAO;
import UserModel.User;
import UserModel.UserDAO;

/**
 * Servlet implementation class GetAllCategoriesServlet
 */
@WebServlet("/GetAllCategoriesServlet")
public class GetAllCategoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllCategoriesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        
		try {
			// define session
			HttpSession session = request.getSession();
			
			List<ServiceCategory> categories = ServiceCategoryDAO.getServiceCategoryByAll();
			
			session.setAttribute("allCategories", categories);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// redirect user to dashboard
		response.sendRedirect(request.getContextPath() + "/public/HTML/services.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
