package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.CartItemDAO;
import model.GetCartItemDAO;
import model.User;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

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
        { // check permission
        	request.setAttribute("pageAccessLevel", "2");
	        RequestDispatcher rd = request.getRequestDispatcher("/checkAccessServlet");
	        rd.include(request, response);
	        
	        HttpSession session = request.getSession();
	        Boolean hasAccess = (Boolean) session.getAttribute("accessCheckResult");
	        
	        if (hasAccess == null || !hasAccess) {
	            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
	            return;
	        }
        }
        
		HttpSession session = request.getSession();
		
        User userIdObj = (User) session.getAttribute("loggedInUser");
        
        if (userIdObj == null) {
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
				response.sendRedirect(request.getContextPath() + "/public/HTML/booking.jsp");
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

	            List<CartItem> updatedCartItems = GetCartItemDAO.getCartItems(((User) session.getAttribute("loggedInUser")).getUser_id());
				
				if(insertBooking != null) {
					session.setAttribute("allCartItems", updatedCartItems);
					response.sendRedirect(request.getContextPath() + "/public/HTML/checkOut.jsp");
				}else {
					response.sendRedirect(request.getContextPath() + "/public/HTML/error.jsp");
				}
			
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "An unexpected error occured ;-;");
			request.getRequestDispatcher("booking.jsp").forward(request, response);
		}
	}

}


