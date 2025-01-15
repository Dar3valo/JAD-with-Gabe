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

import Models.Service.Service;
import Models.Service.ServiceDAO;
import Models.ServiceServiceCategory.ServiceServiceCategory;
import Models.ServiceServiceCategory.ServiceServiceCategoryDAO;

/**
 * Servlet implementation class GetAllServiceServiceCategory
 */
@WebServlet("/GetAllServiceServiceCategoryServlet")
public class GetAllServiceServiceCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllServiceServiceCategoryServlet() {
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
            // Fetch all services
            List<ServiceServiceCategory> relationships = ServiceServiceCategoryDAO.getServiceServiceCategoryByAll();

            if (relationships == null) {
            	throw new Exception("categories is null. please pleaplsepalseplaspe be better");
            }
            
            // Store services in session
            HttpSession session = request.getSession();
            session.setAttribute("allServiceServiceCategories", relationships);

			// redirect user to dashboard
			RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
			dispatcher.forward(request, response);
			return;

        } catch (Exception e) {
            e.printStackTrace();

			// redirect user to dashboard
			RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
			dispatcher.forward(request, response);
			return;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
