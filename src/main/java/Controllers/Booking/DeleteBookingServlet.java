package Controllers.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Models.Booking.BookingDAO;

/**
 * Servlet implementation class DeleteBookingServlet
 */
@WebServlet("/DeleteBookingServlet")
public class DeleteBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBookingServlet() {
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
		try {
            // Get the booking ID from the request
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            
            // Call the DAO method to delete the booking
            BookingDAO.deleteBookingById(bookingId);
            
            // Set success message
            request.getSession().setAttribute("message", "Booking successfully deleted");
            
        } catch (NumberFormatException e) {
            // Handle case where bookingId is not a valid number
            request.getSession().setAttribute("error", "Invalid booking ID format");
            e.printStackTrace();
            
        } catch (Exception e) {
            // Handle any other errors
            request.getSession().setAttribute("error", "Error deleting booking: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Redirect back to the booking list page
        response.sendRedirect(request.getContextPath() + "/public/HTML/bookingStatus.jsp");
	}

}
