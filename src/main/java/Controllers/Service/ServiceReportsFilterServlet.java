package Controllers.Service;

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
import Models.ServiceOrderByRating.ServiceByRating;

/**
 * Servlet implementation class ServiceReportsServlet
 */
@WebServlet("/ServiceReportsFilterServlet")
public class ServiceReportsFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceReportsFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String serviceFilterType = request.getParameter("serviceFilterType"); // New filter type variable
        HttpSession session = request.getSession();

        ServiceDAO serviceDAO = new ServiceDAO();
        List<Service> serviceList = null;

        // Filter based on the serviceFilterType received from the front-end
        if ("rating".equals(serviceFilterType)) {
            serviceList = serviceDAO.getServicesOrderedByRating(); // Services ordered by rating
        } else if ("booking".equals(serviceFilterType)) {
            serviceList = serviceDAO.getServicesByBookingFrequency(); // Services ordered by booking frequency
        } else {
            serviceList = serviceDAO.getServiceReport(); // Default: no filter, all services
        }

        // Store the filtered results in the session
        session.setAttribute("serviceReports", serviceList);

        // Set the current focus of the dashboard tab
        session.setAttribute("dashboardCurrentFocus", "service-reports-content");

        // Redirect back to the dashboard with the selected filter in the URL
        String redirectUrl = request.getContextPath() + "/public/HTML/dashboard.jsp";
        if (serviceFilterType != null && !serviceFilterType.isEmpty()) {
            redirectUrl += "?serviceFilterType=" + serviceFilterType; // Append the new filter type
        }
        response.sendRedirect(redirectUrl); // Redirect to dashboard with filtered data
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}