package Controllers.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import Models.Booking.Booking;
import Models.Booking.BookingDAO;
import Models.Status.Status;
import Models.Status.StatusDAO;
import Models.User.User;

/**
 * Servlet implementation class BookingStatusServlet
 */
@WebServlet("/BookingStatusServlet")
public class BookingStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookingStatusServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the user ID from the session
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/public/HTML/login.jsp");
            return;
        }
        int userId = loggedInUser.getUser_id();	

        // Instantiate DAO classes
        StatusDAO statusDAO = new StatusDAO();
        BookingDAO bookingDAO = new BookingDAO();

        // Get bookings by user ID
        List<Booking> bookings = bookingDAO.getBookingsByUserId(userId);

        // Add status descriptions for each booking
        for (Booking booking : bookings) {
            Status status = statusDAO.getStatus(booking.getStatus_id());
            if (status != null) {
                booking.setStatusDescription(status.getName());  // Set status description
            } else {
                booking.setStatusDescription("Unknown");  // Default if no status found
            }
        }

        // Store the list of bookings in the session and redirect to booking status page
        session.setAttribute("bookings", bookings);
        response.sendRedirect(request.getContextPath() + "/public/HTML/bookingStatus.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to plain text
        response.setContentType("text/plain");

        // Get booking and status IDs from request parameters
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            int statusId = Integer.parseInt(request.getParameter("statusId"));

            // Validate status exists in the database
            StatusDAO statusDAO = new StatusDAO();
            Status status = statusDAO.getStatus(statusId);
            if (status == null) {
                response.getWriter().write("Invalid status ID");
                return;
            }

            // Update booking status
            BookingDAO bookingDAO = new BookingDAO();
            boolean updated = bookingDAO.updateBookingStatus(bookingId, statusId);

            if (updated) {
                // Respond with success message
                response.getWriter().write("Status updated successfully to: " + status.getName());
            } else {
                // Respond with error message
                response.getWriter().write("Failed to update status");
            }
        } catch (NumberFormatException e) {
            // Handle invalid format for booking or status ID
            response.getWriter().write("Invalid booking or status ID format");
        } catch (Exception e) {
            // Handle any unexpected exceptions
            response.getWriter().write("An error occurred while updating status");
        }
    }
}
