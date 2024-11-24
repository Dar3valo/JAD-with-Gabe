package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Service;
import model.ServiceCategory;
import model.ServiceCategoryDAO;
import model.ServiceDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class GetServiceInformationServlet
 */
@WebServlet("/GetServiceInformationServlet")
public class GetServiceInformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetServiceInformationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String categoryParam = request.getParameter("serviceCategory");
		int category = Integer.parseInt(categoryParam);
		
		try {
			// define session
			HttpSession session = request.getSession();
			
			// get stored services
			if (category == 0) {
				List<Service> services = ServiceDAO.getServiceInformationByAll();
				
				// set all current selected service category
				session.setAttribute("currentCategory", null);
		        session.setAttribute("services", services);
				
			} else if (category >= 1) {
				List<Service> services = ServiceDAO.getServiceInformationByCategory(category);
				
				// set current selected service category
				ServiceCategory serviceCategory = ServiceCategoryDAO.getServiceCategoryById(category);
	
				session.setAttribute("currentCategory", serviceCategory);
		        session.setAttribute("services", services);
				
			}
			
			// get stored service categories
			List<ServiceCategory> serviceCategories = ServiceCategoryDAO.getServiceCategoryByAll();

	        session.setAttribute("serviceCategories", serviceCategories);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        // go back to dashboard
		response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
