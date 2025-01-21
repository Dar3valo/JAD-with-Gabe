package Controllers.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import Models.Service.ServiceDAO;
import Models.ServiceOrderByRating.ServiceByRating;

/**
 * Servlet implementation class ServiceRatingOrderServlet
 */
@WebServlet("/ServiceRatingOrderServlet")
public class ServiceRatingOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceRatingOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			ServiceDAO ratingOrderDAO = new ServiceDAO();
			List<ServiceByRating> ratingOrder = ratingOrderDAO.getServicesOrderedByRating();
			HttpSession session = request.getSession();
            if (ratingOrder != null && !ratingOrder.isEmpty()) {
                session.setAttribute("serviceRatingOrder", ratingOrder);
            } else {
                session.setAttribute("error", "No service ordered according to rating found.");
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
