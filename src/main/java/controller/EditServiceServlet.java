package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ServiceCategory;
import model.ServiceCategoryDAO;
import model.ServiceDAO;
import model.ServiceServiceCategoryDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			String formAction = request.getParameter("action");
			
			if (formAction.equals("create")) {
				// general config
				int insertedAt = 0;
				
				// process data
				String input_addServicePrice = request.getParameter("addServicePrice");
				String[] input_addServiceCategories = request.getParameterValues("addServiceCategory");

				String addServiceName = request.getParameter("addServiceName");
				String addServiceDescription = request.getParameter("addServiceDescription");
				double addServicePrice = Double.parseDouble(input_addServicePrice != null ? input_addServicePrice : "0");
				String addServicePhotoUrl = "../Image/carouselImage1.jpg";
				int[] addServiceCategories = new int[input_addServiceCategories.length];
				
				for (int i = 0; i < input_addServiceCategories.length; i++) {
					int addServiceCategory = Integer.parseInt(input_addServiceCategories[i]);
					addServiceCategories[i] = addServiceCategory;
				}

				// use data
				insertedAt = ServiceDAO.createService(addServiceName, addServiceDescription, addServicePrice,
						addServicePhotoUrl);
				
				if (insertedAt == 0) {
					throw new Exception(
							"For some reason no rows changed :| maybe theres no data associated with the id? here is the information given in request:"
									+ "\naddServiceName: " + addServiceName + "\naddServiceDescription: "
									+ addServiceDescription + "\naddServicePrice: " + addServicePrice
									+ "\naddServicePhotoUrl: " + addServicePhotoUrl + "\n");
				} else {
					// successfully inserted
					ServiceServiceCategoryDAO.createServiceServiceCategory(insertedAt, addServiceCategories);
				}
				
			} else if (formAction.equals("update")) {
				// update service data
				
				// general config
				int rowsAffected = 0;
				
				// process data
				String input_serviceId = request.getParameter("serviceId");
				String input_servicePrice = request.getParameter("servicePrice");
				
				int serviceId = Integer.parseInt(input_serviceId != null ? input_serviceId : "0");
				double servicePrice = Double.parseDouble(input_servicePrice != null ? input_servicePrice : "0");
				String serviceName = request.getParameter("serviceName");
				String serviceDescription = request.getParameter("serviceDescription");
				String addServicePhotoUrl = "../Image/carouselImage1.jpg";
				
				rowsAffected = ServiceDAO.updateServiceById(serviceId, serviceName, serviceDescription, servicePrice, addServicePhotoUrl);
				
				if (rowsAffected == 0) {
					throw new Exception("For some reason no rows changed :| maybe theres no data associated with the id? here is the information given in request:" +
				"\nserviceId: " + serviceId +
				"\nserviceName: " + serviceName +
				"\nserviceDescription: " + serviceDescription +
				"\nservicePrice: " + servicePrice +
				"\naddServicePhotoUrl: " + addServicePhotoUrl + "\n");
				}
				
			} else {
                throw new Exception("No appropriate associated form action found in parameter. \nform action given: " + formAction);
			}
			
			// redirect user to dashboard
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
			dispatcher.forward(request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/GetServiceInformationServlet?serviceCategory=0");
			dispatcher.forward(request, response);
			return;
		}
	}
}