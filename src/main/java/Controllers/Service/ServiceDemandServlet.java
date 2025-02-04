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
 * Servlet implementation class ServiceDemandServlet
 */
@WebServlet("/ServiceDemandServlet")
public class ServiceDemandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceDemandServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServiceDAO dao = new ServiceDAO();
			List<Service> bookingDemand = dao.getServicesByBookingFrequency();
			HttpSession session = request.getSession();
            if (bookingDemand != null && !bookingDemand.isEmpty()) {
                session.setAttribute("bookingFrequency", bookingDemand);
            } else {
                session.setAttribute("error", "No services found.");
            }
            
            response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
			
		}catch(Exception e) {
			e.printStackTrace();
			HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
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
