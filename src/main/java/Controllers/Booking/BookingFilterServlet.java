package Controllers.Booking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;

/**
 * Servlet implementation class BookingFilterServlet
 */
@WebServlet("/BookingFilterServlet")
public class BookingFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filterType = request.getParameter("filterType");
        String filterValue = request.getParameter("filterValue");
        int pageNumber = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 5;

        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookingList;

        if (filterType != null && filterValue != null) {
        	bookingList = bookingDAO.getFilteredBookings(filterType, filterValue, pageNumber, pageSize);
        } else {
        	bookingList = bookingDAO.getBookingDetailsAdmin(pageNumber, pageSize);
        }

        request.setAttribute("bookings", bookingList);

        // Set session attribute for dashboard tab
        request.getSession().setAttribute("dashboardCurrentFocus", "booking-content");

        response.sendRedirect(request.getContextPath() + "/public/HTML/dashboard.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}