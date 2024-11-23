package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceServiceCategoryDAO;

import java.io.IOException;

/**
 * Servlet implementation class checkServiceServiceCategoryRelationship
 */
@WebServlet("/checkServiceServiceCategoryRelationship")
public class checkServiceServiceCategoryRelationship extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkServiceServiceCategoryRelationship() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// get and format data
		String input_service_id = request.getParameter("service_id");
		String input_service_category_id = request.getParameter("service_category_id");
		
		int service_id = Integer.parseInt(input_service_id);
		int service_category_id = Integer.parseInt(input_service_category_id);
		
		boolean isRelationship = ServiceServiceCategoryDAO.checkServiceServiceCategoryRelationship(service_id, service_category_id);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
