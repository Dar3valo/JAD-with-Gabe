package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceCategoryDAO;
import model.ServiceDAO;

import java.io.IOException;

/**
 * Servlet implementation class EditServiceServlet
 */
@WebServlet("/EditServiceServlet")
public class EditServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServiceServlet() {
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
		
		try {
			String input_addServicePrice = request.getParameter("addServicePrice");
			
			String addServiceName = request.getParameter("addServiceName");
			String addServiceDescription = request.getParameter("addServiceDescription");
			double addServicePrice = Double.parseDouble(input_addServicePrice);
			String addServicePhotoUrl = "../Image/carouselImage1.jpg";

			// general config
			int rowsAffected = 0;
			
			rowsAffected = ServiceDAO.createService(addServiceName, addServiceDescription, addServicePrice, addServicePhotoUrl);
			
			if (rowsAffected == 0) {
				throw new Exception("For some reason no rows changed :| maybe theres no data associated with the id? here is the information given in request:" +
			"\naddServiceName: " + addServiceName +
			"\naddServiceDescription: " + addServiceDescription +
			"\naddServicePrice: " + addServicePrice + 
			"\naddServicePhotoUrl: " + addServicePhotoUrl + "\n");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
			dispatcher.forward(request, response);
			return;
			
		} catch (Exception e){
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
    		dispatcher.forward(request, response);
    		return;
		}
	}

}
