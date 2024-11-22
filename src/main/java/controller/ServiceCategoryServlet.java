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
 * Servlet implementation class GetServiceCategoryServlet
 */
@WebServlet("/GetServiceCategoryServlet")
public class ServiceCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceCategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			List<ServiceCategory> services = ServiceCategoryDAO.getServiceCategoryByAll();

			HttpSession session = request.getSession();
	        session.setAttribute("services", services);
	        
			response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
				
				
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
			request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
