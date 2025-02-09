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
        List<Booking> bookings;
        if(loggedInUser.getRole_id() == 1) {
        	bookings = bookingDAO.getAllBookingsForAdmin();
        }else {
        	bookings = bookingDAO.getBookingsByUserId(loggedInUser.getUser_id());
        }

        
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
        doGet(request, response);
    }
}
