package Controllers.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Models.Booking.BookingDAO;
import Models.Status.Status;
import Models.Status.StatusDAO;

/**
 * Servlet implementation class UpdateBookingStatusServlet
 */
@WebServlet("/UpdateBookingStatusServlet")
public class UpdateBookingStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBookingStatusServlet() {
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
		// Set response content type to plain text
        // response.setContentType("text/plain");

        try {
            // Get booking and status IDs from request parameters
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int statusId = Integer.parseInt(request.getParameter("statusId"));

            // Validate status exists in the database
            StatusDAO statusDAO = new StatusDAO();
            Status status = statusDAO.getStatus(statusId);
            if (status == null) {
                response.getWriter().write("Invalid status ID");
                request.getRequestDispatcher("/BookingStatusServlet").forward(request, response);
                return;
            }

            // Update booking status
            BookingDAO bookingDAO = new BookingDAO();
            boolean updated = bookingDAO.updateBookingStatus(bookingId, statusId);

            if (updated) {
                request.setAttribute("message", "Status updated successfully to " + status.getName());
            } else {
                request.setAttribute("error", "Failed to update status");
            }
            request.getRequestDispatcher("/BookingStatusServlet").forward(request, response);
        } catch (NumberFormatException e) {
        	request.setAttribute("error", "Invalid booking or status ID format");
            request.getRequestDispatcher("/BookingStatusServlet").forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("error", "An error occurred while updating status");
            request.getRequestDispatcher("/BookingStatusServlet").forward(request, response);
        }
	}

}
