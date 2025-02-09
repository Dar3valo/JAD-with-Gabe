package Controllers.Booking;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;

/**
 * Servlet implementation class ViewBookingDetailsServlet
 */
@WebServlet("/ViewBookingDetailsServlet")
public class ViewBookingDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBookingDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        BookingDAO bookingDAO = new BookingDAO();
        HttpSession session = request.getSession();

        Booking booking = bookingDAO.getBookingDetailsByBookingId(bookingId);
        session.setAttribute("bookingDetails", booking);
        
        response.sendRedirect(request.getContextPath() + "/public/HTML/bookingDetails.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
