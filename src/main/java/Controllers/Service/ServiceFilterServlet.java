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
 * Servlet implementation class ServiceFilterServlet
 */
@WebServlet("/ServiceFilterServlet")
public class ServiceFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Retrieve the filter type from the request
        String filterType = request.getParameter("filterType");

        HttpSession session = request.getSession();

        ServiceDAO serviceDAO = new ServiceDAO();
        if ("rating".equals(filterType)) {
            // Fetch services ordered by rating
            List<ServiceByRating> servicesByRating = serviceDAO.getServicesOrderedByRating();
            session.setAttribute("serviceRatingOrder", servicesByRating);
            session.removeAttribute("bookingFrequency"); // Clear the other attribute
        } else if ("demand".equals(filterType)) {
            // Fetch services ordered by booking frequency
            List<Service> servicesByDemand = serviceDAO.getServicesByBookingFrequency();
            session.setAttribute("bookingFrequency", servicesByDemand);
            session.removeAttribute("serviceRatingOrder"); // Clear the other attribute
        }

        // Redirect back to the JSP page to display filtered services
        response.sendRedirect(request.getContextPath() + "/public/HTML/inquiry.jsp");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
