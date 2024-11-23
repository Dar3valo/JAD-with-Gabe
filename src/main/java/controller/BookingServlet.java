package controller;

import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.CartItemDAO;
import model.User;

import java.io.IOException;
import java.sql.Date;

/**
 * Servlet implementation class BookingServlet
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingServlet() {
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
		
		HttpSession session = request.getSession();
        User userIdObj = (User) session.getAttribute("loggedInUser");

        if (userIdObj == null) {
            request.setAttribute("errorMessage", "You must be logged in to make a booking.");
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }

        int userId = userIdObj.getUser_id(); // Assuming logged-in us
		
		String bookingDateStr = request.getParameter("bookingDate");
		String scheduleIdStr = request.getParameter("schedule_id");
		String serviceTypeStr = request.getParameter("serviceType");
		String mainAddress = request.getParameter("main_address");
		String postalCodeStr = request.getParameter("postal_code");
		String specialRequest = request.getParameter("special_request");
		
		if (bookingDateStr == null || scheduleIdStr == null || serviceTypeStr == null || 
                mainAddress == null || postalCodeStr == null) {
                request.setAttribute("errorMessage", "All required fields must be filled");
                request.getRequestDispatcher("/public/HTML/booking.jsp").forward(request, response);
                return;
            }
		
		Date bookingDate = Date.valueOf(bookingDateStr);
        int scheduleId = Integer.parseInt(scheduleIdStr);
        int serviceId = Integer.parseInt(serviceTypeStr);
        int postalCode = Integer.parseInt(postalCodeStr);
		
		try {
			CartItemDAO cartItemDAO = new CartItemDAO();

				CartItem insertBooking = cartItemDAO.insertBooking(0, bookingDate, specialRequest != null ? specialRequest : "", mainAddress,
		                postalCode, userId, scheduleId, serviceId);
				
				if(insertBooking != null) {
					response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
				}else {
					request.setAttribute("errorMessage", "An unexpected error occured ;-;");
					request.getRequestDispatcher("booking.jsp").forward(request, response);
				}
			
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occured ;-;");
			request.getRequestDispatcher("booking.jsp").forward(request, response);
		}
	}

}


